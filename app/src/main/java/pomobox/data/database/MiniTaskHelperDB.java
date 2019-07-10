package pomobox.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pomobox.R;
import pomobox.data.model.MiniTask;

import static pomobox.utils.Query.CONTENT_MINI_TASK;
import static pomobox.utils.Query.CREATE_MINI_TASK_TABLE;
import static pomobox.utils.Query.DB_NAME;
import static pomobox.utils.Query.DB_VERSION;
import static pomobox.utils.Query.DELETE_MINI_TASK_TABLE;
import static pomobox.utils.Query.DONE_MINI_TASK;
import static pomobox.utils.Query.ID_MINI_TASK;
import static pomobox.utils.Query.MINI_TASK_TABLE;
import static pomobox.utils.Query.PROGRESS_MINI_TASK;
import static pomobox.utils.Query.SELECT_ALL;
import static pomobox.utils.Query.SELECT_BY_ID;
import static pomobox.utils.Query.TARGET_MINI_TASK;
import static pomobox.utils.Query.TITLE_MINI_TASK;

public class MiniTaskHelperDB extends SQLiteOpenHelper{

    private Context mContext;

    public MiniTaskHelperDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MINI_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_MINI_TASK_TABLE);
        onCreate(db);
    }

    //get amount of task
    public int getTaskCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);
        if (cursor == null) return 0;
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //get all data to list
    public List<MiniTask> getAllTasks() {
        List<MiniTask> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);
        if (cursor == null) return arrayList;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MiniTask newTask = new MiniTask();
            newTask.setTaskId(cursor.getInt(cursor.getColumnIndex(ID_MINI_TASK)));
            newTask.setIsDone(cursor.getInt(cursor.getColumnIndex(DONE_MINI_TASK)));
            newTask.setTaskTitle(cursor.getString(cursor.getColumnIndex(TITLE_MINI_TASK)));
            newTask.setTaskContent(cursor.getString(cursor.getColumnIndex(CONTENT_MINI_TASK)));
            newTask.setProgressPomo(cursor.getInt(cursor.getColumnIndex(PROGRESS_MINI_TASK)));
            newTask.setTargetPomo(cursor.getInt(cursor.getColumnIndex(TARGET_MINI_TASK)));
            arrayList.add(newTask);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    //insert new break task
    public void insertTaskData(MiniTask task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Id auto increase
        values.put(DONE_MINI_TASK, task.getIsDone());
        values.put(TITLE_MINI_TASK, task.getTaskTitle());
        values.put(CONTENT_MINI_TASK, task.getTaskContent());
        values.put(PROGRESS_MINI_TASK, task.getProgressPomo());
        values.put(TARGET_MINI_TASK, task.getTargetPomo());
        db.insert(MINI_TASK_TABLE, null, values);//return -1 if insert error
    }

    public void updateTaskData(String id, MiniTask taskData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DONE_MINI_TASK, taskData.getIsDone());
        values.put(TITLE_MINI_TASK, taskData.getTaskTitle());
        values.put(CONTENT_MINI_TASK, taskData.getTaskContent());
        values.put(PROGRESS_MINI_TASK, taskData.getProgressPomo());
        values.put(TARGET_MINI_TASK, taskData.getTargetPomo());
        StringBuilder selection = new StringBuilder();
        selection.append(ID_MINI_TASK).append(mContext.getString(R.string.query_equal)).append(id);
        db.update(MINI_TASK_TABLE, values, String.valueOf(selection), null); //return -1 if update error
    }

    public List<MiniTask> getTaskByid(String id) {
        List<MiniTask> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_BY_ID + id, null);
        if (cursor == null) return taskList;
        cursor.moveToFirst();
        MiniTask task = new MiniTask();
        task.setTaskId(cursor.getInt(cursor.getColumnIndex(ID_MINI_TASK)));
        task.setIsDone(cursor.getInt(cursor.getColumnIndex(DONE_MINI_TASK)));
        task.setTaskTitle(cursor.getString(cursor.getColumnIndex(TITLE_MINI_TASK)));
        task.setTaskContent(cursor.getString(cursor.getColumnIndex(CONTENT_MINI_TASK)));
        task.setProgressPomo(cursor.getInt(cursor.getColumnIndex(PROGRESS_MINI_TASK)));
        task.setTargetPomo(cursor.getInt(cursor.getColumnIndex(TARGET_MINI_TASK)));
        taskList.add(task);
        cursor.close();
        return taskList;
    }

    public void deleteTaskData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuilder selection = new StringBuilder();
        selection.append(ID_MINI_TASK).append(mContext.getString(R.string.query_equal)).append(id);
        db.delete(MINI_TASK_TABLE, String.valueOf(selection), null);
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) db.close();
    }
}
