package com.casper.testDrivenDevelopment;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
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
public class BookListMainActivityTest2 {

    @Rule
    public ActivityTestRule<BookListMainActivity> mActivityTestRule = new ActivityTestRule<>(BookListMainActivity.class);

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

    private static Matcher<View> hasDrawableResource(final int resourceId) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Imageview has a resource id of " + resourceId);
            }

            @Override
            public boolean matchesSafely(View view) {
                ImageView imageView = (ImageView) view;
                return imageView.getDrawable() != null &&
                        imageView.getDrawable().getCurrent().getConstantState().equals(imageView.getResources().getDrawable(resourceId).getConstantState());
            }
        };
    }

    @Test
    public void testContextMenuNewAndDelete() {
        List<Book> listBooks = mActivityTestRule.getActivity().getListBooks();
        int bookCount=listBooks.size();

        String testText = "无名书籍";
        int resourceId = R.drawable.book_no_name;

        /*检查要测试的对象不存在*/
        ViewInteraction theNotExistTextView = onView(
                allOf(withId(R.id.text_view_book_title), withText(testText)));
        theNotExistTextView.check(
                doesNotExist());

        Assert.assertNotEquals(testText,listBooks.get(0).getTitle());
        Assert.assertNotEquals(resourceId,listBooks.get(0).getCoverResourceId());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.image_view_book_cover),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_view_books),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(not(hasDrawableResource(resourceId))));

        /*新建菜单不存在*/
        ViewInteraction textViewMenu = onView(
                allOf(withId(android.R.id.title), withText("添加"), isDisplayed()));
        textViewMenu.check(doesNotExist());

        /*第一个对象长按*/
        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        withId(R.id.list_view_books),
                        0),
                        isDisplayed()));
        linearLayout.perform(longClick());

        /*出现新建菜单*/
        onView(
                allOf(withId(android.R.id.title), withText(listBooks.get(0).getTitle()))).check(matches(isDisplayed()));
        ViewInteraction textView = onView(
                allOf(withId(android.R.id.title), withText("添加"), isDisplayed()));
        textView.perform(click());
        ViewInteraction theExistTextView = onView(
                allOf(withId(R.id.text_view_book_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_view_books),
                                        0),
                                1),
                        withText(testText)));
        theExistTextView.check(matches(isDisplayed()));

        Assert.assertEquals(testText,listBooks.get(0).getTitle());
        Assert.assertEquals(resourceId,listBooks.get(0).getCoverResourceId());
        Assert.assertEquals(bookCount+1,listBooks.size());

        imageView = onView(
                allOf(withId(R.id.image_view_book_cover),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_view_books),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches((hasDrawableResource(listBooks.get(0).getCoverResourceId()))));


        /*删除菜单不存在*/
        ViewInteraction textViewMenu1 = onView(
                allOf(withId(android.R.id.title), withText("删除"), isDisplayed()));
        textViewMenu1.check(doesNotExist());
        /*第一个对象长按*/
        ViewInteraction linearLayout1 = onView(
                allOf(childAtPosition(
                        withId(R.id.list_view_books),
                        0),
                        isDisplayed()));
        linearLayout1.perform(longClick());

        /*出现删除菜单*/
        onView(
                allOf(withId(android.R.id.title), withText("删除"), isDisplayed()))
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

        ViewInteraction textView2 = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.widget.TextView.class), withText("询问"),
                        isDisplayed()))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity()
                .getWindow()
                .getDecorView())))
                ;
        textView2.check(matches(withText("询问")));

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.message), withText("您确定要删除这条吗？"),
                        isDisplayed()));
        textView4.check(matches(withText("您确定要删除这条吗？")));

        ViewInteraction appCompatButton = onView(allOf(withId(android.R.id.button2), withText("取消")))
                        .perform(scrollTo(), click());
        Assert.assertEquals(testText,listBooks.get(0).getTitle());
        Assert.assertEquals(resourceId,listBooks.get(0).getCoverResourceId());
        Assert.assertEquals(bookCount+1,listBooks.size());

        /*-----------------------测试删除-----------------------------*/
        /*第一个对象长按*/
        onView(
                allOf(childAtPosition(
                        withId(R.id.list_view_books),
                        0),
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

        onView(allOf(withId(android.R.id.button1), withText("确定")))
                .perform(scrollTo(), click());
        Assert.assertNotEquals(testText,listBooks.get(0).getTitle());
        Assert.assertNotEquals(resourceId,listBooks.get(0).getCoverResourceId());
        Assert.assertEquals(bookCount,listBooks.size());
        onView(allOf(withId(R.id.text_view_book_title), withText(testText)))
                .check(
                doesNotExist());


        try {
            Thread.sleep(1000);
        }catch(InterruptedException e2){  }
        onView(withText("删除成功"))
                .inRoot(withDecorView(not(mActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        Assert.assertNotEquals(testText,listBooks.get(0).getTitle());
        Assert.assertNotEquals(resourceId,listBooks.get(0).getCoverResourceId());

        onView(allOf(withId(R.id.image_view_book_cover),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_view_books),
                                        0),
                                0),
                        isDisplayed()))
                .check(matches(not(hasDrawableResource(resourceId))));
    }

}
