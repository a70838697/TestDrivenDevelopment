package com.casper.testDrivenDevelopment.data;
import android.content.Context;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BookSaver {
    Context context;
    ArrayList<Book> books = new ArrayList<Book>();

    public BookSaver(Context context) {
        this.context = context;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void save() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("Serializable.txt", Context.MODE_PRIVATE)
            );
            outputStream.writeObject(books);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Book> load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("Serializable.txt"));
            books = (ArrayList<Book>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

}