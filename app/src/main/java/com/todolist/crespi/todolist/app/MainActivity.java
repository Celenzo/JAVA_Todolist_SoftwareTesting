package com.todolist.crespi.todolist.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.todolist.crespi.todolist.R;
import com.todolist.crespi.todolist.db.DBTasks;
import com.todolist.crespi.todolist.db.Task;
import com.todolist.crespi.todolist.db.TaskAdapter;
import com.todolist.crespi.todolist.db.TaskContract;

import java.util.ArrayList;

//TODO: Compile
//TODO: UX/UI design
//TODO: Espresso testing
//TODO: Documentation

public class MainActivity extends AppCompatActivity {

    public static DBTasks db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBTasks(this);

        addAllToView(null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                addTask();
                return true;

            case R.id.sortBtn:
                showOptions();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showOptions() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(TaskContract.TaskEntry.COL_TASK_TITLE);
        arrayList.add(TaskContract.TaskEntry.COL_TASK_DATE);
        arrayList.add(TaskContract.TaskEntry.COL_TASK_DESC);
        final Spinner orderMode = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        orderMode.setAdapter(spinnerArrayAdapter);
        linearLayout.addView(orderMode);
        final EditText editText = new EditText(this);
        editText.setHint("Enter a word to match");
        linearLayout.addView(editText);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Options")
                .setView(linearLayout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String orderNode = String.valueOf(orderMode.getSelectedItem()) + " COLLATE NOCASE ASC";
                        String where = null;
                        if (editText.getText() != null) {
                            where = TaskContract.TaskEntry.COL_TASK_TITLE + " LIKE '%" + editText.getText() + "%'";
                        }
                        addAllToView(where, orderNode);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int rq, int rc, Intent data) {
        addAllToView(null, null);
    }

    /*
    Add all element to the ListView
     */
    public void addAllToView(String where, String orderBy) {
        TaskAdapter arrayAdapter;
        ArrayList<Task> taskList = db.getTasks(where, orderBy);
        ListView listView = findViewById(R.id.list_todo);

        arrayAdapter = new TaskAdapter(this, R.layout.item,
                    R.id.task_title, taskList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long l) {
                Intent intent = new Intent(MainActivity.this, TaskDescription.class);

                String TAG = "Adapter log :";
                TextView idText = (v.findViewById(R.id.task_dbid));
                String id = String.valueOf(idText.getText());
                Log.d(TAG, "id is " + id);
                intent.putExtra("TASK_CLICKED_ID", id);
                startActivityForResult(intent, 0);
            }
        });
    }

    public void addTask() {
        Intent intent = new Intent(this, Dialog.class);

        startActivityForResult(intent, 0);
    }

    public static DBTasks getDB() {
        return db;
    }
}
