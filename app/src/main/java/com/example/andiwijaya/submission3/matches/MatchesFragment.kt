package com.example.andiwijaya.submission3.matches

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.*
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.home.HomeActivity
import com.example.andiwijaya.submission3.matches.lastmatch.LastMatchFragment
import com.example.andiwijaya.submission3.matches.nextmatch.NextMatchFragment
import com.example.andiwijaya.submission3.model.Match
import com.example.andiwijaya.submission3.model.Team
import com.example.andiwijaya.submission3.searchview.SearchMatchActivity
import kotlinx.android.synthetic.main.fragment_matches.view.*
import org.jetbrains.anko.support.v4.startActivity

class MatchesFragment : Fragment() {

    private lateinit var viewPager: ViewPager
     lateinit var tabs: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_matches, container, false)
        viewPager = view.findViewById(R.id.viewPager)
        tabs = view.findViewById(R.id.tabLayout)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(view.toolbar)

        val matchesFragmentAdapter = MatchesViewPagerAdapter(childFragmentManager)
        matchesFragmentAdapter.addFragment(LastMatchFragment(), "Last Match")
        matchesFragmentAdapter.addFragment(NextMatchFragment(), "Next Match")
        viewPager.adapter = matchesFragmentAdapter
        tabs.setupWithViewPager(viewPager)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.search_menu, menu)
        val item = menu?.findItem(R.id.search)
        val searchView = SearchView((context as HomeActivity).getSupportActionBar()?.getThemedContext())
        MenuItemCompat.setActionView(item, searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                startActivity<SearchMatchActivity>("SEARCH_QUERY" to p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

}