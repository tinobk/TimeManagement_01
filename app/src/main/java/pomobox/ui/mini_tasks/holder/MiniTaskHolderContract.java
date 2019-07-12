package pomobox.ui.mini_tasks.holder;


import pomobox.data.model.MiniTask;

public interface MiniTaskHolderContract {
    interface View {
        void viewChecked();

        void viewUnCheck();

        void viewOnDeleteTask();

        void onTaskDeleteSuccess();
    }

    interface Presenter {
        void handleDeleteMiniTask();

        void deleteMiniTask(MiniTask task);

        void handleMiniTaskDone(MiniTask task, boolean isCheck);
    }
}
