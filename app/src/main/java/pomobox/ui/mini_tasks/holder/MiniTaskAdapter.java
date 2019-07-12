package pomobox.ui.mini_tasks.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import pomobox.R;
import pomobox.base.BaseAlertDialog;
import pomobox.data.database.MiniTaskHelperDB;
import pomobox.ui.mini_tasks.fragment_detail.DetailMiniTaskFragment;
import pomobox.ui.mini_tasks.helper.ItemTouchHelperAdapter;
import pomobox.ui.mini_tasks.helper.ItemTouchHelperViewHolder;
import pomobox.ui.mini_tasks.helper.OnStartDragListener;
import pomobox.data.model.MiniTask;

import static pomobox.utils.Constants.ONE_VALUE;
import static pomobox.utils.Constants.ZERO_VALUE;

public class MiniTaskAdapter extends RecyclerView.Adapter<MiniTaskAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    public static final String KEY_DETAIL_MINI_TASK = "Key Detai Mini Task";
    private List<MiniTask> mMiniTasks;
    private Context mContext;
    private MiniTaskHelperDB mHelper;
    private OnStartDragListener mDragStartListener;
    private FragmentManager mFragmentManager;

    public MiniTaskAdapter(Context context, List<MiniTask> listTask, MiniTaskHelperDB helper,
                           OnStartDragListener dragStartListener, FragmentManager fragmentManager) {
        mMiniTasks = listTask;
        mContext = context;
        mHelper = helper;
        mDragStartListener = dragStartListener;
        mFragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_mini_task, viewGroup, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        mHelper = new MiniTaskHelperDB(mContext);
        boolean check = mMiniTasks.get(i).getIsDone() == ONE_VALUE;
        viewHolder.mCBTaskDone.setChecked(check);
        viewHolder.mTextTaskTitle.setText(mMiniTasks.get(i).getTaskTitle());
        viewHolder.mTextTaskContent.setText(mMiniTasks.get(i).getTaskContent());
        String progress = mMiniTasks.get(i).getProgressPomo() +
                mContext.getString(R.string.separate_progress) + mMiniTasks.get(i).getTargetPomo();
        viewHolder.mTextTaskProgress.setText(progress);
        if (viewHolder.mCBTaskDone.isChecked())
            viewHolder.mTextTaskTitle.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        else viewHolder.mTextTaskTitle.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return mMiniTasks == null ? ZERO_VALUE : mMiniTasks.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        MiniTask task1 = mMiniTasks.get(fromPosition);
        MiniTask task2 = mMiniTasks.get(toPosition);
        task1.setIndex(toPosition);
        task2.setIndex(fromPosition);
        Collections.swap(mMiniTasks, fromPosition, toPosition);
        mHelper.updateTaskData(String.valueOf(task1.getTaskId()), task1);
        mHelper.updateTaskData(String.valueOf(task2.getTaskId()), task2);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mHelper.deleteTaskData(mMiniTasks.get(position).getTaskId());
        mMiniTasks.remove(position);
        Toast.makeText(mContext,
                mContext.getString(R.string.toast_delete), Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements CompoundButton.OnCheckedChangeListener, View.OnClickListener,
            MiniTaskHolderContract.View, ItemTouchHelperViewHolder, View.OnTouchListener {
        private CheckBox mCBTaskDone;
        private TextView mTextTaskTitle, mTextTaskContent, mTextTaskProgress;
        private ImageButton mButtonDel, mButtonDragItem;
        private MiniTaskHolderPresenter mTaskHolderPresenter;

        @SuppressLint("ClickableViewAccessibility")
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCBTaskDone = itemView.findViewById(R.id.checkbox_done);
            mTextTaskTitle = itemView.findViewById(R.id.text_title);
            mTextTaskContent = itemView.findViewById(R.id.text_content);
            mTextTaskProgress = itemView.findViewById(R.id.text_progress);
            mButtonDel = itemView.findViewById(R.id.button_del_task);
            mButtonDragItem = itemView.findViewById(R.id.button_drag_task);
            mTaskHolderPresenter = new MiniTaskHolderPresenter(this, mHelper);
            //On Click Action
            mButtonDragItem.setOnTouchListener(this);
            mButtonDel.setOnClickListener(this);
            mCBTaskDone.setOnCheckedChangeListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //check complete mini task
            MiniTask task = mMiniTasks.get(getAdapterPosition());
            mTaskHolderPresenter.handleMiniTaskDone(task, isChecked);
        }

        @Override
        public void onClick(View v) {
            //click show alert dialog
            if (v.getId() == R.id.button_del_task) mTaskHolderPresenter.handleDeleteMiniTask();
            else if (v == itemView) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(KEY_DETAIL_MINI_TASK, mMiniTasks.get(getAdapterPosition()));
                Fragment fragment = DetailMiniTaskFragment.newInstance();
                fragment.setArguments(bundle);
                if (mFragmentManager != null) {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(null).commit();
                }
            }
        }

        @Override
        public void viewChecked() {
            //on task click completed
            mTextTaskTitle.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mTextTaskTitle.setTypeface(mTextTaskTitle.getTypeface(), Typeface.BOLD_ITALIC);
        }

        @Override
        public void viewUnCheck() {
            //on task click uncompleted
            mTextTaskTitle.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
            mTextTaskTitle.setTypeface(mTextTaskTitle.getTypeface(), Typeface.BOLD);
        }

        //Ask for delete item
        @Override
        public void viewOnDeleteTask() {
            //Alert dialog delete mini task
            BaseAlertDialog alertDialog = new BaseAlertDialog(mContext,
                    mContext.getString(R.string.app_name),
                    mContext.getString(R.string.ask_for_delete),
                    mContext.getString(R.string.alert_no),
                    mContext.getString(R.string.alert_yes),
                    R.mipmap.ic_icon) {
                @Override
                protected void actionClickPositive() {
                    mTaskHolderPresenter.deleteMiniTask(mMiniTasks.get(getAdapterPosition()));
                }

                @Override
                public void actionClickNegative() {
                    notifyDataSetChanged();
                }
            };
            alertDialog.showDialog();
        }

        @Override
        public void onTaskDeleteSuccess() {
            mMiniTasks.remove(getAdapterPosition());
            Toast.makeText(mContext,
                    mContext.getString(R.string.toast_delete), Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        }

        //On item dragged
        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
        }

        //On item clear
        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(mContext.getResources()
                    .getColor(R.color.color_yellow_mustard));
        }

        //Touch on button to drag item
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(this);
            }
            return false;
        }
    }
}
