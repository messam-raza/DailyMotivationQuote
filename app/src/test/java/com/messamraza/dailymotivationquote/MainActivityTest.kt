package com.messamraza.dailymotivationquote

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun activityLaunchesSuccessfully() {
        // When
        ActivityScenario.launch(MainActivity::class.java)

        // Then
        onView(withId(R.id.quoteText)).check(matches(isDisplayed()))
        onView(withId(R.id.authorText)).check(matches(isDisplayed()))
        onView(withId(R.id.newQuoteButton)).check(matches(isDisplayed()))
        onView(withId(R.id.shareButton)).check(matches(isDisplayed()))
    }

    @Test
    fun newQuoteButtonIsClickable() {
        // Given
        ActivityScenario.launch(MainActivity::class.java)

        // When & Then
        onView(withId(R.id.newQuoteButton))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
            .perform(click())
    }

    @Test
    fun shareButtonIsClickable() {
        // Given
        ActivityScenario.launch(MainActivity::class.java)

        // When & Then
        onView(withId(R.id.shareButton))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
    }

    @Test
    fun buttonsHaveCorrectText() {
        // Given
        ActivityScenario.launch(MainActivity::class.java)

        // Then
        onView(withId(R.id.newQuoteButton))
            .check(matches(withText("New Quote")))

        onView(withId(R.id.shareButton))
            .check(matches(withText("Share")))
    }

    @Test
    fun quoteTextViewsAreVisible() {
        // Given
        ActivityScenario.launch(MainActivity::class.java)

        // Then
        onView(withId(R.id.quoteText))
            .check(matches(isDisplayed()))
            .check(matches(withText(containsString("Loading"))))

        onView(withId(R.id.authorText))
            .check(matches(isDisplayed()))
    }
}
