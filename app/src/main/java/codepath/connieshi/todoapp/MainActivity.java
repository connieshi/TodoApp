package codepath.connieshi.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT_ITEM = "TEXT";
    public static final String EXTRA_TEXT_POSITION = "POSITION";
    private static final Integer EDIT_TEXT_ACTIVITY_REQUEST_CODE = 1;

    @NonNull private static ArrayList<String> items;
    @NonNull private static ArrayAdapter<String> itemsAdapter;
    private static ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        items = new ArrayList<>();
        readItems();

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
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
                intent.putExtra(EXTRA_TEXT_ITEM, items.get(pos));
                intent.putExtra(EXTRA_TEXT_POSITION, pos);
                startActivityForResult(intent, EDIT_TEXT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onClickAdd(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_text);
        String newInput = editText.getText().toString();
        itemsAdapter.add(newInput);
        editText.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_ACTIVITY_REQUEST_CODE) {
            String edittedText = data.getStringExtra(EXTRA_TEXT_ITEM);
            int position = data.getIntExtra(EXTRA_TEXT_POSITION, 0);

            items.set(position, edittedText);
            itemsAdapter.notifyDataSetChanged();
            writeItems();

            String toastString = "Changed to " + edittedText;
            Toast.makeText(this, toastString, Toast.LENGTH_SHORT).show();
        }
    }
}
