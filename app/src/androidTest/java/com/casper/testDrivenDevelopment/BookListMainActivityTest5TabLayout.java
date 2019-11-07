package com.casper.testDrivenDevelopment;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.web.assertion.WebAssertion;
import androidx.test.espresso.web.webdriver.Locator;
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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webContent;
import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BookListMainActivityTest5TabLayout {

    @Rule
    public ActivityTestRule<BookListMainActivity> mActivityTestRule = new ActivityTestRule<>(BookListMainActivity.class);

    @Test
    public void BookListMainActivityTest5TabLayout() {
        onView(allOf(withId(R.id.tablayout),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed())
        ).check(matches(isDisplayed()));

        onData(anything())
                .inAdapterView(allOf(withId(R.id.list_view_books),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0))
                ).atPosition(0)
                .check(matches(isDisplayed()));;

        ViewInteraction listView=onView(allOf(withId(R.id.list_view_books),
                        childAtPosition(
                                withParent(withId(R.id.view_pager)),
                                0),
                        isDisplayed())
        ).check(matches(isDisplayed()));

        ViewInteraction actionBar$Tab = onView(
                allOf(withContentDescription("图书"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tablayout),
                                        0),
                                0),
                        isDisplayed())
        ).check(matches(isDisplayed()));

        /*TabLayout-->SlidingTabIndicator-->TabView-->AppCompatImageView,AppCompatTextView*/
        ViewInteraction textView = onView(
                allOf(withText("图书"),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withId(R.id.tablayout),
                                                0),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("图书")));

        ViewInteraction textView2 = onView(
                allOf(withText("新闻"),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withId(R.id.tablayout),
                                                0),
                                        1),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("新闻")));

        ViewInteraction tabView = onView(
                allOf(withContentDescription("新闻"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tablayout),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction listView2=onView(allOf(withId(R.id.list_view_books),
                childAtPosition(
                        withParent(withId(R.id.view_pager)),
                        0),
                isDisplayed())
        ).check(doesNotExist());

        ViewInteraction webView = onView(
                allOf(childAtPosition(
                        withParent(withId(R.id.view_pager)),
                        0),
                        isDisplayed()));
        webView.check(matches(isDisplayed()));

        onWebView().withElement(findElement(Locator.CLASS_NAME,"topnav_s")).check(webMatches(getText(), containsString("国内")));
        onWebView().withElement(findElement(Locator.CLASS_NAME,"hd_tit_a")).check(webMatches(getText(), containsString("新闻")));
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
