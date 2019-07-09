package pomobox.ui.mini_tasks.holder;
import pomobox.data.database.MiniTaskHelperDB;
import pomobox.data.model.MiniTask;

public class MiniTaskHolderPresenter implements MiniTaskHolderContract.Presenter {

    private MiniTaskHolderContract.View mView;
    private MiniTaskHelperDB mHelper;

    MiniTaskHolderPresenter(MiniTaskAdapter.ViewHolder viewHolder, MiniTaskHelperDB helper) {
        mView = viewHolder;
        mHelper = helper;
    }


    @Override
    public void handleDeleteTask() {
        if (mHelper.getTaskCount() != 0) {
            mView.viewOnDeleteTask();
        }
    }

    @Override
    public void deleteTask(MiniTask task) {
        mHelper.deleteTaskData(task.getTaskId());
    }

    @Override
    public void handleTaskDone(MiniTask task, boolean isCheck) {
        int done = isCheck ? 1 : 0;
        task.setIsDone(done);
        if (isCheck) {
            mView.viewChecked();
        } else {
            mView.viewUnCheck();
        }
        mHelper.updateTaskData(String.valueOf(task.getTaskId()), task);
    }
}

