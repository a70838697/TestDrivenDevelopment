package com.casper.testDrivenDevelopment;

import android.content.Context;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GetSetTextMainActivityTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.casper.testDrivenDevelopment", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<GetSetTextMainActivity> mActivityTestRule = new ActivityTestRule<>(GetSetTextMainActivity.class);

    @Test
    public void showGetSetTextMainActivity() {
        String buttonText=mActivityTestRule.getActivity().getString(R.string.copy_text);
        ViewInteraction buttonCopyText = onView(
                allOf(withId(R.id.button_copy_text),withText(buttonText),
                        isDisplayed()));
        buttonCopyText.check(matches(withText(buttonText)));
        ViewInteraction editTextToShow = onView(
                allOf(withId(R.id.edit_text_to_show),
                        isDisplayed()));
        editTextToShow.check(matches(withText(buttonText)));
        ViewInteraction textViewShow = onView(
                allOf(withId(R.id.text_view_show),
                        isDisplayed()));
        textViewShow.check(matches(withText(buttonText)));
    }
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    @Test
    public void copyText() {
        for(int iLoop=1;iLoop<=2;iLoop++) {
            ViewInteraction editTextToShow = onView(
                    allOf(withId(R.id.edit_text_to_show),
                            isDisplayed()));
            editTextToShow.perform(click());

            String toCopied = getRandomString(22);
            editTextToShow.perform(replaceText(toCopied));
            editTextToShow.perform(closeSoftKeyboard());

            ViewInteraction buttonCopyText = onView(
                    allOf(withId(R.id.button_copy_text),
                            isDisplayed()));
            buttonCopyText.perform(click());


            ViewInteraction textViewShow = onView(
                    allOf(withId(R.id.text_view_show),
                            isDisplayed()));
            textViewShow.check(matches(withText(toCopied)));
        }
    }
}
