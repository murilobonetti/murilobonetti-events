package com.murilobonetti.events.detail

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.murilobonetti.events.R
import com.murilobonetti.events.data.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {


    @Before
    fun setup() {
        val selectedEvent = Event("1", "teste1", 99.0, 0.0, 0.0, "imagem1", "desc1", 1660856038000)

        val bundle = Bundle().apply { putParcelable("selectedEvent", selectedEvent) }
        launchFragmentInContainer<DetailFragment>(bundle, R.style.AppTheme)
    }

    @Test
    fun selectedEventDetails_DisplayedInUi() {
        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(withText("teste1")))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(withText("desc1")))
        onView(withId(R.id.date)).check(matches(isDisplayed()))
        onView(withId(R.id.date)).check(matches(withText("Data: 18/08/2022 - 20:53")))
        onView(withId(R.id.directions_button)).check(matches(isDisplayed()))
        onView(withId(R.id.price)).check(matches(isDisplayed()))
        onView(withId(R.id.price)).check(matches(withText("Valor: R$ 99.00")))
        onView(withId(R.id.checkin_button)).check(matches(isDisplayed()))
    }

    @Test
    fun clickCheckinButton_DisplayModalCheckin() {
        onView(withId(R.id.checkin_button)).check(matches(isDisplayed()))
        onView(withId(R.id.checkin_button)).perform(click())

        // ModalBottomSheet fields
        onView(withId(R.id.name)).check(matches(isDisplayed()))
        onView(withId(R.id.email)).check(matches(isDisplayed()))
        onView(withId(R.id.button_checkin)).check(matches(isDisplayed()))
    }

}