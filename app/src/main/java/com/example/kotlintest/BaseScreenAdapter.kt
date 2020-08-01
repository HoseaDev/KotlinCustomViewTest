package com.example.kotlintest

import android.view.View
import android.view.ViewGroup

abstract class BaseScreenAdapter {
    public abstract fun getCount(): Int

    abstract fun getTabLayout(position:Int,parent:ViewGroup):View



    abstract fun getMenuLayout(position:Int,parent:ViewGroup):View
}