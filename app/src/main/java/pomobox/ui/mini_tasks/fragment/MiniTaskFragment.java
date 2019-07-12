package pomobox.ui.mini_tasks.fragment;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pomobox.R;
import pomobox.base.BaseCustomDialog;
import pomobox.base.BaseFragment;
import pomobox.data.model.MiniTask;
import pomobox.data.database.MiniTaskHelperDB;
import pomobox.ui.mini_tasks.helper.OnStartDragListener;
import pomobox.ui.mini_tasks.helper.SimpleItemTouchHelperCallback;
import pomobox.ui.mini_tasks.holder.MiniTaskAdapter;

import static pomobox.utils.Constants.TEN_VALUE;

public class MiniTaskFragment extends BaseFragment implements MiniTaskContract.View, OnStartDragListener {

    private MiniTaskHelperDB mHelper;
    private RecyclerView mRecyclerMiniTask;
    private ArrayList<MiniTask> mTaskDataList;
    private MiniTaskAdapter mAdapter;
    private ImageButton mButtonAdd;
    private MiniTaskPresenter mTaskPresenter;
    private ItemTouchHelper mItemTouchHelper;
    private FragmentManager mFragmentManager;
    private Context mContext;

    public static MiniTaskFragment newInstance() {
        return new MiniTaskFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mini_tasks;
    }

    @Override
    public void onViewReady(View view) {
        if(getActivity() != null){
            mContext = getActivity().getApplicationContext();
        }
        mRecyclerMiniTask = view.findViewById(R.id.recycler_mini_tasks);
        mFragmentManager = getFragmentManager();
        mButtonAdd = view.findViewById(R.id.button_add_list);
        mHelper = new MiniTaskHelperDB(getContext());
        mTaskDataList = (ArrayList<MiniTask>) mHelper.getAllTasksByIndex();
        mTaskPresenter = new MiniTaskPresenter(this, mHelper);
        initRecyclerView();
        setButtonAdd();
    }

    private void initRecyclerView() {
        mRecyclerMiniTask.setHasFixedSize(false);
        LinearLayoutManager mLinearLayoutManager =
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerMiniTask.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration mDividerItemDecoration =
                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        mRecyclerMiniTask.addItemDecoration(mDividerItemDecoration);
        //Set default animation for recycler view
        mRecyclerMiniTask.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MiniTaskAdapter(mContext, mTaskDataList,
                mHelper, this, mFragmentManager);
        mRecyclerMiniTask.setAdapter(mAdapter);
        //Handle drag on recycler view
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerMiniTask);

    }

    private void setButtonAdd() {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Action move to add mini task dialog
                mTaskPresenter.handleShowAddMiniTask();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHelper.closeDB();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAddNewTask() {
        //Dialog add new mini task
        BaseCustomDialog addDialog = new BaseCustomDialog(getContext(), R.layout.dialog_add_mini_task) {
            @Override
            public void onBindView(final Dialog dialog) {
                final TextView textTargetTask = dialog.findViewById(R.id.text_show_target_task);
                final EditText textTitle = dialog.findViewById(R.id.text_enter_title_task);
                final EditText textContent = dialog.findViewById(R.id.text_enter_content_task);
                Button buttonAdd = dialog.findViewById(R.id.button_create_task);
                final SeekBar seekBar = dialog.findViewById(R.id.seekbar_target_pomodoro);
                seekBar.setMax(TEN_VALUE);
                final int minProgress = 1;
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        textTargetTask.setText(String.valueOf(progress + minProgress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String taskTitle = textTitle.getText().toString();
                        String taskContent = textContent.getText().toString();
                        int targetPomodoro = seekBar.getProgress() + minProgress;
                        mTaskPresenter.checkAddMiniTask(taskTitle, taskContent, targetPomodoro);
                        dialog.dismiss();
                    }
                });
            }
        };
        addDialog.showDialog();
    }

    @Override
    public void showInputEmpty() {
        Toast.makeText(mContext, getString(R.string.warning_title_empty), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddTaskSuccess(MiniTask miniTask) {
        if (miniTask != null) mTaskDataList.add(miniTask);
        Toast.makeText(mContext, getString(R.string.toast_created), Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
