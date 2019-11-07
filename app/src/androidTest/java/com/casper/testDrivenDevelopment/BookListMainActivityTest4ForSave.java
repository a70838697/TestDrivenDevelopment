package com.casper.testDrivenDevelopment;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

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

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BookListMainActivityTest4ForSave {

    @Rule
    public ActivityTestRule<BookListMainActivity> mActivityTestRule = new ActivityTestRule<>(BookListMainActivity.class);

    private BookSaver bookKeeper;

    @Before
    public void setup() {
        bookKeeper = new BookSaver(ApplicationProvider.getApplicationContext());
        bookKeeper.load();
        List<Book> listBooks = mActivityTestRule.getActivity().getListBooks();
        listBooks.clear();
        listBooks.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        listBooks.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
        mActivityTestRule.finishActivity();
        mActivityTestRule.launchActivity(new Intent());
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(1000);
        mActivityTestRule.finishActivity();
        Thread.sleep(1000);
        bookKeeper.save();
    }

    @Test
    public void bookListMainActivityTest4ForSave() {
        BookSaver bookTester = new BookSaver(ApplicationProvider.getApplicationContext());
        bookTester.load();

        List<Book> listBooks = mActivityTestRule.getActivity().getListBooks();
        int bookCount = listBooks.size();
        int insertPosition = 0;

        String testText = (int) (Math.random() * 10000) + "name";
        /*检查要测试的对象不存在*/
        onView(
                allOf(withId(R.id.text_view_book_title), withText(testText))
        ).check(doesNotExist());

        /*第insertPosition个对象长按*/
        onView(
                allOf(childAtPosition(
                        withId(R.id.list_view_books),
                        insertPosition),
                        isDisplayed())
        ).perform(longClick());

        /*出现新编菜单*/
        onView(allOf(withId(android.R.id.title), withText(listBooks.get(insertPosition).getTitle())))
                .check(matches(isDisplayed()));
        onView(allOf(withId(android.R.id.title), withText("新编"), isDisplayed()))
                .perform(click());

        /*新窗口中的内容*/
        onView(
                allOf(withId(R.id.edit_text_book_name), isDisplayed())
        ).perform(click());

        /*修改编辑框内容*/
        onView(allOf(withId(R.id.edit_text_book_name), isDisplayed())
        ).perform(replaceText(testText), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_ok), withText("Ok"), isDisplayed())
        ).perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.text_view_book_title),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.list_view_books),
                                insertPosition),
                        1),
                withText(testText)))
                .check(matches(isDisplayed()));
        /*-----------------------测试删除-----------------------------*/
        int deletePosition=2;
        /*第一个对象长按*/
        onView(
                allOf(childAtPosition(
                        withId(R.id.list_view_books),
                        deletePosition),
                        isDisplayed()))
                .perform(longClick());

        /*显示并点击删除菜单*/
        onView(allOf(withId(android.R.id.title), withText("删除"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(1000);
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("您确定要删除这条吗？"))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity()
                        .getWindow()
                        .getDecorView())))
                .check(matches(isDisplayed()));

        String beforeDeleteTestText=listBooks.get(deletePosition).getTitle();
        int beforeDeleteSize=listBooks.size();
        onView(allOf(withId(android.R.id.button1), withText("确定")))
                .perform(scrollTo(), click());
        Assert.assertEquals(beforeDeleteSize-1,listBooks.size());
        onView(allOf(withId(R.id.text_view_book_title), withText(beforeDeleteTestText)))
                .check(doesNotExist());


        try {
            Thread.sleep(1000);
        }catch(InterruptedException e2){  }
        onView(withText("删除成功"))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        mActivityTestRule.finishActivity();
        mActivityTestRule.launchActivity(new Intent());
        List<Book> listBooks2 = mActivityTestRule.getActivity().getListBooks();
        /*检测对象已变化*/
        Assert.assertNotEquals(listBooks,listBooks2);
        Assert.assertEquals(listBooks.size(),listBooks2.size());
        /*检测对象的内容没变化*/
        for(int i=0;i<listBooks.size();++i)
        {
            Assert.assertEquals(listBooks.get(i).getTitle(),listBooks2.get(i).getTitle());
            Assert.assertEquals(listBooks.get(i).getCoverResourceId(),listBooks2.get(i).getCoverResourceId());
        }
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
