package com.casper.testDrivenDevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SimpleListMainActivity extends AppCompatActivity {

    ListView listViewSimpler;
    private String[] booksData = {"软件项目管理案例教程（第4版）", "创新工程实践", "C语言从入门到精通"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list_main);

        listViewSimpler=findViewById(R.id.list_view_simple);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                SimpleListMainActivity.this, android.R.layout.simple_list_item_1, booksData);
        ((ListView) findViewById(R.id.list_view_simple)).setAdapter(adapter);
    }
}
