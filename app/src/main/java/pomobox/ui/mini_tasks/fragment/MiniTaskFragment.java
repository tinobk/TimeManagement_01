package pomobox.ui.mini_tasks.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import pomobox.ui.mini_tasks.holder.MiniTaskAdapter;

import static pomobox.utils.Constants.TEN_VALUE;

public class MiniTaskFragment extends BaseFragment implements MiniTaskContract.View {

    private MiniTaskHelperDB mHelper;
    private RecyclerView mRecyclerMiniTask;
    private ArrayList<MiniTask> mTaskDataList;
    private MiniTaskAdapter mAdapter;
    private Context mContext;
    private ImageButton mButtonAdd;
    private MiniTaskPresenter mTaskPresenter;

    public static MiniTaskFragment newInstance() {
        return new MiniTaskFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mini_tasks;
    }

    @Override
    public void onViewReady(View view) {
        mContext = getContext();
        mRecyclerMiniTask = view.findViewById(R.id.recycler_mini_tasks);
        mButtonAdd = view.findViewById(R.id.button_add_list);
        mHelper = new MiniTaskHelperDB(mContext);
        mTaskDataList = (ArrayList<MiniTask>) mHelper.getAllTasks();
        mTaskPresenter = new MiniTaskPresenter(this, mHelper);
        initRecyclerView();
        setButtonAdd();
    }

    private void initRecyclerView() {
        mRecyclerMiniTask.setHasFixedSize(false);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerMiniTask.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        mRecyclerMiniTask.addItemDecoration(mDividerItemDecoration);
        mRecyclerMiniTask.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new MiniTaskAdapter(mContext, mTaskDataList, mHelper);
        mRecyclerMiniTask.setAdapter(mAdapter);
        //Set default animation for recycler view
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
        BaseCustomDialog addDialog = new BaseCustomDialog(mContext, R.layout.dialog_add_mini_task) {
            @Override
            public void onBindView(final Dialog dialog) {
                final TextView textTargetTask = dialog.findViewById(R.id.text_show_target_task);
                final EditText textTitle = dialog.findViewById(R.id.text_enter_title_task);
                final EditText textContent = dialog.findViewById(R.id.text_enter_content_task);
                Button buttonAdd = dialog.findViewById(R.id.button_create_task);
                final SeekBar seekBar = dialog.findViewById(R.id.seekbar_target_pomodoro);
                seekBar.setMax(TEN_VALUE);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        textTargetTask.setText(String.valueOf(progress));
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
                        int targetPomodoro = seekBar.getProgress();
                        mTaskPresenter.checkAddMiniTask(taskTitle, taskContent, targetPomodoro);\
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
    public void showAddTaskSuccess(MiniTask miniTask, Dialog dialog) {
        if (miniTask != null) mTaskDataList.add(miniTask);
        Toast.makeText(mContext, getString(R.string.toast_save), Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
        dialog.dismiss();
    }
}
