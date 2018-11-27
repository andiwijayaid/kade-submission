package com.example.andiwijaya.submission3.home

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)


//    // Checking bottom navigation
//    @Test
//    fun testBottomNavigation() {
//        Thread.sleep(1000)
//        onView(withId(R.id.lastMatchIC)).perform(click())
//
//        Thread.sleep(1000)
//        onView(withId(R.id.nextMatchIC)).perform(click())
//
//        Thread.sleep(1000)
//        onView(withId(R.id.favoriteIC)).perform(click())
//
//        Thread.sleep(1000)
//    }
//
//    // Checking recycler view
//    @Test
//    fun testRecyclerViewBehaviour() {
//
//        Thread.sleep(1000)
//        onView(withId(nextMatchRV))
//            .check(matches(isDisplayed()))
//
//        Thread.sleep(1000)
//        onView(withId(nextMatchRV)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
//        onView(withId(nextMatchRV)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
//        Thread.sleep(1000)
//
//        pressBack() // finish() detail activity
//        Thread.sleep(1000)
//    }

//    @Test
//    fun testFavoritesFeature() {
//
//        // Last match bottom navigation
//        Thread.sleep(500)
//        onView(withId(lastMatchIC)).perform(click())
//
//        Thread.sleep(500)
//        onView(withId(lastMatchRV)).check(matches(isDisplayed()))
//
//        Thread.sleep(500)
//        onView(withId(lastMatchRV)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
//        onView(withId(lastMatchRV)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
//        Thread.sleep(500)
//
//        // Add to favorite
//        onView(withId(add_to_favorite)).perform(click())
//        Thread.sleep(500)
//
//        // checking snackbar
//        onView(withText("Ditambahkan ke favorite")).check(matches(isDisplayed()))
//        Thread.sleep(500)
//        pressBack() // finish() detail activity
//
//        // FavoriteMatch bottom navigation
//        Thread.sleep(500)
//        onView(withId(favoriteIC)).perform(click())
//
//        // Counting item in favorite match recyclerview
//        // make sure it has been added
//        onView(withId(favoriteMatchRV)).check(CustomAssertions.hasItemCount(1))
//
//        // back to match detail from a match in favorite
//        Thread.sleep(500)
//        onView(withId(favoriteMatchRV)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
//        Thread.sleep(500)
//
//        // Removing favorite
//        onView(withId(add_to_favorite)).perform(click())
//
//        // checking snackbar
//        onView(withText("Dihapus dari favorite")).check(matches(isDisplayed()))
//        Thread.sleep(500)
//        pressBack() // finish() detail activity
//
//        // Refresh item with swipe refresh layout
//        onView(withId(swipeRefreshLayout)).perform(swipeDown())
//
//        // Counting item in favorite match recyclerview
//        // make sure it has been removed
//        onView(withId(favoriteMatchRV))?.check(CustomAssertions.hasItemCount(0))
//
//        Thread.sleep(500)
//    }
}