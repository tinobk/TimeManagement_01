package pomobox.ui.mini_tasks.fragment_detail;

import pomobox.data.model.MiniTask;

public interface DetailMiniTaskContract {
    interface View {

        void onUpdateSuccess();

        void onUpdateFail();

        void onBackFragment();
    }

    interface Presenter {

        void handleUpdateMiniTask(MiniTask miniTask);

        void handleBackFragment();
    }
}
