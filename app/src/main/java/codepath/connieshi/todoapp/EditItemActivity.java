package codepath.connieshi.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity {

    private EditText editTextView;
    private DatePicker dpView;
    private RadioGroup radioGroupView;
    private Integer position;
    private ToDoItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item = getIntent().getParcelableExtra(MainActivity.EXTRA_TEXT_ITEM);
        position = getIntent().getIntExtra(MainActivity.EXTRA_TEXT_POSITION, 0);

        editTextView = (EditText) findViewById(R.id.edit_item);
        editTextView.setText(item.name);
        editTextView.setSelection(item.name.length());

        dpView = (DatePicker) findViewById(R.id.datePicker);
        dpView.updateDate(item.dueBy.get(Calendar.YEAR),
                item.dueBy.get(Calendar.MONTH),
                item.dueBy.get(Calendar.DAY_OF_MONTH));

        radioGroupView = (RadioGroup) findViewById(R.id.radios);
        switch (item.priority) {
            case HIGH:
                radioGroupView.check(R.id.high);
                break;
            case MEDIUM:
                radioGroupView.check(R.id.medium);
                break;
            case LOW:
                radioGroupView.check(R.id.low);
                break;
            default:
                radioGroupView.check(R.id.high);
        }
    }

    public void onClickSave(View view) {
        item.name = editTextView.getText().toString();
        item.dueBy.set(dpView.getYear(), dpView.getMonth(), dpView.getDayOfMonth());

        int checkedRadioButton = radioGroupView.getCheckedRadioButtonId();
        switch (checkedRadioButton) {
            case R.id.high:
                item.priority = ToDoItem.Priority.HIGH;
                break;
            case R.id.medium:
                item.priority = ToDoItem.Priority.MEDIUM;
                break;
            case R.id.low:
                item.priority = ToDoItem.Priority.LOW;
                break;
        }

        Intent data = new Intent();
        data.putExtra(MainActivity.EXTRA_TEXT_ITEM, item);
        data.putExtra(MainActivity.EXTRA_TEXT_POSITION, position);

        setResult(RESULT_OK, data);
        finish();
    }
}
