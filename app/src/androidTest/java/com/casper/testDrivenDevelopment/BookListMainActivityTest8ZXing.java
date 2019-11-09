package com.casper.testDrivenDevelopment;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.casper.testDrivenDevelopment.data.Book;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BookListMainActivityTest8ZXing {

    @Rule
    public ActivityTestRule<BookListMainActivity> mActivityTestRule = new ActivityTestRule<>(BookListMainActivity.class);
    ArrayList<Book> listBooksBackup;

    @Before
    public void setup()
    {
        listBooksBackup=new ArrayList<Book>();
        List<Book> listBooks = mActivityTestRule.getActivity().getListBooks();
        for (Book book:listBooks) {
            Book book1=new Book(book.getTitle(),book.getCoverResourceId());
            listBooksBackup.add(book1);
        }
        listBooks.clear();
        listBooks.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        listBooks.add(new Book("创新工程实践", R.drawable.book_no_name));
        listBooks.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
        mActivityTestRule.finishActivity();
        mActivityTestRule.launchActivity(new Intent());
    }
    @After
    public void tearDown() throws InterruptedException {
        List<Book> listBooks = mActivityTestRule.getActivity().getListBooks();
        listBooks.clear();
        for (Book book:listBooksBackup) {
            Book book1=new Book(book.getTitle(),book.getCoverResourceId());
            listBooks.add(book1);
        }
        Thread.sleep(1000);
        mActivityTestRule.finishActivity();
    }
    @Test
    public void bookListMainActivityTest4() {
        List<Book> listBooks = mActivityTestRule.getActivity().getListBooks();
        String bookTitle="AndroidStudio应用程序设计(第2版微课版21世纪高等学校计算机类课程创新规划教材)";
        Assert.assertEquals(3,listBooks.size());
        for (Book book:listBooks) {
            Assert.assertNotEquals(bookTitle,book.getTitle());
        }
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.floating_action_button),
                        childAtPosition(
                                withParent(withId(R.id.view_pager)),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(4,listBooks.size());
        Assert.assertEquals(bookTitle,listBooks.get(3).getTitle());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
