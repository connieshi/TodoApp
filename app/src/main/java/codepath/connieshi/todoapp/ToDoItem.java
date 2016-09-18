package codepath.connieshi.todoapp;

import java.util.Calendar;

/**
 * Created by connieshi on 9/11/16.
 */
public class ToDoItem {
    enum Priority {
        HIGH, MEDIUM, LOW;
    }

    public String name;
    public Calendar dueBy;
    public Priority priority;

    public ToDoItem(String name, Calendar dueBy, Priority priority) {
        this.name = name;
        this.dueBy = dueBy;
        this.priority = priority;
    }
}
