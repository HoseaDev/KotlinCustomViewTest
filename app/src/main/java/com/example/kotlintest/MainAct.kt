package com.example.kotlintest

import android.animation.ObjectAnimator
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

public class MainAct : AppCompatActivity() {
    private val handler: Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sliding_menu)
//        touch1.setOnClickListener({
//            Log.i("hltag", "touch1 onClick")
//        })

//        touch2.setOnClickListener({
//            Log.i("hltag", "touch2 onClick")
//        })
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
        Log.i("hltag", "..................")
//        letterView.onTouchLetterListener=object :LetterView.OnTouchLetterListener{
//            override fun touch(letter: String, show: Boolean) {
//                if (show){
//                    centerLetter.text=letter
//                    centerLetter.visibility=View.VISIBLE
//                }else{
//                    centerLetter.visibility= View.GONE
//                }
//            }
//        }
//        step_view.setStepMax(4000)
//
//        //属性动画
//        val animator =  ObjectAnimator.ofInt(0,4000)
//
//        animator.setDuration(5000)
//        animator.interpolator=DecelerateInterpolator()
//        animator.addUpdateListener {
//         step_view.setCurrentStep(it.getAnimatedValue()as Int)
//        }
//        animator.start()

//        leftToRight.setOnClickListener {
//            trackText.setDirection(TrackTextView.Direction.LEFT_TO_RIGHT)
//            val animator = ObjectAnimator.ofFloat(0f, 1f)
//
//            animator.setDuration(2000)
////            animator.interpolator = DecelerateInterpolator()
//            animator.addUpdateListener {
////                step_view.setCurrentStep(it.getAnimatedValue() as Int)
//                Log.i("MainAct ",  "leftToRight  ${it.animatedFraction}")
//                trackText.setCurrentProgress(1-it.animatedFraction)
//
//            }
//            animator.start()
//            handler.postDelayed({
//                rightToLeft.performClick()
//            },2000)
//        }
//
//        rightToLeft.setOnClickListener {
//            trackText.setDirection(TrackTextView.Direction.RIGHT_TO_LEFT)
//            val animator = ObjectAnimator.ofFloat(0f, 1f)
//
//            animator.setDuration(2000)
////            animator.interpolator = DecelerateInterpolator()
//            animator.addUpdateListener {
////                step_view.setCurrentStep(it.getAnimatedValue() as Int)
//                Log.i("MainAct", "rightToLeft  ${it.animatedFraction}")
//                trackText.setCurrentProgress(1-it.animatedFraction)
//            }
//            animator.start()
//        }

    }
}