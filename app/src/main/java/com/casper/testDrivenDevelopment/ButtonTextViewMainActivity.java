package com.casper.testDrivenDevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ButtonTextViewMainActivity extends AppCompatActivity {

    private Button buttonHelloEn,buttonHelloCz;
    private TextView textViewHelloWorld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_text_view_main);

        buttonHelloCz=this.findViewById(R.id.button_hello_cn);
        buttonHelloEn=this.findViewById(R.id.button_hello_en);
        textViewHelloWorld=this.findViewById(R.id.text_wiew_hello_world);

        buttonHelloCz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewHelloWorld.setText(buttonHelloCz.getText());
            }
        });

        buttonHelloEn.setOnClickListener(new ButtonCzOnClick());
    }

    private class ButtonCzOnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            textViewHelloWorld.setText(((Button)view).getText());
        }
    }
}
