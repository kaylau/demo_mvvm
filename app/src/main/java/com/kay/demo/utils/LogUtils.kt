package com.kay.demo.utils

import android.util.Log


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/30 11:17
 * @Description:
 */
class LogUtils {

    companion object {
        fun e(tag: String, msg: String) {
            if (AppCfg.IS_DEBUG) {
                Log.e(tag, msg)
            }
        }

        fun e(msg: String) {
            e("LogUtils", msg)
        }
    }
}