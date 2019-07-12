package pomobox.ui.mini_tasks.holder;

import pomobox.data.database.MiniTaskHelperDB;
import pomobox.data.model.MiniTask;

import static pomobox.utils.Constants.ONE_VALUE;
import static pomobox.utils.Constants.ZERO_VALUE;

public class MiniTaskHolderPresenter implements MiniTaskHolderContract.Presenter {

    private MiniTaskHolderContract.View mView;
    private MiniTaskHelperDB mHelper;

    MiniTaskHolderPresenter(MiniTaskAdapter.ViewHolder viewHolder, MiniTaskHelperDB helper) {
        mView = viewHolder;
        mHelper = helper;
    }


    @Override
    public void handleDeleteMiniTask() {
        if (mHelper.getTaskCount() != ZERO_VALUE) {
            mView.viewOnDeleteTask();
        }
    }

    @Override
    public void deleteMiniTask(MiniTask task) {
        mHelper.deleteTaskData(task.getTaskId());
        mView.onTaskDeleteSuccess();
    }

    @Override
    public void handleMiniTaskDone(MiniTask task, boolean isCheck) {
        int done = isCheck ? ONE_VALUE : ZERO_VALUE;
        task.setIsDone(done);
        if (isCheck) {
            mView.viewChecked();
        } else {
            mView.viewUnCheck();
        }
        mHelper.updateTaskData(String.valueOf(task.getTaskId()), task);
    }
}

