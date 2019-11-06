package com.casper.testDrivenDevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
        String listContent=getIntent().getStringExtra("listcontent");
        if(listContent!=null && !listContent.isEmpty())
        {
            dataList.add(listContent);
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);

        list.setAdapter(arrayAdapter);
        //为列表视图中选中的项添加响应事件
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String result = adapterView.getItemAtPosition(position).toString();//获取选择项的值
                Toast.makeText(AddToListActivity.this, "点击了" + result, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
