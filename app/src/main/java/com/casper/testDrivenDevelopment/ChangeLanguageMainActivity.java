package com.casper.testDrivenDevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeLanguageMainActivity extends AppCompatActivity {

    EditText editTextCountry;
    TextView textViewLanguage;
    Button buttonChangeLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language_main);

        editTextCountry =this.findViewById(R.id.edit_text_country);
        textViewLanguage=this.findViewById(R.id.text_view_language);
        buttonChangeLanguage=this.findViewById(R.id.button_change_language);

        buttonChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=ChangeLanguageMainActivity.this.getBaseContext();
                int idLanguage= context.getResources()
                        .getIdentifier("language_"+ editTextCountry.getText()
                                ,"string",context.getPackageName());
                textViewLanguage.setText(idLanguage);
                int idChangeLanguage= context.getResources()
                        .getIdentifier("change_language_"+ editTextCountry.getText()
                                ,"string",context.getPackageName());
                buttonChangeLanguage.setText(idChangeLanguage);
            }
        });
    }
}
