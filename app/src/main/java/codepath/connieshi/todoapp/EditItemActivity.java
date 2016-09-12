package codepath.connieshi.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private static EditText editTextView;
    private static Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String originalText = getIntent().getStringExtra(MainActivity.EXTRA_TEXT_ITEM);
        position = getIntent().getIntExtra(MainActivity.EXTRA_TEXT_POSITION, 0);

        editTextView = (EditText) findViewById(R.id.edit_item);
        editTextView.setText(originalText);
        editTextView.setSelection(originalText.length());
    }

    public void onClickSave(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_item);
        String newInput = editText.getText().toString();

        Intent data = new Intent();
        data.putExtra(MainActivity.EXTRA_TEXT_ITEM, newInput);
        data.putExtra(MainActivity.EXTRA_TEXT_POSITION, position);

        setResult(RESULT_OK, data);
        finish();
    }
}
