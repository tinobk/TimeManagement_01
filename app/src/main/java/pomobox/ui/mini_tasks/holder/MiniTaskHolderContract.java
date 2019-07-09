package pomobox.ui.mini_tasks.holder;


import pomobox.data.model.MiniTask;

public interface MiniTaskHolderContract {
    interface View {
        void viewChecked();

        void viewUnCheck();

        void viewOnDeleteTask();
    }

    interface Presenter {
        void handleDeleteTask();

        void deleteTask(MiniTask task);

        void handleTaskDone(MiniTask task, boolean isCheck);
    }
}
