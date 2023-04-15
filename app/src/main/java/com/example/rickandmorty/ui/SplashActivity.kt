package com.example.rickandmorty.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.rickandmorty.R

class SplashActivity : AppCompatActivity() {

    private lateinit var message: TextView

    private val isFirstRun: Boolean
        get() {
            val sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("isFirstRun", true)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (isFirstRun) {
            message = findViewById(R.id.messageTv)
            message.text = "Welcome"
        } else {
            message = findViewById(R.id.messageTv)
            message.text = "Hello!"
        }

        val handler = Handler()
        handler.postDelayed({
            if (isFirstRun) {
                // Show "Welcome" when first installed on device
                val editor = getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                editor.putBoolean("isFirstRun", false)
                editor.apply()


                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Then "Hello!" show your text
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}
