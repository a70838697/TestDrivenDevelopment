package com.casper.testDrivenDevelopment;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddToMainActivityTest {

    @Rule
    public ActivityTestRule<AddToMainActivity> mActivityRule = new ActivityTestRule<>(
            AddToMainActivity.class);
    public ActivityTestRule<AddToListActivity> mlistActivityRule = new ActivityTestRule<>(
            AddToListActivity.class);
    @Test
    public void onClick() throws Exception {
        onView(withId(R.id.hellotext))
                .check(matches(withText("Hello world!")));
        onView(withId(R.id.buttonChange)).perform(click());
        onView(withId(R.id.hellotext))
                .check(matches(withText("荷塘月色")));
    }
    @Test
    public void CheckListViewWindowShow() throws Exception {
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.textivewhello))
                .check(matches(withText("荷塘月色2")));
    }
    @Test
    public void CheckListViewAdd() throws Exception {
        Random random = new Random();
        int pos = random.nextInt(10000);

        String typedText = "5xxxx"+pos;
        onView(withId(R.id.editText)).perform(typeText(typedText), closeSoftKeyboard());
        onView(withId(R.id.button2)).perform(click());
        onData(instanceOf(String.class))
                .atPosition(2)
                .check(matches(withText(typedText)))
                .check(matches(isDisplayed()));
    }
    @Test
    public void CheckListView() throws Exception {
        Random random = new Random();
        int pos = random.nextInt(10000);

        String typedText = "5xxxx"+pos;
        onView(withId(R.id.editText)).perform(typeText(typedText), closeSoftKeyboard());
        onView(withId(R.id.button2)).perform(click());
        onData(instanceOf(String.class))
                .atPosition(2)
                .check(matches(withText(typedText)))
                .perform(click());


        Thread.sleep(1000);
        onView(withText("点击了"+typedText))
                .inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}
