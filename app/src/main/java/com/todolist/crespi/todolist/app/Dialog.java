package com.todolist.crespi.todolist.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.todolist.crespi.todolist.R;
import com.todolist.crespi.todolist.db.DBTasks;

public class Dialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    public void closeAdd(View view) {
        finishWithCode(1);
    }

    /*
    Add the task button
     */
    public void addTask(View view) {

        EditText taskName = findViewById(R.id.editName);
        EditText taskDesc = findViewById(R.id.editDesc);
        DatePicker datePicker = findViewById(R.id.datePicker);

        DBTasks db = MainActivity.getDB();

        String name = String.valueOf(taskName.getText());
        String desc = String.valueOf(taskDesc.getText());
        String date =
                String.valueOf(datePicker.getDayOfMonth()) + "/" +
                String.valueOf(datePicker.getMonth() + 1) + "/" +
                String.valueOf(datePicker.getYear());

        db.addElement(name, desc, date);

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
