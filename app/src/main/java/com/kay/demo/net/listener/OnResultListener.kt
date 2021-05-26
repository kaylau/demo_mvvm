package com.kay.demo.net.listener

import com.kay.demo.net.exc.ResultException
import com.kay.demo.net.model.BaseResponse


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/30 14:52
 * @Description:
 */
interface OnResultListener<T> {

    fun onLoading()

    fun closeLoading()

    fun onFailed(error: ResultException)

    fun onSuccess(response: BaseResponse<T>)
}
