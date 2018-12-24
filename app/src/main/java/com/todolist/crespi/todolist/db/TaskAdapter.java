package com.todolist.crespi.todolist.db;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.todolist.crespi.todolist.R;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public TaskAdapter(Context context, int layoutId, int textViewResourceId, ArrayList<Task> items) {
        super(context, textViewResourceId, items);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        ViewHolder id = new ViewHolder();
        ViewHolder status = new ViewHolder();

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.item, parent, false);

            id.itemView = convertView.findViewById(R.id.task_dbid);
            viewHolder.itemView = convertView.findViewById(R.id.task_title);
            status.itemView = convertView.findViewById(R.id.taskStatus);

            convertView.setTag(id);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            id = (ViewHolder) convertView.getTag();
            status = (ViewHolder) convertView.getTag();
        }

        Task item = getItem(position);
        if (item!= null) {
            viewHolder.itemView.setText(String.format("%s", item.get_name()));
            status.itemView.setText(String.format("%s", item.get_done() == 0 ? "To do" : "Done"));
            status.itemView.setBackgroundColor(item.get_done() == 0 ? Color.parseColor("#e57f19") : Color.parseColor("#27c644"));
            id.itemView.setText(String.format("%s", item.get_id()));
        }

        return convertView;
    }
}