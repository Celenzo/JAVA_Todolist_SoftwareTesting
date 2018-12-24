package com.todolist.crespi.todolist.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.todolist.crespi.todolist.R;
import com.todolist.crespi.todolist.db.DBTasks;
import com.todolist.crespi.todolist.db.Task;

public class TaskDescription extends AppCompatActivity {

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description);

        Intent intent = getIntent();
        String id = intent.getStringExtra("TASK_CLICKED_ID");
        DBTasks db = MainActivity.getDB();
        task = db.getOneTask(id);

        TextView name = findViewById(R.id.chName);
        TextView desc = findViewById(R.id.chDesc);
        DatePicker date = findViewById(R.id.chDate);
        name.setText(task.get_name());
        desc.setText(task.get_desc());

        switchStatusBtn();

        String[] SDate = task.get_date().split("/");


        date.updateDate(Integer.parseInt(SDate[2]),
                        Integer.parseInt(SDate[1]) - 1,
                        Integer.parseInt(SDate[0]));
    }

    public void backToMain(View view) {
        finishWithCode(1);
    }

    public void taskChangeStatus(View view) {
        task.set_done(task.get_done() == 0 ? 1 : 0);
        switchStatusBtn();
    }

    public void switchStatusBtn() {
        Button btn = findViewById(R.id.statusChanger);
        btn.setText(task.get_done() == 0 ? "To do" : "Done");
    }

    public void saveTaskEdit(View view) {
        EditText taskName = findViewById(R.id.chName);
        EditText taskDesc = findViewById(R.id.chDesc);
        DatePicker datePicker = findViewById(R.id.chDate);

        DBTasks db = MainActivity.getDB();

        String name = String.valueOf(taskName.getText());
        String desc = String.valueOf(taskDesc.getText());
        String date = String.valueOf(datePicker.getDayOfMonth()) + "/" +
                        String.valueOf(datePicker.getMonth() + 1) + "/" +
                        String.valueOf(datePicker.getYear());

        Task newTask = new Task(this.task.get_id(), name, desc, date, this.task.get_done());

        db.updateElement(newTask);
        finishWithCode(0);
    }

    public void deleteTask(View view) {
        DBTasks db = MainActivity.getDB();
        db.removeElement(String.valueOf(task.get_id()));
        finishWithCode(0);
    }

    private void finishWithCode(int i) {
        Intent intent = new Intent();
        intent.putExtra("NB_1", 0);
        intent.putExtra("NB_2", i);
        setResult(RESULT_OK, intent);

        finish();
    }
}
