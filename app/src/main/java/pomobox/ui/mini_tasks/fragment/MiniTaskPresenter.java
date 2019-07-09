package pomobox.ui.mini_tasks.fragment;

import pomobox.data.database.MiniTaskHelperDB;
import pomobox.data.model.MiniTask;

public class MiniTaskPresenter implements MiniTaskContract.Presenter {

    private MiniTaskContract.View mView;
    private MiniTaskHelperDB mHelperDB;

    MiniTaskPresenter(MiniTaskContract.View view, MiniTaskHelperDB helperDB) {
        mView = view;
        mHelperDB = helperDB;
    }

    @Override
    public void handleShowAddMiniTask() {
        //handle show dialog add new mini task
        mView.showAddNewTask();
    }

    @Override
    public void checkAddMiniTask(String title, String content, int target) {
        //Check for add new mini task
        if (title.isEmpty()) {
            mView.showInputEmpty();
        } else {
            MiniTask taskData = new MiniTask(title, content, target);
            mHelperDB.insertTaskData(taskData);
            mView.showAddTaskSuccess(taskData);
        }
    }
}
