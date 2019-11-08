package com.example.myapplication.Activities

import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.myapplication.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        }, 1000)
    }
}
