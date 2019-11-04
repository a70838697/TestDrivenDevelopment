package com.casper.testDrivenDevelopment;

import android.view.View;
import android.widget.ImageView;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ImageViewMainActivityTest {
    private int[] imageIDArray={R.drawable.funny_1,R.drawable.funny_2
            ,R.drawable.funny_3,R.drawable.funny_4,R.drawable.funny_5
            ,R.drawable.funny_6
    };
    @Rule
    public ActivityTestRule<ImageViewMainActivity> mActivityTestRule = new ActivityTestRule<>(ImageViewMainActivity.class);
    @Test
    public void showImageViewMainActivity() {
        ViewInteraction buttonNext = onView(
                allOf(withId(R.id.button_next),
                        isDisplayed()));
        buttonNext.check(matches(
                withText(mActivityTestRule.getActivity().getString(R.string.next))
        ));

        ViewInteraction buttonPrevious = onView(
                allOf(withId(R.id.button_previous),
                        isDisplayed()));
        buttonPrevious.check(matches(
                withText(mActivityTestRule.getActivity().getString(R.string.previous))
        ));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.image_view_funny),
                        isDisplayed()));
        imageView.check(matches(hasDrawableResource(R.drawable.funny_1)));
    }
    @Test
    public void clickNextButton() {
        for(int iClicked=0;iClicked<=2*imageIDArray.length;iClicked++)
        {
            clickButtonAndTestImage(R.id.button_next,imageIDArray[(iClicked+1)%imageIDArray.length]);
        }
    }
    @Test
    public void clickPreviousButton() {
        for(int iClicked=2*imageIDArray.length;iClicked>=0;iClicked--)
        {
            clickButtonAndTestImage(R.id.button_previous,imageIDArray[(iClicked+imageIDArray.length-1)%imageIDArray.length]);
        }
    }
    private void clickButtonAndTestImage(int buttonId,int imageResourceId)
    {
        ViewInteraction buttonNext = onView(
                allOf(withId(buttonId),
                        isDisplayed()));
        buttonNext.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.image_view_funny),
                        isDisplayed()));
        imageView.check(matches(hasDrawableResource(imageResourceId)));
    }

    private static Matcher<View> hasDrawableResource( final int resourceId) {

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
