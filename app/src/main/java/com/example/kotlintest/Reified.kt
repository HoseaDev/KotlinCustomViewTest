package com.example.kotlintest

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

//inline fun <reified T> startActivity(context: Context, block: Intent.() -> Unit) {
//    val intent = Intent(context, T::class.java)
//    intent.block()
//    context.startActivity(intent)
//}
//
//inline fun <reified T> startActivityForResult(context: FragmentActivity, result: Int, block: Intent.() -> Unit) {
//    val intent = Intent(context, T::class.java)
//    intent.block()
//    context.startActivityForResult(intent, result)
//}

inline fun <reified T : FragmentActivity> startActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T : FragmentActivity> startActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    block(intent)
    context.startActivity(intent)
}

inline fun <reified T : FragmentActivity> startActivityForResult(context: FragmentActivity, requestCode: Int, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    block(intent)
    context.startActivityForResult(intent, requestCode)
}

inline fun <reified T : FragmentActivity> startActivityForResult(fragment: Fragment, requestCode: Int, block: Intent.() -> Unit) {
    val intent = Intent(fragment.context, T::class.java)
    block(intent)
    fragment.startActivityForResult(intent, requestCode)
}
