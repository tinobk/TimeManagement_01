package pomobox.ui.mini_tasks.fragment_detail;

import pomobox.data.database.MiniTaskHelperDB;
import pomobox.data.model.MiniTask;

public class DetailMiniTaskPresenter implements DetailMiniTaskContract.Presenter {
    private MiniTaskHelperDB mHelper;
    private DetailMiniTaskContract.View mView;

    DetailMiniTaskPresenter(DetailMiniTaskContract.View view, MiniTaskHelperDB helper) {
        mView = view;
        mHelper = helper;
    }

    @Override
    public void handleUpdateMiniTask(MiniTask miniTask) {
        if(mHelper.updateTaskData(String.valueOf(miniTask.getTaskId()), miniTask) != -1) mView.onUpdateSuccess();
        else mView.onUpdateFail();
    }

    @Override
    public void handleBackFragment() {
        mView.onBackFragment();
    }
}
