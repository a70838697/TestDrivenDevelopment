package com.casper.testDrivenDevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class HelloAndroidMainActivity extends AppCompatActivity {
    private TextView textViewHelloWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_android_main);

        textViewHelloWorld= (TextView) findViewById(R.id.text_view_hello_world);
        textViewHelloWorld.setText(R.string.hello_android);
    }

}
