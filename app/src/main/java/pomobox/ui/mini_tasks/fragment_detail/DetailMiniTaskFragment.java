package pomobox.ui.mini_tasks.fragment_detail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import pomobox.R;
import pomobox.base.BaseFragment;
import pomobox.data.database.MiniTaskHelperDB;
import pomobox.data.model.MiniTask;
import pomobox.ui.mini_tasks.fragment.MiniTaskFragment;
import pomobox.utils.StringUtil;

import static pomobox.ui.mini_tasks.holder.MiniTaskAdapter.KEY_DETAIL_MINI_TASK;
import static pomobox.utils.Constants.NINE_VALUE;
import static pomobox.utils.Constants.ONE_VALUE;
import static pomobox.utils.StringUtil.getStringUtil;

public class DetailMiniTaskFragment extends BaseFragment implements
        View.OnClickListener, DetailMiniTaskContract.View, SeekBar.OnSeekBarChangeListener {

    private TextView mTextTitle;
    private TextView mTextProgress;
    private TextView mTextTarget;
    private EditText mTextEditTitle;
    private EditText mTextEditContent;
    private SeekBar mSeekBarProgress;
    private SeekBar mSeekBarTarget;
    private ImageButton mButtonEditTitle;
    private Button mButtonUpdate;
    private Button mButtonBack;
    private MiniTask mMiniTask;
    private MiniTaskHelperDB mHelper;
    private Context mContext;

    private int progressStart;
    private DetailMiniTaskPresenter mDetailMiniTaskPresenter;

    public static DetailMiniTaskFragment newInstance() {
        return new DetailMiniTaskFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail_mini_task;
    }

    @Override
    public void onViewReady(View view) {
        mContext = getContext();
        mHelper = new MiniTaskHelperDB(mContext);
        initView(view);
        setEvents();
    }

    private void initView(View view) {
        mTextTitle = view.findViewById(R.id.text_title_mini_task);
        mTextProgress = view.findViewById(R.id.text_show_progress_task);
        mTextTarget = view.findViewById(R.id.text_show_target_task);
        mTextEditTitle = view.findViewById(R.id.text_update_title_mini_task);
        mTextEditContent = view.findViewById(R.id.text_update_content_task);
        mSeekBarProgress = view.findViewById(R.id.seekbar_progress_pomodoro);
        mSeekBarTarget = view.findViewById(R.id.seekbar_target_pomodoro);
        mButtonEditTitle = view.findViewById(R.id.button_edit_title);
        mButtonUpdate = view.findViewById(R.id.button_update_task);
        mButtonBack = view.findViewById(R.id.button_back);
        getDataMiniTask();
    }

    private void setEvents() {
        mDetailMiniTaskPresenter = new DetailMiniTaskPresenter(this, mHelper);
        mButtonEditTitle.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
        mSeekBarTarget.setOnSeekBarChangeListener(this);
    }

    private void getDataMiniTask() {
        if (this.getArguments() != null) {
            mMiniTask = this.getArguments().getParcelable(KEY_DETAIL_MINI_TASK);
        }
        if (mMiniTask != null) {
            String title = mMiniTask.getTaskTitle();
            String content = mMiniTask.getTaskContent();
            int target = mMiniTask.getTargetPomo();
            progressStart = mMiniTask.getProgressPomo();
            mTextTitle.setText(title);
            mTextEditTitle.setText(title);
            mTextEditContent.setText(content);
            mSeekBarProgress.setProgress(progressStart);
            mSeekBarProgress.setMax(target);
            mSeekBarProgress.setEnabled(false);
            StringBuilder stringProgress = getStringUtil(String.valueOf(progressStart),
                    getString(R.string.separate_progress), String.valueOf(target));
            mTextProgress.setText(stringProgress);
            mSeekBarTarget.setProgress(target);
            mSeekBarTarget.setMax(NINE_VALUE);
            mTextTarget.setText(String.valueOf(target));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_edit_title:
                setOnButtonEditTitle();
                break;
            case R.id.button_update_task:
                setOnButtonUpdate();
                break;
            case R.id.button_back:
                mDetailMiniTaskPresenter.handleBackFragment();
                break;
            default:
                break;
        }
    }

    private void setOnButtonEditTitle() {
        mTextTitle.setVisibility(View.INVISIBLE);
        mTextEditTitle.setVisibility(View.VISIBLE);
        mTextEditTitle.setSelection(mTextEditTitle.length());
    }

    private void setOnButtonUpdate() {
        String title = mTextEditTitle.getText().toString();
        String content = mTextEditContent.getText().toString();
        int target = mSeekBarTarget.getProgress() + ONE_VALUE;
        mMiniTask.setTaskTitle(title);
        mMiniTask.setTaskContent(content);
        mMiniTask.setTargetPomo(target);
        mDetailMiniTaskPresenter.handleUpdateMiniTask(mMiniTask);
    }

    @Override
    public void onUpdateSuccess() {
        Toast.makeText(mContext, getResources().getString(R.string.toast_updated), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateFail() {
        Toast.makeText(mContext, getResources().getString(R.string.update_failure), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackFragment() {
        Fragment fragment = MiniTaskFragment.newInstance();
        FragmentManager manager = getFragmentManager();
        if (manager != null) {
            manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mTextTarget.setText(String.valueOf(progress + ONE_VALUE));
        StringBuilder stringProgress = getStringUtil(String.valueOf(progressStart),
                getString(R.string.separate_progress),mTextTarget.getText().toString());
        mTextProgress.setText(stringProgress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
