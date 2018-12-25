package com.todolist.crespi.todolist.app.WHITEBOX_Database_DataFlowTest.WHITEBOX_DatabasePersitence;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.todolist.crespi.todolist.app.MainActivity;
import com.todolist.crespi.todolist.db.DBTasks;
import com.todolist.crespi.todolist.db.Task;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WHITEBOX_DatabasePersistenceResult {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void DatabasePersistenceCheck() {

        DBTasks db = MainActivity.db;

        ArrayList<Task> tasks = db.getTasks("", "");

        assertEquals(tasks.isEmpty(), false);
    }

    @After
    public void clean() {
        MainActivity.db.dropDb();
    }
}
