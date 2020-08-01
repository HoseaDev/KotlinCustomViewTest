package com.example.kotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_screen_list.*

class ScreenListAct : AppCompatActivity() {
    val items = mutableListOf<String>("11", "22", "33", "44", "55")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_list)
        screen_list.setAdapter(object : BaseScreenAdapter() {
            override fun getCount(): Int {
                return items.size
            }

            override fun getTabLayout(position: Int, parent: ViewGroup): View {
                val tv = LayoutInflater.from(this@ScreenListAct)
                    .inflate(R.layout.item_tab_head, null) as TextView
                tv.setText(items[position])
                return tv;
            }

            override fun getMenuLayout(position: Int, parent: ViewGroup): View {
                val root = LayoutInflater.from(this@ScreenListAct)
                    .inflate(R.layout.item_menu_body, null)
                val tv = root.findViewById<TextView>(R.id.tv)
                tv.setText(items[position])
                return root;
            }

        })
        btn2.setOnClickListener {
            Toast.makeText(this, "按钮3...............", Toast.LENGTH_SHORT).show()
        }
    }
}