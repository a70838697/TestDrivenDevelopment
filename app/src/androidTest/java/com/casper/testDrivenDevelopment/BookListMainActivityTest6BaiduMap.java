package com.casper.testDrivenDevelopment;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.baidu.mapapi.map.MapView;
import com.google.android.material.tabs.TabLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BookListMainActivityTest6BaiduMap {

    @Rule
    public ActivityTestRule<BookListMainActivity> mActivityTestRule = new ActivityTestRule<>(BookListMainActivity.class);

    @Test
    public void BookListMainActivityTest6BaiduMap() {
        onView(
                allOf(withText("卖家"),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                allOf(
                                                        Matchers.<View>instanceOf(TabLayout.class),
                                                        withId(R.id.tablayout)
                                                ),
                                                0),
                                        2),
                                1),
                        isDisplayed())
        ).check(matches(withText("卖家")));

        onView(
                allOf(withContentDescription("卖家"),
                        childAtPosition(
                                childAtPosition(
                                        allOf(
                                                Matchers.<View>instanceOf(TabLayout.class),
                                                withId(R.id.tablayout)
                                        ),
                                        0),
                                2),
                        isDisplayed())
        ).perform(click());


        ViewInteraction viewGroup = onView(
                allOf(withId(R.id.bmap_view),
                        Matchers.<View>instanceOf(com.baidu.mapapi.map.MapView.class),
                        childAtPosition(
                                withParent(withId(R.id.view_pager)),
                                0),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withText(" 100米 "),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bmap_view),
                                        3),
                                1),
                        isDisplayed()));
        textView.check(matches(withText(" 100米 ")));
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
