package com.journeydigitalpractical.app.ui.view

import android.content.Intent
import android.os.Handler
import androidx.core.content.ContextCompat
import com.journeydigitalpractical.app.ui.base.BaseActivity
import com.journeydigitalpractical.app.R
import com.journeydigitalpractical.app.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*

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
        splashLogo.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.blog))
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