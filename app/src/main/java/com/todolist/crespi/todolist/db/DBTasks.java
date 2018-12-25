package com.todolist.crespi.todolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBTasks extends SQLiteOpenHelper {

    public DBTasks(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    /*
    Create the table
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_DESC + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_DATE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_DONE + " INTEGER);";

        db.execSQL(createTable);
    }

    /*
    On upgrade, required
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int novus) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }

    public void dropDb() {
        SQLiteDatabase dtb = this.getWritableDatabase();
        dtb.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(dtb);
    }


    /*
    Add an element to the database
     */
    public void addElement(String task, String desc, String date) {
        SQLiteDatabase dtb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
        values.put(TaskContract.TaskEntry.COL_TASK_DESC, desc);
        values.put(TaskContract.TaskEntry.COL_TASK_DONE, 0);
        values.put(TaskContract.TaskEntry.COL_TASK_DATE, date);
        dtb.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        dtb.close();
    }

    /*
    Function to retrieve data from database
     */
    public ArrayList<Task> getTasks(String where, String orderBy) {

        ArrayList<Task> tasks = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE,
                        TaskContract.TaskEntry.COL_TASK_DESC, TaskContract.TaskEntry.COL_TASK_DATE,
                        TaskContract.TaskEntry.COL_TASK_DONE},
                        where, null, null, null, orderBy);

        while(cursor.moveToNext()) {
            String name;
            String desc;
            String date;
            int done;
            int id;

            int i = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            name = cursor.getString(i);
            i = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DESC);
            desc = (cursor.getString(i));
            i = cursor.getColumnIndex(TaskContract.TaskEntry._ID);
            id = cursor.getInt(i);
            i = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE);
            date = cursor.getString(i);
            i = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DONE);
            done = cursor.getInt(i);

            tasks.add(new Task(id, name, desc, date, done));
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public Task getOneTask(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE,
                        TaskContract.TaskEntry.COL_TASK_DESC, TaskContract.TaskEntry.COL_TASK_DATE,
                        TaskContract.TaskEntry.COL_TASK_DONE},
                TaskContract.TaskEntry._ID + " = " + id, null, null, null, null);

        String name = "";
        String desc = "";
        String date = "";
        int taskid = 0;
        int done = 0;

        if (cursor.moveToNext()) {
            int i = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            name = cursor.getString(i);
            i = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DESC);
            desc = cursor.getString(i);
            i = cursor.getColumnIndex(TaskContract.TaskEntry._ID);
            taskid = cursor.getInt(i);
            i = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DONE);
            done = cursor.getInt(i);
            i = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE);
            date = cursor.getString(i);
        }

        cursor.close();
        db.close();
        return new Task(taskid, name, desc, date, done);
    }

    /*
    Remove an element from database
     */
    public void removeElement(String task) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry._ID + " = ?",
                new String[]{task});
        db.close();
    }

    public void updateElement(Task newTask) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(TaskContract.TaskEntry.COL_TASK_TITLE, newTask.get_name());
        contentValues.put(TaskContract.TaskEntry.COL_TASK_DATE, newTask.get_date());
        contentValues.put(TaskContract.TaskEntry.COL_TASK_DESC, newTask.get_desc());
        contentValues.put(TaskContract.TaskEntry.COL_TASK_DONE, newTask.get_done());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TaskContract.TaskEntry.TABLE, contentValues,
                TaskContract.TaskEntry._ID + "=" + String.valueOf(newTask.get_id()), null);
        db.close();
    }
}
