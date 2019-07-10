package pomobox.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import static pomobox.utils.Constants.DEFAULT_ZERO_VALUE;
import static pomobox.utils.Constants.ZERO_VALUE;

public class MiniTask implements Parcelable {
    private int mTaskId;
    private int mIsDone;
    private String mTaskTitle;
    private String mTaskContent;
    private int mTargetPomo;
    private int mProgressPomo;

    public MiniTask() {
    }

    public MiniTask(String taskTitle, String taskContent, int targetPomo) {
        mIsDone = ZERO_VALUE;
        mTaskTitle = taskTitle;
        mTaskContent = taskContent;
        mProgressPomo = ZERO_VALUE;
        mTargetPomo = targetPomo;
    }

    protected MiniTask(Parcel in) {
        mTaskId = in.readInt();
        mIsDone = in.readInt();
        mTaskTitle = in.readString();
        mTaskContent = in.readString();
        mTargetPomo = in.readInt();
        mProgressPomo = in.readInt();
    }

    public static final Creator<MiniTask> CREATOR = new Creator<MiniTask>() {
        @Override
        public MiniTask createFromParcel(Parcel in) {
            return new MiniTask(in);
        }

        @Override
        public MiniTask[] newArray(int size) {
            return new MiniTask[size];
        }
    };

    public int getTaskId() {
        return mTaskId;
    }

    public void setTaskId(int taskId) {
        mTaskId = taskId;
    }

    public int getIsDone() {
        return mIsDone;
    }

    public void setIsDone(int isDone) {
        mIsDone = isDone;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public String getTaskContent() {
        return mTaskContent;
    }

    public void setTaskContent(String taskContent) {
        mTaskContent = taskContent;
    }

    public int getTargetPomo() {
        return mTargetPomo;
    }

    public void setTargetPomo(int targetPomo) {
        mTargetPomo = targetPomo;
    }

    public int getProgressPomo() {
        return mProgressPomo;
    }

    public void setProgressPomo(int progressPomo) {
        mProgressPomo = progressPomo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTaskId);
        dest.writeInt(mIsDone);
        dest.writeString(mTaskTitle);
        dest.writeString(mTaskContent);
        dest.writeInt(mProgressPomo);
        dest.writeInt(mTargetPomo);
    }
}
