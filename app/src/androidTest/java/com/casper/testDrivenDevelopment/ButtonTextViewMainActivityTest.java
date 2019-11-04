package com.casper.testDrivenDevelopment;


import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

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
public class ButtonTextViewMainActivityTest {

    @Rule
    public ActivityTestRule<ButtonTextViewMainActivity> mActivityTestRule = new ActivityTestRule<>(ButtonTextViewMainActivity.class);


    @Test
    public void showButtonTextViewMainActivity() {
        String helloWorldEnString=mActivityTestRule.getActivity().getString(R.string.hello_world_en);
        String helloWorldCzString=mActivityTestRule.getActivity().getString(R.string.hello_world_cn);
        checkText(helloWorldEnString,helloWorldEnString, helloWorldCzString);
    }
    @Test
    public void clickEnButton() {
        String helloWorldEnString=mActivityTestRule.getActivity().getString(R.string.hello_world_en);
        String helloWorldCzString=mActivityTestRule.getActivity().getString(R.string.hello_world_cn);
        ViewInteraction button = onView(
                allOf(withId(R.id.button_hello_en),
                        isDisplayed()));
        button.perform(click());
        checkText(helloWorldEnString,helloWorldEnString, helloWorldCzString);
    }
    @Test
    public void clickCzButton() {
        String helloWorldEnString=mActivityTestRule.getActivity().getString(R.string.hello_world_en);
        String helloWorldCzString=mActivityTestRule.getActivity().getString(R.string.hello_world_cn);
        ViewInteraction button = onView(
                allOf(withId(R.id.button_hello_cn),
                        isDisplayed()));
        button.perform(click());
        checkText(helloWorldCzString,helloWorldEnString, helloWorldCzString);
    }
    private void checkText(String textViewText,String buttonEnText,String buttonCnText)
    {
        ViewInteraction textView = onView(
                allOf(withId(R.id.text_wiew_hello_world),
                        isDisplayed()));
        textView.check(matches(withText(textViewText)));
        ViewInteraction buttonEn = onView(
                allOf(withId(R.id.button_hello_en),
                        isDisplayed()));
        buttonEn.check(matches(withText(buttonEnText)));
        ViewInteraction buttonCz = onView(
                allOf(withId(R.id.button_hello_cn),
                        isDisplayed()));
        buttonCz.check(matches(withText(buttonCnText)));

    }
}
