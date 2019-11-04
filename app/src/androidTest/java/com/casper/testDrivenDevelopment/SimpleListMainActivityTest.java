package com.casper.testDrivenDevelopment;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
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

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class SimpleListMainActivityTest {
    private String[] booksData = {"软件项目管理案例教程（第4版）", "创新工程实践", "C语言从入门到精通"};
    @Rule
    public ActivityTestRule<SimpleListMainActivity> mActivityTestRule = new ActivityTestRule<>(SimpleListMainActivity.class);
    @Test
    public void showSimpleListMainActivity() {
        for(int iLoop=0;iLoop<booksData.length;iLoop++) {
            onView(withText(booksData[iLoop])).check(matches(isCompletelyDisplayed()));
            onData(is(booksData[iLoop])).check(matches(isCompletelyDisplayed()));
            onData(is(booksData[iLoop])).check(matches(withId(android.R.id.text1)));

            ViewInteraction textView = onView(
                    allOf(withId(android.R.id.text1), withText(booksData[iLoop]),
                            childAtPosition(
                                    allOf(withId(R.id.list_view_simple)),
                                    iLoop),
                            isDisplayed()));
            textView.check(matches(withText(booksData[iLoop])));
            DataInteraction textViewData = onData(anything())
                    .inAdapterView(allOf(withId(R.id.list_view_simple),
                            childAtPosition(
                                    withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                    0)))
                    .atPosition(iLoop);
            textViewData.check(matches(withText(booksData[iLoop])));
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
