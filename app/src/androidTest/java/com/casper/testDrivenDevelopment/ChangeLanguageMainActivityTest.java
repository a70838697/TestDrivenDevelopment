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
public class ChangeLanguageMainActivityTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.casper.testDrivenDevelopment", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<ChangeLanguageMainActivity> mActivityTestRule = new ActivityTestRule<>(ChangeLanguageMainActivity.class);


    private void testCountryLanguage(String countryName,int textViewTextResourceID,int buttonTextResourceID)
    {
        ViewInteraction editTextCountry= onView(
                allOf(withId(R.id.edit_text_country),
                        isDisplayed()));
        editTextCountry.check(matches(withText(countryName)));

        String textViewText=mActivityTestRule.getActivity().getString(textViewTextResourceID);
        ViewInteraction textViewLanguage = onView(
                allOf(withId(R.id.text_view_language),
                        isDisplayed()));
        textViewLanguage.check(matches(withText(textViewText)));

        String buttonText=mActivityTestRule.getActivity().getString(buttonTextResourceID);
        ViewInteraction buttonChangeLanguage = onView(
                allOf(withId(R.id.button_change_language),
                        isDisplayed()));
        buttonChangeLanguage.check(matches(withText(buttonText)));
    }
    private void changeLanguage(String countryName) {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edit_text_country),
                        isDisplayed()));
        appCompatEditText.perform(click());
        appCompatEditText.perform(replaceText(countryName));

        appCompatEditText.perform(closeSoftKeyboard());
        ViewInteraction buttonChangeLanguage = onView(
                allOf(withId(R.id.button_change_language),
                        isDisplayed()));
        buttonChangeLanguage.perform(click());
    }

    private void TestToChangeCountry(String countryName, int textViewTextResourceID, int buttonTextResourceID) {
        changeLanguage(countryName);
        testCountryLanguage(countryName, textViewTextResourceID, buttonTextResourceID);
    }
    @Test
    public void showChangeLanguageMainActivity() {
        testCountryLanguage("en", R.string.language_en, R.string.change_language_en);
    }
    @Test
    public void changeLanguageToCn() {
        TestToChangeCountry("cn", R.string.language_cn, R.string.change_language_cn);
    }
    @Test
    public void changeLanguageToEn() {
        TestToChangeCountry("en", R.string.language_en, R.string.change_language_en);
    }

    @Test
    public void changeLanguageToJp() {
        TestToChangeCountry("jp", R.string.language_jp, R.string.change_language_jp);
    }
    @Test
    public void changeLanguageToKr() {
        TestToChangeCountry("kr", R.string.language_kr, R.string.change_language_kr);
    }
}
