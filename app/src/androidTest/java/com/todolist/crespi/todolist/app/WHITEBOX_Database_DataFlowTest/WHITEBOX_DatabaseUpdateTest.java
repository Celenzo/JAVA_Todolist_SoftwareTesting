package com.todolist.crespi.todolist.app.WHITEBOX_Database_DataFlowTest;

import android.content.res.Resources;
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
public class WHITEBOX_DatabaseUpdateTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private DBTasks db;

    @Before
    public void getDb() {
        this.db = MainActivity.db;
        this.db.dropDb();
    }

    @Test(expected = Resources.NotFoundException.class)
    public void InvalidUpdate() {
        Task nonExitingTask = new Task(42, "Non Exiting Task", "", "", 5);
        this.db.updateElement(nonExitingTask);
    }

    @Test
    public void NormalUpdate() {
        this.db.addElement("Normal Update Task", "", "");
        ArrayList<Task> tasks = this.db.getTasks("", "");

        assertEquals(tasks.isEmpty(), false);

        Task updatedTask = new Task(
                tasks.get(0).get_id(),
                "Updated Task Name",
                "Updated Task Description",
                "Updated Task Date",
                1
        );

        this.db.updateElement(updatedTask);

        Task newStoredTask = this.db.getOneTask(String.valueOf(updatedTask.get_id()));

        assertEquals(newStoredTask.get_name(), "Updated Task Name");
    }

    @After
    public void clean() {
        this.db.dropDb();
    }
}
