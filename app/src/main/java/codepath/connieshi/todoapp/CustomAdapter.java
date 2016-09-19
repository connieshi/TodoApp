package codepath.connieshi.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by connieshi on 9/11/16.
 */
public class CustomAdapter extends ArrayAdapter<ToDoItem> {
    ArrayList<ToDoItem> items;
    Context context;

    public CustomAdapter(Context context, ArrayList<ToDoItem> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToDoItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }

        final TextView textViewName = (TextView) convertView.findViewById(R.id.list_item_string);
        textViewName.setText(item.name);
        textViewName.setTag(position);
        textViewName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = (Integer) textViewName.getTag();
                Intent intent = new Intent(getContext(), EditItemActivity.class);
                intent.putExtra(MainActivity.EXTRA_TEXT_POSITION, position);
                intent.putExtra(MainActivity.EXTRA_TEXT_ITEM, items.get(position));
                ((Activity) getContext()).startActivityForResult(intent, MainActivity.EDIT_TEXT_ACTIVITY_REQUEST_CODE);
            }
        });

        switch (item.priority) {
            case HIGH:
                textViewName.setTextColor(Color.RED); break;
            case MEDIUM:
                textViewName.setTextColor(Color.parseColor("#FF9912")); break;
            case LOW:
                textViewName.setTextColor(Color.YELLOW); break;
            default:
                textViewName.setTextColor(Color.WHITE); break;
        }

        final ImageView checkbox = (ImageView) convertView.findViewById(R.id.checkbox);
        checkbox.setTag(position);
        checkbox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = (Integer) checkbox.getTag();
                items.remove(position);
                notifyDataSetChanged();
                ((MainActivity) getContext()).writeItems();
            }
        });

        TextView textViewDate = (TextView) convertView.findViewById(R.id.list_item_due_date);
        String dateString = (item.dueBy.get(Calendar.MONTH) + 1) + "/"
                + item.dueBy.get(Calendar.DAY_OF_MONTH) + "/"
                + String.valueOf(item.dueBy.get(Calendar.YEAR)).substring(2);
        textViewDate.setText(dateString);

        return convertView;
    }
}
