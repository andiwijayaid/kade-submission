package com.example.andiwijaya.submission3.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.R.id.*
import com.example.andiwijaya.submission3.fragment.favorites.FavoriteMatchesFragment
import com.example.andiwijaya.submission3.fragment.matches.MatchesFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                lastMatchIC -> {
                    loadMatchesFragment(savedInstanceState)
                }

                favoriteIC -> {
                    loadFavoriteMatchesFragment(savedInstanceState)
                }
            }
            true
        }

        bottomNavigation.selectedItemId = teamsIC
    }

    private fun loadMatchesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    MatchesFragment(), MatchesFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoriteMatchesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    FavoriteMatchesFragment(), FavoriteMatchesFragment::class.java.simpleName
                )
                .commit()
        }
    }
}
