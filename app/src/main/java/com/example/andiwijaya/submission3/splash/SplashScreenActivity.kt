package com.example.andiwijaya.submission3.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.andiwijaya.submission3.R
import com.example.andiwijaya.submission3.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler()
        handler.postDelayed({
            kotlin.run {
                startActivity(Intent(applicationContext, HomeActivity::class.java))
                finish()
            }
        }, 1000L)
    }
}
