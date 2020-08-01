package com.example.kotlintest

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lockpattern.*

class LockPatternAct : AppCompatActivity() {
    private val lockPwd = "14863"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lockpattern)
        Toast.makeText(this, "123123", Toast.LENGTH_LONG).show()
        lockPattern.unLockListener = object : LockPatternView.UnLockListener {
            override fun unLock(pwd: String) {
                if (pwd == lockPwd) {
                    Toast.makeText(this@LockPatternAct, "密码正确=${pwd}", Toast.LENGTH_LONG).show()
                    lockPattern.setStatus(LockPatternView.Pointer.STATUS_NORMAL)
                }else{
                    Toast.makeText(this@LockPatternAct, "密码错误=${pwd}", Toast.LENGTH_LONG).show()
                    lockPattern.setStatus(LockPatternView.Pointer.STATUS_ERROR)
                }
            }

        }
    }
}