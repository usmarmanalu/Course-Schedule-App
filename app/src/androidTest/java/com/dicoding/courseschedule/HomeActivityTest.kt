package com.dicoding.courseschedule

import androidx.test.core.app.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.*
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.*
import androidx.test.ext.junit.runners.*
import com.dicoding.courseschedule.ui.add.*
import com.dicoding.courseschedule.ui.home.*
import com.dicoding.courseschedule.ui.list.*
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun destroy() {
        Intents.release()
    }

    @Test
    fun navigateToAddActivityFromHome() {
        ActivityScenario.launch(HomeActivity::class.java)
        onView(ViewMatchers.withId(R.id.action_add)).perform(click())
        intended(hasComponent(AddCourseActivity::class.java.name))
    }

    @Test
    fun navigateToListActivityFromHome() {
        ActivityScenario.launch(HomeActivity::class.java)
        onView(ViewMatchers.withId(R.id.action_list)).perform(click())
        intended(hasComponent(ListActivity::class.java.name))
    }

    @Test
    fun navigateToAddActivityFromList() {
        ActivityScenario.launch(ListActivity::class.java)
        onView(ViewMatchers.withId(R.id.fab)).perform(click())
        intended(hasComponent(AddCourseActivity::class.java.name))
    }
}
