package com.casper.testDrivenDevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddToMainActivity extends AppCompatActivity {


    TextView theText;
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_main);
        Button buttonChange= (Button)this.findViewById(R.id.buttonChange);
        theText=(TextView)this.findViewById(R.id.hellotext);
        Button button2= (Button)this.findViewById(R.id.button2);
        edit=(EditText)this.findViewById(R.id.editText);

        //在此处添加代码

    }
}
