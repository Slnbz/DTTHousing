package com.example.android.dtthousing

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout

//"about" page and its features
@Suppress ("DEPRECATION")
class InfoActivity : AppCompatActivity(){

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_info)

            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

            val toolBar = findViewById<ConstraintLayout>(R.id.toolBar)
            val infoButton = toolBar.findViewById<ImageButton>(R.id.infoButton)
            infoButton.setColorFilter(Color.BLACK)

            val homeButton = toolBar.findViewById<ImageButton>(R.id.homeButton)
            homeButton.setColorFilter(Color.LTGRAY)
            homeButton.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
    }
}