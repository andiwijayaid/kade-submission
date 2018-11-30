package com.example.andiwijaya.submission3.favorites

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

open class FavoritesViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTitleList = mutableListOf<String>()

    override fun getItem(p0: Int): Fragment {
        return fragmentList.get(p0)
    }

    override fun getCount(): Int {
        return fragmentTitleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList.get(position)
    }

    fun addFragment(fragment: Fragment, title: String) {

        fragmentList.add(fragment)
        fragmentTitleList.add(title)

    }
}