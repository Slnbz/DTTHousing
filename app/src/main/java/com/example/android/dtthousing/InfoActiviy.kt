package com.example.android.dtthousing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

//"about" page and its features

class InfoActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.info_activity)

            val button = findViewById<ImageButton>(R.id.homebutton)
            button.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }


    }


}