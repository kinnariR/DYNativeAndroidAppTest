package com.example.app.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.app.ui.base.BaseActivity
import com.example.app.R
import com.example.app.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun layoutId() = R.layout.activity_splash

    lateinit var mHandler: Handler
    lateinit var mRunnable: Runnable
    override fun onViewReady() {
        supportActionBar?.hide()
        mHandler = Handler(mainLooper)
        mRunnable = Runnable {
            moveToNext()
        }
        mHandler.postDelayed(mRunnable, 2000)
    }


    private fun moveToNext() {
        val intent: Intent = Intent(this@SplashActivity, PostActivity::class.java)

        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }


    override fun onDestroy() {
        if (this::mHandler.isInitialized && this::mRunnable.isInitialized) {
            mHandler.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}