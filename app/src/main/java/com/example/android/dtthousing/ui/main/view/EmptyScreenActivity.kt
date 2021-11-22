package com.example.android.dtthousing.ui.main.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.android.dtthousing.R

@Suppress("DEPRECATION")
class EmptyScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_search)

        //fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //setup of the toolbar buttons and their behavior
        val toolBar = findViewById<ConstraintLayout>(R.id.toolBar)
        val homeButtonColor = toolBar.findViewById<ImageButton>(R.id.homeButton)
        homeButtonColor.setColorFilter(Color.BLACK)

        //To get to the "About" screen with the info button
        val infoButton = toolBar.findViewById<ImageButton>(R.id.infoButton)
        infoButton.setColorFilter(Color.LTGRAY)
        infoButton.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

        //to go back to MainActivity to list all houses again and search among them
        val button = findViewById<Button>(R.id.intentButton)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}