package pomobox.break_tasks;

import pomobox.R;
import pomobox.base.BaseFragment;

public class BreakTasksFragment extends BaseFragment {

    public static BreakTasksFragment newInstance() {
        return new BreakTasksFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_break_tasks;
    }
}
