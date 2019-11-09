package com.casper.testDrivenDevelopment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.casper.testDrivenDevelopment.data.Book;
import com.casper.testDrivenDevelopment.data.BookFragmentAdapter;
import com.casper.testDrivenDevelopment.data.BookSaver;
import com.casper.testDrivenDevelopment.view.GameFragment;
import com.casper.testDrivenDevelopment.view.ListFragment;
import com.casper.testDrivenDevelopment.view.MapFragment;
import com.casper.testDrivenDevelopment.view.WebFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    ListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        BookFragmentAdapter myPageAdapter = new BookFragmentAdapter(getSupportFragmentManager());
        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        listFragment=new ListFragment();
        datas.add(listFragment);
        datas.add(new WebFragment());
        datas.add(new MapFragment());
        datas.add(new GameFragment());
        myPageAdapter.setData(datas);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("图书");
        titles.add("新闻");
        titles.add("卖家");
        titles.add("游戏");
        myPageAdapter.setTitles(titles);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(myPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    public List<Book> getListBooks() {
        return listFragment.getListBooks();
    }
}
