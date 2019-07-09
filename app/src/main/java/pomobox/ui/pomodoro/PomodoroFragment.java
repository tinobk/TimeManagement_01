package pomobox.ui.pomodoro;

import android.view.View;

import pomobox.R;
import pomobox.base.BaseFragment;

public class PomodoroFragment extends BaseFragment {

    public static PomodoroFragment newInstance() {
        return new PomodoroFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pomodoro;
    }

    @Override
    public void onViewReady(View view) {

    }
}
