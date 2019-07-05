package pomobox.about;

import pomobox.R;
import pomobox.base.BaseFragment;

public class AboutFragment extends BaseFragment {

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_about;
    }
}
