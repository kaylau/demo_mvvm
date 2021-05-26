package com.kay.demo

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.getkeepsafe.relinker.ReLinker
import com.kay.demo.net.net.RetrofitClient
import com.kay.demo.utils.ActManager
import com.kay.demo.utils.ChannelUtils
import com.kay.demo.utils.LogUtils
import com.tencent.mmkv.MMKV
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/25 10:45
 * @Description:
 */
class GlobalApp : MultiDexApplication() {


    /**
     * 当前活动的Activity数量
     */
    private var count = 0

    companion object {
        lateinit var mContext: Application

        @JvmStatic
        fun getContext(): Context {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        RetrofitClient.init()

        ChannelUtils.getChannelCode(this)

        initMMKV()
        initActLifecycle()
        initAutoSize()

    }

    private fun initMMKV() {
        val relativePath = filesDir.absolutePath + "/mmkv"
        if (Build.VERSION.SDK_INT == 19) {
            MMKV.initialize(
                relativePath
            ) { libName -> ReLinker.loadLibrary(this, libName) }
        } else {
            MMKV.initialize(this)
        }
    }

    /**
     * 适配初始化
     */
    private fun initAutoSize() {
        AutoSizeConfig.getInstance().unitsManager
            .setSupportDP(false)
            .setSupportSP(false).supportSubunits = Subunits.MM
    }

    private fun initActLifecycle() {

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                ActManager.push(activity);
            }

            override fun onActivityStarted(activity: Activity) {
                if (count++ == 0) {
                    LogUtils.e(">>>>>>>>>>>>>>>>>>> 切到前台 <<<<<<<<<<<<<<<<<<<")
                }
            }

            override fun onActivityResumed(activity: Activity) {
                ActManager.setTopActivity(activity);
            }

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {
                count--
                if (count == 0) {
                    LogUtils.e(">>>>>>>>>>>>>>>>>>> 切到后台 <<<<<<<<<<<<<<<<<<<")
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {
                ActManager.remove(activity);
            }
        })
    }
}