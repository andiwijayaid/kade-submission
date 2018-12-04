package com.example.andiwijaya.submission3.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.home.HomeActivity
import org.jetbrains.anko.startActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler()
        handler.postDelayed({
            kotlin.run {
                startActivity<HomeActivity>()
                finish()
            }
        }, 2000L)
    }

}
