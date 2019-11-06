package com.casper.testDrivenDevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class AddToListActivity extends AppCompatActivity {
    ListView list;
    final private ArrayList<String> dataList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);
        list=(ListView)this.findViewById(R.id.listview01);
        dataList.add("轻轻的");
        dataList.add("我走了");

        //在此处添加代码

    }

}
