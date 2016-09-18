package codepath.connieshi.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT_ITEM = "TEXT";
    public static final String EXTRA_TEXT_POSITION = "POSITION";
    public static final String EXTRA_DATE = "DATE";
    public static final String EXTRA_PRIORITY = "PRIORITY";

    public static final Integer EDIT_TEXT_ACTIVITY_REQUEST_CODE = 1;

    @NonNull private static ArrayList<ToDoItem> items;
    @NonNull private static CustomAdapter itemsAdapter;
    private static ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        items = new ArrayList<>();
        readItems();

        itemsAdapter = new CustomAdapter(this, items);
        listView.setAdapter(itemsAdapter);

        setListViewDeleteListener();
        setListViewEditListener();
    }

    private void setListViewDeleteListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private void setListViewEditListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra(EXTRA_TEXT_ITEM, items.get(pos).name);
                intent.putExtra(EXTRA_TEXT_POSITION, pos);
                startActivityForResult(intent, EDIT_TEXT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onClickAdd(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_text);
        String newInput = editText.getText().toString();

        ToDoItem newItem = new ToDoItem(newInput, Calendar.getInstance(), ToDoItem.Priority.HIGH);
        itemsAdapter.add(newItem);
        editText.setText("");
        writeItems();
    }

    public void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todoapp.txt");
        try {
            items.clear();

            List<String> stringItems = new ArrayList<>(FileUtils.readLines(todoFile));

            for (String str : stringItems) {
                String[] splitted = str.split(",");

                String name = splitted[0];

                String dateString = splitted[1];
                String[] yearMonthDay = dateString.split("-");
                Calendar date = Calendar.getInstance();
                date.set(Integer.parseInt(yearMonthDay[0]),
                        Integer.parseInt(yearMonthDay[1]),
                        Integer.parseInt(yearMonthDay[2]));

                String priority = splitted[2];
                ToDoItem.Priority priority_enum = ToDoItem.Priority.valueOf(priority);

                items.add(new ToDoItem(name, date, priority_enum));
            }
        } catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    public void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todoapp.txt");
        try {
            ArrayList<String> stringItems = new ArrayList<>();
            for (ToDoItem item : items) {
                String dateSerialized = item.dueBy.get(Calendar.YEAR) + "-"
                        + item.dueBy.get(Calendar.MONTH) + "-"
                        + item.dueBy.get(Calendar.DAY_OF_MONTH);
                stringItems.add(item.name + "," + dateSerialized + "," + item.priority);
            }
            FileUtils.writeLines(todoFile, stringItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_ACTIVITY_REQUEST_CODE) {
            String edittedText = data.getStringExtra(EXTRA_TEXT_ITEM);
            Calendar dueByDate = data.getParcelableExtra(EXTRA_DATE);
            ToDoItem.Priority priority = data.getParcelableExtra(EXTRA_PRIORITY);
            int position = data.getIntExtra(EXTRA_TEXT_POSITION, 0);

            ToDoItem item = new ToDoItem(edittedText, dueByDate, priority);

            items.set(position, item);
            itemsAdapter.notifyDataSetChanged();
            writeItems();

            String toastString = "Changed " + edittedText;
            Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
        }
    }
}
