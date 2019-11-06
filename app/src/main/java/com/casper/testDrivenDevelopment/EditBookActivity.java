package com.casper.testDrivenDevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditBookActivity extends AppCompatActivity {
    private Button buttonOK, buttonCancel;
    private EditText editTextBookTitle, editTextBookPrice;
    private int insertPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        buttonOK = findViewById(R.id.button_ok);
        buttonCancel = findViewById(R.id.button_cancel);
        editTextBookTitle = findViewById(R.id.edit_text_book_name);

        String bookName=getIntent().getStringExtra("book_name");
        if(null!=bookName)editTextBookTitle.setText(bookName);
        insertPosition = getIntent().getIntExtra("position", 0);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("book_name", editTextBookTitle.getText().toString());
                intent.putExtra("position",insertPosition);
                setResult(RESULT_OK, intent);
                EditBookActivity.this.finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBookActivity.this.finish();
            }
        });

    }
}

