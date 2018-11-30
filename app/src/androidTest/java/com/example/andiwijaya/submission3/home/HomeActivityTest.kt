package com.example.andiwijaya.submission3.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.andiwijaya.submission3.CustomAssertions
import com.example.andiwijaya.submission3.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testMatchFavoritesFeature() {

        // Last match bottom navigation
        onView(withId(matchesIC)).perform(click())

        onView(withId(lastMatchRV)).check(matches(isDisplayed()))

        Thread.sleep(500)
        onView(withId(lastMatchRV)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(lastMatchRV)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click())
        )

        Thread.sleep(1000)
        // Add to favorite
        onView(withId(add_to_favorite)).perform(click())

        // checking snackbar
        onView(withText("Added to favorite")).check(matches(isDisplayed()))
        pressBack() // finish() detail activity

        // FavoriteMatch bottom navigation
        onView(withId(favoriteIC)).perform(click())

        /** Counting item in favorite match recyclerview
         *  make sure it has been added
         */
        onView(withId(favoriteMatchRV)).check(CustomAssertions.hasItemCount(1))

        // back to match detail from a match in favorite
        onView(withId(favoriteMatchRV)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        // Removing favorite
        onView(withId(add_to_favorite)).perform(click())

        // checking snackbar
        onView(withText("Deleted from favorite")).check(matches(isDisplayed()))
        pressBack() // finish() detail activity

        // Refresh item with swipe refresh layout
        onView(withId(favoriteIC)).perform(click())

        /** Counting item in favorite match recyclerview
         *  make sure it has been removed
         */
        onView(withId(favoriteMatchRV))?.check(CustomAssertions.hasItemCount(0))

        Thread.sleep(500)
    }

    @Test
    fun testTeamFavoritesFeature() {

        // make sure teamsRV is active at the moment
        onView(withId(teamsRV)).check(matches(isDisplayed()))

        Thread.sleep(1000)
        onView(withId(teamsRV)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(teamsRV)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click())
        )

        Thread.sleep(1000)
        // Add to favorite
        onView(withId(add_to_favorite)).perform(click())

        // checking snackbar
        onView(withText("Added to favorite")).check(matches(isDisplayed()))
        pressBack() // finish() detail activity

        // FavoriteTeam bottom navigation
        onView(withId(favoriteIC)).perform(click())

        onView(withText("TEAM")).perform(click())

        /** Counting item in favorite team recyclerview
         *  make sure it has been added
         */
        onView(withId(favoriteTeamRV)).check(CustomAssertions.hasItemCount(1))

        // back to team detail from a match in favorite
        onView(withId(favoriteTeamRV)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        // Removing favorite
        onView(withId(add_to_favorite)).perform(click())

        // checking snackbar
        onView(withText("Deleted from favorite")).check(matches(isDisplayed()))
        pressBack() // finish() detail activity

        // Refresh item with swipe refresh layout
        onView(withId(favoriteIC)).perform(click())
        onView(withText("TEAM")).perform(click())

        /** Counting item in favorite team recyclerview
         *  make sure it has been removed
         */
        onView(withId(favoriteTeamRV))?.check(CustomAssertions.hasItemCount(0))

        Thread.sleep(500)
    }

    @Test
    fun testTeamDetail() {

        // make sure teamsRV is active at the moment
        onView(withId(teamsRV)).check(matches(isDisplayed()))

        Thread.sleep(1000)
        onView(withId(teamsRV)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(13))
        onView(withId(teamsRV)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(13, click())
        )

        Thread.sleep(1000)
        onView(withText("PLAYERS")).perform(click())

        onView(withId(playersRV)).check(matches(isDisplayed()))

        onView(withId(playersRV)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(withId(playersRV)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
        )

        Thread.sleep(500)
        pressBack()
        pressBack()

        Thread.sleep(500)
    }

    @Test
    fun searchTeam() {
        onView(withId(search)).perform(click())
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("Man"), closeSoftKeyboard())
        Thread.sleep(2000)

        onView(withId(teamsRV)).check(matches(isDisplayed()))
        onView(withId(teamsRV)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        onView(withId(teamsRV)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click())
        )
        Thread.sleep(500)
        onView(withText("PLAYERS")).perform(click())
        Thread.sleep(500)
        pressBack()
        Thread.sleep(500)
    }

    @Test
    fun spinnerTest() {
        onView(withId(teamsRV)).check(matches(isDisplayed()))
        onView(withText("English Premier League")).perform(click())
        onView(withText("Spanish La Liga")).perform(click())
        Thread.sleep(3000)
        onView(withText("Spanish La Liga")).perform(click())
        onView(withText("Italian Serie A")).perform(click())
        Thread.sleep(3000)
    }

//    @Test
//    fun calendarIntentTest() {
//        onView(withId(matchesIC)).perform(click())
//        onView(withText("NEXT MATCH")).perform(click())
//
//        onView(withId(nextMatchRV)).check(matches(isDisplayed()))
//
//        Thread.sleep(3000)
//        onView(withId(nextMatchRV)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(7))
//        onView(withId(nextMatchRV)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(7, MyViewAction.clickChildViewWithId(
//            bellIB)), closeSoftKeyboard())
//    }
}