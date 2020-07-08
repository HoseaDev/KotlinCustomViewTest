package com.example.kotlintest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class Touch2View : View


{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.i("hltag", "View2:$this  dispatchTouchEvent ")
        return super.dispatchTouchEvent(ev)
    }



    override fun onTouchEvent(event: MotionEvent?): Boolean {

        Log.i("hltag", "View2:$this  onTouchEvent "+event?.action)
        return true

    }
}