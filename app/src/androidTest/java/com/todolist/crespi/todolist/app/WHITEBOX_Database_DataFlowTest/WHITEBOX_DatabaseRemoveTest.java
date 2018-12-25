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

/**
 * Created by moi.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WHITEBOX_DatabaseRemoveTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private DBTasks db;


    @Before
    public void prepareDB() {
        this.db = MainActivity.db;
        db.dropDb();
    }

    @Test(expected = Resources.NotFoundException.class)
    public void InvalidDelete() {
        this.db.removeElement("Non Existing Element");
    }

    @Test
    public void NormalDelete() {
        this.db.addElement("Normal Element", "Normal Description", "Not so normal Date");
        ArrayList<Task> tasks = this.db.getTasks("", "");

        assertEquals(tasks.isEmpty(), false);

        this.db.removeElement(String.valueOf(tasks.get(0).get_id()));

        tasks = this.db.getTasks("", "");

        assertEquals(tasks.isEmpty(), true);
    }

    @After
    public void clean() {
        this.db.dropDb();
    }
}
