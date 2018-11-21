package com.example.andiwijaya.submission3.fragment.matches

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.*
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.fragment.lastmatch.LastMatchFragment
import com.example.andiwijaya.submission3.fragment.nextmatch.NextMatchFragment

class MatchesFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_matches, container, false)
        viewPager = view.findViewById(R.id.viewPager)
        tabs = view.findViewById(R.id.tabLayout)

        val matchesFragmentAdapter = MatchesViewPagerAdapter(childFragmentManager)
        matchesFragmentAdapter.addFragment(LastMatchFragment(), "Last Match")
        matchesFragmentAdapter.addFragment(NextMatchFragment(), "Next Match")
        viewPager.adapter = matchesFragmentAdapter
        tabs.setupWithViewPager(viewPager)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        inflater?.inflate(R.menu.toolbar_menu, menu)

        val mSearch = menu?.findItem(R.id.action_search)

        val mSearchView = mSearch?.actionView as SearchView
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(match: String?): Boolean {
                return false
            }

        })
    }

}