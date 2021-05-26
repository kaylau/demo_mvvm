package com.kay.demo.net.model

import com.kay.demo.net.exc.ResultException


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/26 15:47
 * @Description:
 */
sealed class NetResult<out O : Any> {

    data class Success<T : Any>(val response: BaseResponse<T>) : NetResult<T>()

    data class Error(val exception: ResultException) : NetResult<Nothing>()


}