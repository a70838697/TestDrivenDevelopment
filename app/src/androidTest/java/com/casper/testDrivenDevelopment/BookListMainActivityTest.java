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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BookListMainActivityTest {

    @Rule
    public ActivityTestRule<BookListMainActivity> mActivityTestRule = new ActivityTestRule<>(BookListMainActivity.class);

    @Test
    public void showBookListMainActivityTest() {
        List<Book> listBooks = mActivityTestRule.getActivity().getListBooks();
        for (int iLoop = 0; iLoop < listBooks.size(); iLoop++) {
            ViewInteraction imageView = onView(
                    allOf(withId(R.id.image_view_book_cover),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.list_view_books),
                                            iLoop),
                                    0),
                            isDisplayed()));
            imageView.check(matches(hasDrawableResource(listBooks.get(iLoop).getCoverResourceId())));

            //if(iLoop==2)continue;
            ViewInteraction textView = onView(
                    allOf(withId(R.id.text_view_book_title),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.list_view_books),
                                            iLoop),
                                    1),
                            isDisplayed()));
            textView.check(matches(withText(listBooks.get(iLoop).getTitle())));
        }

        DataInteraction linearLayout3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.list_view_books),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(1);
        linearLayout3.perform(click());
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
}
