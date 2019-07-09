package pomobox.ui.mini_tasks.fragment;

import android.app.Dialog;

import pomobox.data.model.MiniTask;

public interface MiniTaskContract {
    interface View {
        void showAddNewTask();

        void showInputEmpty();

        void showAddTaskSuccess(MiniTask breakTask, Dialog dialog);
    }

    interface Presenter {
        void handleShowAddMiniTask();

        void checkAddMiniTask(Dialog dialog, String title, String content, int target);
    }
}
