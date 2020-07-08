package com.example.kotlintest

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter

class MyPagerAdapter(val names: ArrayList<String>,val fm:FragmentManager,val fragments:ArrayList<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
       return fragments[position]
    }



    override fun getCount(): Int {
       return fragments.size
    }
}