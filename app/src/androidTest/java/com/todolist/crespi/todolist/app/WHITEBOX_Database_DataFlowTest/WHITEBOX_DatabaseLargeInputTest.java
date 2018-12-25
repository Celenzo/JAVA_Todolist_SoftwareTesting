package com.todolist.crespi.todolist.app.WHITEBOX_Database_DataFlowTest;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.todolist.crespi.todolist.app.MainActivity;
import com.todolist.crespi.todolist.db.DBTasks;
import com.todolist.crespi.todolist.db.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WHITEBOX_DatabaseLargeInputTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void cleanDB() {
        MainActivity.db.dropDb();
    }

    @Test
    public void LargeInputTest() {

        StringBuilder largeString = new StringBuilder();

        for (int i = 0; i < 1000; i++) {
            largeString.append(" Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ");
        }

        DBTasks db = MainActivity.db;
        db.addElement(largeString.toString(), largeString.toString(), largeString.toString());

        ArrayList<Task> tasks = db.getTasks("", "");

        assertEquals(tasks.isEmpty(), false);
    }

    @After
    public void clean() {
        MainActivity.db.dropDb();
    }
}
