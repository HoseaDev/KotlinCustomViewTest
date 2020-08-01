package com.example.kotlintest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class TouchAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        touch1.setOnClickListener {
            Log.i("hltag","touch Click")
        }
        above.name="above"
        below.name="below"

        above. setOnClickListener{
            Log.i("hltag","above nner click")
        }
        btn1.setOnClickListener {
            Log.i("hltag","btn1 click")
        }


    }
}