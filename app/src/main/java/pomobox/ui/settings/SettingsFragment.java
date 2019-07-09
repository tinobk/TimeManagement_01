package pomobox.ui.settings;

import android.view.View;

import pomobox.R;
import pomobox.base.BaseFragment;

public class SettingsFragment extends BaseFragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onViewReady(View view) {

    }
}
