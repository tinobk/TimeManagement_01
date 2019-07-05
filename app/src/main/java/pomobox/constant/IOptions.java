package pomobox.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static pomobox.constant.Constants.OPTION_ABOUT;
import static pomobox.constant.Constants.OPTION_AISHENHOWER_BOX;
import static pomobox.constant.Constants.OPTION_BREAK_TASKS;
import static pomobox.constant.Constants.OPTION_HELPS;
import static pomobox.constant.Constants.OPTION_POMODORO;
import static pomobox.constant.Constants.OPTION_SETTINGS;

@IntDef({OPTION_AISHENHOWER_BOX, OPTION_POMODORO, OPTION_BREAK_TASKS,
        OPTION_SETTINGS, OPTION_HELPS, OPTION_ABOUT})

@Retention(RetentionPolicy.SOURCE)
public @interface IOptions {
}
