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
import java.util.Random;

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
public class BookListMainActivityTest3 {

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
    public void testContextMenuNewEditCancel() {
        List<Book> listBooks = mActivityTestRule.getActivity().getListBooks();
        int bookCount = listBooks.size();

        String testText = "无名书籍" + Math.random();
        int resourceId = R.drawable.book_no_name;
        int insertPosition = 2;

        /*检查要测试的对象不存在*/
        ViewInteraction theNotExistTextView = onView(
                allOf(withId(R.id.text_view_book_title), withText(testText)));
        theNotExistTextView.check(
                doesNotExist());

        Assert.assertNotEquals(testText, listBooks.get(insertPosition).getTitle());
        Assert.assertNotEquals(resourceId, listBooks.get(insertPosition).getCoverResourceId());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.image_view_book_cover),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_view_books),
                                        insertPosition),
                                0),
                        isDisplayed()));
        imageView.check(matches(not(hasDrawableResource(resourceId))));

        /*新编菜单不存在*/
        onView(allOf(withId(android.R.id.title), withText("新编"), isDisplayed()))
                .check(doesNotExist());

        /*第insertPosition个对象长按*/
        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        withId(R.id.list_view_books),
                        insertPosition),
                        isDisplayed()))
                .perform(longClick());

        /*出现新编菜单*/
        onView(allOf(withId(android.R.id.title), withText(listBooks.get(insertPosition).getTitle())))
                .check(matches(isDisplayed()));
        onView(allOf(withId(android.R.id.title), withText("新编"), isDisplayed()))
                .perform(click());

        /*在新窗口的编辑框*/
        onView(allOf(withId(R.id.edit_text_book_name), isDisplayed()))
                .perform(click());

        /*修改编辑框内容*/
        onView(allOf(withId(R.id.edit_text_book_name), isDisplayed()))
                .perform(replaceText(testText), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_cancel), withText("Cancel"), isDisplayed()))
                .perform(click());

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
                .check((doesNotExist()));
        onView(allOf(withId(R.id.image_view_book_cover),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.list_view_books),
                                insertPosition),
                        0),
                isDisplayed()))
                .check(matches((hasDrawableResource(listBooks.get(insertPosition).getCoverResourceId()))));

        Assert.assertNotEquals(testText, listBooks.get(insertPosition).getTitle());
        Assert.assertNotEquals(resourceId, listBooks.get(insertPosition).getCoverResourceId());
        Assert.assertEquals(bookCount, listBooks.size());

    }
    @Test
    public void testContextMenuNewEdit() {
        List<Book> listBooks = mActivityTestRule.getActivity().getListBooks();
        int bookCount = listBooks.size();

        String testText = "无名书籍" + Math.random();
        int resourceId = R.drawable.book_no_name;
        int insertPosition = 2;

        /*检查要测试的对象不存在*/
        ViewInteraction theNotExistTextView = onView(
                allOf(withId(R.id.text_view_book_title), withText(testText)));
        theNotExistTextView.check(
                doesNotExist());

        Assert.assertNotEquals(testText, listBooks.get(insertPosition).getTitle());
        Assert.assertNotEquals(resourceId, listBooks.get(insertPosition).getCoverResourceId());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.image_view_book_cover),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_view_books),
                                        insertPosition),
                                0),
                        isDisplayed()));
        imageView.check(matches(not(hasDrawableResource(resourceId))));

        /*新编菜单不存在*/
        onView(allOf(withId(android.R.id.title), withText("新编"), isDisplayed()))
                .check(doesNotExist());

        /*第insertPosition个对象长按*/
        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        withId(R.id.list_view_books),
                        insertPosition),
                        isDisplayed()))
                .perform(longClick());

        /*出现新编菜单*/
        onView(allOf(withId(android.R.id.title), withText(listBooks.get(insertPosition).getTitle())))
                .check(matches(isDisplayed()));
        onView(allOf(withId(android.R.id.title), withText("新编"), isDisplayed()))
                .perform(click());

        /*在新窗口的编辑框*/
        onView(allOf(withId(R.id.edit_text_book_name), isDisplayed()))
                .perform(click());

        /*修改编辑框内容*/
        onView(allOf(withId(R.id.edit_text_book_name), isDisplayed()))
                .perform(replaceText(testText), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_ok), withText("Ok"), isDisplayed()))
                .perform(click());

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
        onView(allOf(withId(R.id.image_view_book_cover),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.list_view_books),
                                insertPosition),
                        0),
                isDisplayed()))
                .check(matches((hasDrawableResource(listBooks.get(insertPosition).getCoverResourceId()))));

        Assert.assertEquals(testText, listBooks.get(insertPosition).getTitle());
        Assert.assertEquals(resourceId, listBooks.get(insertPosition).getCoverResourceId());
        Assert.assertEquals(bookCount + 1, listBooks.size());

    }

    @Test
    public void testContextMenuEdit() {
        List<Book> listBooks = mActivityTestRule.getActivity().getListBooks();
        int bookCount = listBooks.size();

        String testText = "有名书籍" + Math.random();
        int resourceId = R.drawable.book_no_name;
        int updatePosition = 0;

        /*检查要测试的对象不存在*/
        onView(
                allOf(withId(R.id.text_view_book_title), withText(testText))
        ).check(doesNotExist());

        Assert.assertNotEquals(testText, listBooks.get(updatePosition).getTitle());
        Assert.assertNotEquals(resourceId, listBooks.get(updatePosition).getCoverResourceId());

        onView(allOf(withId(R.id.image_view_book_cover),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.list_view_books),
                                updatePosition),
                        0),
                isDisplayed())
        ).check(matches(not(hasDrawableResource(resourceId))));

        /*编辑菜单不存在*/
        onView(
                allOf(withId(android.R.id.title), withText("编辑"), isDisplayed())
        ).check(doesNotExist());

        /*第updatePosition个对象长按*/
        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        withId(R.id.list_view_books),
                        updatePosition),
                        isDisplayed()))
                .perform(longClick());

        /*出现新编菜单*/
        onView(allOf(withId(android.R.id.title), withText(listBooks.get(updatePosition).getTitle())))
                .check(matches(isDisplayed()));
        onView(allOf(withId(android.R.id.title), withText("编辑"), isDisplayed()))
                .perform(click());

        /*在新窗口的编辑框*/
        onView(
                allOf(withId(R.id.edit_text_book_name), isDisplayed())
        ).perform(click())
                .check(matches(withText(listBooks.get(updatePosition).getTitle())));

        /*修改编辑框内容*/
        onView(allOf(withId(R.id.edit_text_book_name), isDisplayed()))
                .perform(replaceText(testText), closeSoftKeyboard());

        onView(allOf(withId(R.id.button_ok), withText("Ok"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.text_view_book_title),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.list_view_books),
                                updatePosition),
                        1),
                withText(testText)))
                .check(matches(isDisplayed()));
        onView(allOf(withId(R.id.image_view_book_cover),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.list_view_books),
                                updatePosition),
                        0),
                isDisplayed()))
                .check(matches((hasDrawableResource(listBooks.get(updatePosition).getCoverResourceId()))));

        Assert.assertEquals(testText, listBooks.get(updatePosition).getTitle());
        Assert.assertEquals(bookCount, listBooks.size());
    }

}
