package com.kay.demo.mvvm.view.act

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gyf.immersionbar.ImmersionBar
import com.kay.demo.R
import com.kay.demo.databinding.ActSplashBinding
import java.lang.ref.WeakReference


/**
 * @Author: Administrator
 * @CreateDate: 2021/5/19 16:13
 * @Description:
 */
class SplashAct : AppCompatActivity() {

    private lateinit var welcomeHandler: WelcomeHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.styleMvvm)
        super.onCreate(savedInstanceState)
        val mDataBinding: ActSplashBinding =
            DataBindingUtil.setContentView(this, R.layout.act_splash)

        mDataBinding.bgRoot.animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash)

        ImmersionBar.with(this).reset().fitsSystemWindows(false)
            .navigationBarColor(R.color.black).statusBarDarkFont(false, 0.2f).init()

        welcomeHandler = WelcomeHandler(this)
        welcomeHandler.removeMessages(0)
        welcomeHandler.sendEmptyMessageDelayed(0, 1000 * 2)
    }

    private class WelcomeHandler(activity: SplashAct) : Handler(Looper.myLooper()!!) {

        private val mActivity: WeakReference<SplashAct> = WeakReference(activity)

        override fun handleMessage(msg: Message) {
            if (mActivity.get() == null) {
                return
            }
            val activity = mActivity.get()
            when (msg.what) {
                0 -> {
                    activity?.let {
                        val intent = Intent(it, MainActivity::class.java)
                        it.startActivity(intent)
                        it.finish()
                    }
                }
            }
        }
    }
}