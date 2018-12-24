package com.todolist.crespi.todolist.db;

import android.provider.BaseColumns;

public class TaskContract {
    static final String DB_NAME = "com.todolist.crespi.todolist.datadate";
    static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {

        static final String TABLE = "tasks";
        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_DESC = "desc";
        public static final String COL_TASK_DATE = "date";
        public static final String COL_TASK_DONE = "isDone";
    }
}
