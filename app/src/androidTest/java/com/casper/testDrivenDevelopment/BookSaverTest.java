package com.casper.testDrivenDevelopment;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookSaverTest {
    private BookSaver bookKeeper;
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        bookKeeper = new BookSaver(context);
        bookKeeper.load();
    }

    @After
    public void tearDown() throws Exception {
        bookKeeper.save();
    }

    @Test
    public void getBooks() {
        Assert.assertNotNull(bookKeeper.getBooks());
        BookSaver bookSaver = new BookSaver(context);
        Assert.assertNotNull(bookKeeper.getBooks());
        Assert.assertEquals(0, bookSaver.getBooks().size());
    }

    @Test
    public void saveThenLoad() {
        BookSaver bookSaverTest = new BookSaver(context);
        Assert.assertEquals(0, bookSaverTest.getBooks().size());
        Book book = new Book("test book",  123);
        book.setPrice(1.33);
        bookSaverTest.getBooks().add(book);
        book = new Book("test book2",  124);
        book.setPrice(1.34);
        bookSaverTest.getBooks().add(book);
        bookSaverTest.save();

        BookSaver bookSaverLoader = new BookSaver(context);
        bookSaverLoader.load();
        Assert.assertEquals(bookSaverTest.getBooks().size(), bookSaverLoader.getBooks().size());
        for (int i = 0; i < bookSaverTest.getBooks().size(); i++) {
            Book bookThis = bookSaverTest.getBooks().get(i);
            Book bookThat = bookSaverLoader.getBooks().get(i);
            Assert.assertEquals(bookThat.getCoverResourceId(), bookThis.getCoverResourceId());
            Assert.assertEquals(bookThat.getTitle(), bookThis.getTitle());
            Assert.assertEquals(bookThat.getPrice(), bookThis.getPrice(), 1e-4);
        }
    }

    @Test
    public void saveEmptyThenLoad() {
        BookSaver bookSaverTest = new BookSaver(context);
        Assert.assertEquals(0, bookSaverTest.getBooks().size());
        bookSaverTest.save();

        BookSaver bookSaverLoader = new BookSaver(context);
        bookSaverLoader.load();
        Assert.assertEquals(bookSaverTest.getBooks().size(), bookSaverLoader.getBooks().size());
    }
}