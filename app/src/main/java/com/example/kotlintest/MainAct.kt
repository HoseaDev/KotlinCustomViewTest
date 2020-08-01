package com.example.kotlintest

import android.animation.ObjectAnimator
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_drag_view.*
import kotlinx.android.synthetic.main.activity_main.*

public class MainAct : AppCompatActivity() {
    private val handler: Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_view)


//        Snackbar.make(this,"12",1).

        simulationData()

//        touch1.setOnClickListener({
//            Log.i("hltag", "touch1 onClick")
//        })

//        touch2.setOnClickListener({
//            Log.i("hltag", "touch2 onClick")
//        })

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

    private fun simulationData() {
        val list = mutableListOf<Int>()
        for (index in 0..100) {
            list.add(index)
        }
        val adapter = object : BaseAdapter() {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                var container =
                    LayoutInflater.from(this@MainAct).inflate(R.layout.item_text, parent, false) as LinearLayout
                val tv = container.getChildAt(0) as TextView
                tv.setText("------>${list.get(position)}")
                return container
            }

            override fun getItem(position: Int): Any {
                return list.get(position)
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getCount(): Int {
                return list.size
            }

        }
        listView.adapter = adapter
    }
}