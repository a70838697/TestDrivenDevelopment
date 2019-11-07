package com.casper.testDrivenDevelopment.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.casper.testDrivenDevelopment.BookListMainActivity;
import com.casper.testDrivenDevelopment.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    BookListMainActivity.BookAdapter bookAdapter;

    public ListFragment(BookListMainActivity.BookAdapter bookAdapter) {
        this.bookAdapter = bookAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listViewBooks = view.findViewById(R.id.list_view_books);
        listViewBooks.setAdapter(bookAdapter);

        this.registerForContextMenu(listViewBooks);
        return view;
    }
}
