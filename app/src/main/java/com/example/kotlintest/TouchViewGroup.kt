package com.example.kotlintest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.LinearLayout

class TouchViewGroup : FrameLayout {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.i("hltag", "ViewGroup:$this  dispatchTouchEvent ")
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.i("hltag", "ViewGroup:$this  onInterceptTouchEvent ")
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        Log.i("hltag", "ViewGroup:$this  onTouchEvent "+event?.action)
        return super.onTouchEvent(event)

    }
}