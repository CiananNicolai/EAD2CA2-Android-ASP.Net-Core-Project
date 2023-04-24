package com.example.restaurant;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.intent.Intents.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;
// Expresso is a type of instrumentation test (requires running AVD or device)


// Wrong Espresso Tests made preemptively for API
@RunWith(AndroidJUnit4.class)
public class RestaurantExpressoTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testListViewDisplaysCorrectNumberOfRestaurants() {
        onView(withId(R.id.listView)).check(matches(hasChildCount(3)));
    }

    @Test
    public void testClickingOnRestaurantOpensCorrectDetailsPage() {
        onData(instanceOf(Restaurant.class))
                .inAdapterView(withId(R.id.listView))
                .atPosition(0)
                .perform(click());
        intended(hasComponent(RestaurantDetailsActivity.class.getName()));
        intended(hasExtra("name", "Restaurant A"));
        intended(hasExtra("address", "123 Main St"));
        intended(hasExtra("phone", "555-1234"));
    }

    @Test
    public void testRestaurantAdapterDisplaysCorrectRestaurantInformation() {
        onData(instanceOf(Restaurant.class))
                .inAdapterView(withId(R.id.listView))
                .atPosition(0)
                .check(matches(hasDescendant(withText("Restaurant A"))))
                .check(matches(hasDescendant(withText("123 Main St"))))
                .check(matches(hasDescendant(withText("555-1234"))));
    }

    @Test
    public void testRestaurantDetailsActivityDisplaysCorrectRestaurantInformation() {
        Intent intent = new Intent();
        intent.putExtra("name", "Restaurant A");
        intent.putExtra("address", "123 Main St");
        intent.putExtra("phone", "555-1234");
        activityRule.launchActivity(intent);
        onView(withId(R.id.nameTextView)).check(matches(withText("Restaurant A")));
        onView(withId(R.id.addressTextView)).check(matches(withText("123 Main St")));
        onView(withId(R.id.phoneTextView)).check(matches(withText("555-1234")));
    }

    @Test
    public void testListViewIsScrollable() {
        onView(withId(R.id.listView)).perform(swipeUp());
    }
}