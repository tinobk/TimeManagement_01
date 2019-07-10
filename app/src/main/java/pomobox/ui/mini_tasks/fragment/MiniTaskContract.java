package pomobox.ui.mini_tasks.fragment;

import pomobox.data.model.MiniTask;

public interface MiniTaskContract {
    interface View {
        void showAddNewTask();

        void showInputEmpty();

        void showAddTaskSuccess(MiniTask breakTask);
    }

    interface Presenter {
        void handleShowAddMiniTask();

        void checkAddMiniTask(String title, String content, int target);
    }
}
