package com.kay.demo.utils

import android.content.Context
import android.content.pm.PackageManager


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/30 11:16
 * @Description:
 */
class ChannelUtils {

    companion object {
        fun getChannelCode(context: Context?): String {
            var channel: String = AppCfg.CHANNEL_VALUE
            if (context == null) {
                return channel
            }
            try {
                val info = context.packageManager.getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA
                )
                channel = info.metaData.getString("CHANNEL").toString()
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                LogUtils.e(e.message.toString())
            }
            LogUtils.e("-------------------------> ChannelCode: $channel")
            return channel
        }

    }
}