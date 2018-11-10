package com.example.andiwijaya.submission3.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.R.id.*
import com.example.andiwijaya.submission3.fragment.favorites.FavoriteMatchesFragment
import com.example.andiwijaya.submission3.fragment.lastmatch.LastMatchFragment
import com.example.andiwijaya.submission3.fragment.nextmatch.NextMatchFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                lastMatchIC -> {
                    loadLastMatchesFragment(savedInstanceState)
                }

                nextMatchIC -> {
                    loadNextMatchesFragment(savedInstanceState)
                }

                favoriteIC -> {
                    loadFavoriteMatchesFragment(savedInstanceState)
                }
            }
            true
        }

        bottomNavigation.selectedItemId = nextMatchIC
    }

    private fun loadLastMatchesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    LastMatchFragment(), LastMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadNextMatchesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    NextMatchFragment(), NextMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoriteMatchesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    FavoriteMatchesFragment(), FavoriteMatchesFragment::class.java.simpleName)
                .commit()
        }
    }
}
