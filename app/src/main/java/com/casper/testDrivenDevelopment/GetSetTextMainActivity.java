package com.casper.testDrivenDevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GetSetTextMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_set_text_main);

        Button buttonCopyText=this.findViewById(R.id.button_copy_text);
        final EditText editTextToShow=this.findViewById(R.id.edit_text_to_show);
        final TextView textViewShow=this.findViewById(R.id.text_view_show);

        buttonCopyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewShow.setText(editTextToShow.getText());
            }
        });
    }
}
