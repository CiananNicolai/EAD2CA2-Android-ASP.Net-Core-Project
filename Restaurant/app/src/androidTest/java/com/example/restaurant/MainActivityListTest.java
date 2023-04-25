package com.example.restaurant;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityListTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityListTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.nameTextView), withText("The Spicy Noodle"),
                        withParent(withParent(withId(R.id.listView))),
                        isDisplayed()));
        textView.check(matches(withText("The Spicy Noodle")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.nameTextView), withText("Pizza Palace"),
                        withParent(withParent(withId(R.id.listView))),
                        isDisplayed()));
        textView2.check(matches(withText("Pizza Palace")));
    }
}
