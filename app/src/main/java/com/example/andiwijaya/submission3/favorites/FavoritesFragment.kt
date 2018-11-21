package com.example.andiwijaya.submission3.favorites

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.favorites.favoritematches.FavoriteMatchesFragment

class FavoritesFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        viewPager = view.findViewById(R.id.viewPager)
        tabs = view.findViewById(R.id.tabLayout)

        val matchesFragmentAdapter = FavoritesViewPagerAdapter(childFragmentManager)
        matchesFragmentAdapter.addFragment(FavoriteMatchesFragment(), "Match")
        matchesFragmentAdapter.addFragment(FavoriteMatchesFragment(), "Team")
        viewPager.adapter = matchesFragmentAdapter
        tabs.setupWithViewPager(viewPager)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

    }
}