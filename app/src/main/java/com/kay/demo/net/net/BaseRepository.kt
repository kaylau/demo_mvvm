package com.kay.demo.net.net

import android.util.Log
import com.kay.demo.net.exc.DealException
import com.kay.demo.net.exc.ResultException
import com.kay.demo.net.model.BaseResponse
import com.kay.demo.net.model.NetResult
import com.kay.demo.net.util.NetCfg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/25 18:51
 * @Description:
 */
open class BaseRepository {

    private val tag: String = BaseRepository::class.java.simpleName

    suspend fun <T : Any> callRequest(
        call: suspend () -> NetResult<T>
    ): NetResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            //这里统一处理异常
            e.printStackTrace()
            val exception = DealException.handlerException(e)
            Log.e(tag, "callRequest errCode: ${exception.errCode.toString()}")
            Log.e(tag, "callRequest msg: ${exception.msg}")
            NetResult.Error(exception)
        }
    }

    suspend fun <T : Any> handleResponse(
        response: BaseResponse<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): NetResult<T> {
        return coroutineScope {
            when (response.returnCode) {
                NetCfg.RET_CODE_SUCCESS -> {
                    successBlock?.let { it() }
                    NetResult.Success(response)
                }
//                NetCfg.RET_CODE_OTHER_LOGIN -> {
//                    // 异地登录
//                    NetResult.Error(ResultException("", ""))
//                }
                else -> {
                    errorBlock?.let { it() }
                    Log.e(tag, "handleResponse returnCode: ${response.returnCode}")
                    Log.e(tag, "handleResponse ex: ${response.ex}")
                    NetResult.Error(
                        ResultException(
                            response.returnCode.toString(),
                            response.message
                        )
                    )
                }
            }
        }
    }
}