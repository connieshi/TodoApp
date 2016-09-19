package codepath.connieshi.todoapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * A to do item with name, priority, and due date.
 */
public class ToDoItem implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeSerializable(dueBy);
        out.writeSerializable(priority);
    }

    public static final Parcelable.Creator<ToDoItem> CREATOR
            = new Parcelable.Creator<ToDoItem>() {
        public ToDoItem createFromParcel(Parcel in) {
            return new ToDoItem(in.readString(),
                    (Calendar) in.readSerializable(),
                    (Priority) in.readSerializable());
        }

        public ToDoItem[] newArray(int size) {
            return new ToDoItem[size];
        }
    };
}
