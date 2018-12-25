package com.todolist.crespi.todolist.app.WHITEBOX_Database_DataFlowTest.WHITEBOX_DatabasePersitence;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.todolist.crespi.todolist.app.MainActivity;
import com.todolist.crespi.todolist.db.DBTasks;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WHITEBOX_DatabasePersistenceInit {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void initializeDatabase() {
        DBTasks db = MainActivity.db;
        db.dropDb();

        db.addElement("Persistence Test", "Persistence", "");
    }
}
