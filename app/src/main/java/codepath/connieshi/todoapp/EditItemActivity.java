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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String originalText = getIntent().getStringExtra(MainActivity.EXTRA_TEXT_ITEM);
        position = getIntent().getIntExtra(MainActivity.EXTRA_TEXT_POSITION, 0);

        editTextView = (EditText) findViewById(R.id.edit_item);
        editTextView.setText(originalText);
        editTextView.setSelection(originalText.length());

        dpView = (DatePicker) findViewById(R.id.datePicker);
        Calendar c = Calendar.getInstance();
        dpView.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        radioGroupView = (RadioGroup) findViewById(R.id.radios);
    }

    public void onClickSave(View view) {
        String newInput = editTextView.getText().toString();

        Calendar calendar = Calendar.getInstance();
        calendar.set(dpView.getYear(), dpView.getMonth(), dpView.getDayOfMonth());

        int checkedRadioButton = radioGroupView.getCheckedRadioButtonId();
        ToDoItem.Priority selectedPriority = ToDoItem.Priority.HIGH;

        switch (checkedRadioButton) {
            case R.id.high:
                selectedPriority = ToDoItem.Priority.HIGH;
                break;
            case R.id.medium:
                selectedPriority = ToDoItem.Priority.MEDIUM;
                break;
            case R.id.low:
                selectedPriority = ToDoItem.Priority.LOW;
                break;
        }

        Intent data = new Intent();
        data.putExtra(MainActivity.EXTRA_TEXT_ITEM, newInput);
        data.putExtra(MainActivity.EXTRA_TEXT_POSITION, position);
        data.putExtra(MainActivity.EXTRA_DATE, calendar);
        data.putExtra(MainActivity.EXTRA_PRIORITY, selectedPriority);

        setResult(RESULT_OK, data);
        finish();
    }
}
