package com.example.andiwijaya.submission3.home

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View
import org.hamcrest.Matcher


object MyViewAction {

    fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click a view in recyclerview item"
            }

            override fun perform(uiController: UiController, view: View) {
                val mView = view.findViewById<View>(id)
                mView.performClick()
            }
        }
    }


}