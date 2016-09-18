package codepath.connieshi.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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

        final ImageView checkbox = (ImageView) convertView.findViewById(R.id.checkbox);
        checkbox.setTag(position);
        checkbox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = (Integer) checkbox.getTag();
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
