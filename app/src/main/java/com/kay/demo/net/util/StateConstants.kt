package com.kay.demo.net.util

import androidx.annotation.IntDef

/**
 * 统一处理状态码（自定义）
 */
class StateConstants {

    //1开始加载， 2结束加载， 3网络错误
    @IntDef(value = [LOADING_OPEN, LOADING_CLOSE, NET_ERROR])
    @Retention(AnnotationRetention.SOURCE)
    annotation class Types

    companion object {
        const val LOADING_OPEN = 1
        const val LOADING_CLOSE = 2
        const val NET_ERROR = 3
    }


}