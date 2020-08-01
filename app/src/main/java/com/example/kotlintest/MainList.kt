package com.example.kotlintest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_list.*

class MainList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)
        lock_pattern.setOnClickListener {
            startActivity(Intent(this, MainAct::class.java))
        }
        screen_list.setOnClickListener {
            startActivity(Intent(this, ScreenListAct::class.java))
        }
        touch_act.setOnClickListener {
            startActivity(Intent(this, TouchAct::class.java))
        }
        messageBubbleBtn.setOnClickListener {
            startActivity(Intent(this, MessageBubbleViewAct::class.java))
        }
    }
}