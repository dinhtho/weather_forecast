package com.nab.weatherforecast

import android.os.Looper
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nab.weatherforecast.common.RecyclerViewItemCountAssertion
import com.nab.weatherforecast.forecast.MainActivity
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.logging.Handler

@RunWith(AndroidJUnit4::class)
class LoginActivityUITest {


    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun checkToastAtLeast3Characters() {
        onView(withId(R.id.etSearch)).perform(typeText("ab"))
        onView(withId(R.id.tvSearch)).perform(click())
        rule.scenario.onActivity {
            onView(withText(R.string.input_at_least_three_characters))
                .inRoot(withDecorView(not(`is`(it.window.decorView))))
                .check(matches(isDisplayed()))
        }
    }


    @Test
    fun checkSearchSuccess() {
        onView(withId(R.id.etSearch)).perform(typeText("Saigon"))
        onView(withId(R.id.tvSearch)).perform(click())
        onView(withId(R.id.tvError)).check(matches(not(isDisplayed())))
        onView(withId(R.id.rvInfos)).check(RecyclerViewItemCountAssertion(7))

    }

    @Test
    fun checkSearchFailed() {
        onView(withId(R.id.etSearch)).perform(typeText("-----"))
        onView(withId(R.id.tvSearch)).perform(click())
        onView(withId(R.id.rvInfos)).check(RecyclerViewItemCountAssertion(0))
        Thread.sleep(1000)
        onView(withId(R.id.tvError)).check(matches(isDisplayed()))
    }

}