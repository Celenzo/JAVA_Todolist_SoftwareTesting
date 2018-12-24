package com.todolist.crespi.todolist.db;

public class Task {

    private String  _desc;
    private String  _name;
    private String  _date;
    private int     _done;
    private int     _id;

    public Task(int id, String name, String desc, String date, int done) {
        _id = id;
        _name = name;
        _desc = desc;
        _done = done;
        _date = date;
    }

    @Override
    public String toString() {
        return _name;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_desc() {
        return _desc;
    }

    public String get_date() {
        return _date;
    }

    public int get_done() {
        return _done;
    }

    public void set_done(int done) { _done = done; }
}
