package com.casper.testDrivenDevelopment;

import android.content.Context;

import androidx.test.espresso.ViewInteraction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HelloAndroidMainActivityTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.casper.testDrivenDevelopment", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<HelloAndroidMainActivity> mActivityTestRule = new ActivityTestRule<>(HelloAndroidMainActivity.class);

    @Test
    public void showHelloAndroidMainActivity() {
        String textHelloAndroid = mActivityTestRule.getActivity().getString(R.string.hello_android);
        ViewInteraction textViewLanguage = onView(
                allOf(withId(R.id.text_view_hello_world),
                        isDisplayed()));
        textViewLanguage.check(matches(withText(textHelloAndroid)));
    }
}
