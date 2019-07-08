package pomobox.utils;

public class Query {

    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "pomodoro.db";
    public static final String MINI_TASK_TABLE = "MiniTaskTable";

    //Columns in mini task
    public static final String ID_MINI_TASK = "MiniTaskId";
    public static final String DONE_MINI_TASK = "MiniTaskDone";
    public static final String TITLE_MINI_TASK = "MiniTaskTitle";
    public static final String CONTENT_MINI_TASK = "MiniTaskContent";
    public static final String PROGRESS_MINI_TASK = "MiniTaskProgress";
    public static final String TARGET_MINI_TASK = "MiniTaskTarget";
    public static final String INDEX_MINI_TASK = "MiniTaskIndex";

    //Query mini task
    public static final String CREATE_MINI_TASK_TABLE =
            "CREATE TABLE IF NOT EXISTS " + MINI_TASK_TABLE + "("
                    + ID_MINI_TASK + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + DONE_MINI_TASK + " INTEGER(1),"
                    + TITLE_MINI_TASK + " VARCHAR(30),"
                    + CONTENT_MINI_TASK + " VARCHAR(200),"
                    + PROGRESS_MINI_TASK + " INTEGER(8),"
                    + TARGET_MINI_TASK + " INTEGER(8),"
                    + INDEX_MINI_TASK + " INTEGER)";

    public static final String DELETE_MINI_TASK_TABLE = "DROP TABLE IF EXISTS " + MINI_TASK_TABLE;
    public static final String SELECT_ALL = "SELECT * FROM " + MINI_TASK_TABLE;
    public static final String SELECT_ALL_BY_INDEX = "SELECT * FROM " + MINI_TASK_TABLE + " ORDER BY "+INDEX_MINI_TASK;
    public static final String SELECT_BY_ID = "SELECT * FROM " + MINI_TASK_TABLE + " WHERE " + ID_MINI_TASK + " = ";
}
