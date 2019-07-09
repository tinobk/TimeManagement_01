package pomobox.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static pomobox.utils.Constants.OPTION_ABOUT;
import static pomobox.utils.Constants.OPTION_AISHENHOWER_BOX;
import static pomobox.utils.Constants.OPTION_HELPS;
import static pomobox.utils.Constants.OPTION_MINI_TASKS;
import static pomobox.utils.Constants.OPTION_POMODORO;
import static pomobox.utils.Constants.OPTION_SETTINGS;

@IntDef({OPTION_AISHENHOWER_BOX, OPTION_POMODORO, OPTION_MINI_TASKS,
        OPTION_SETTINGS, OPTION_HELPS, OPTION_ABOUT})

@Retention(RetentionPolicy.SOURCE)
public @interface IOptions {
}
