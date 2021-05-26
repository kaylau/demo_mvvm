package com.kay.demo.net.exc

import android.net.ParseException
import com.google.gson.JsonParseException
import com.kay.demo.GlobalApp
import com.kay.demo.R
import com.kay.demo.utils.getResString
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import javax.net.ssl.SSLHandshakeException


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/26 15:49
 * @Description:
 */

object DealException {

    fun handlerException(t: Throwable): ResultException {
        val ex: ResultException
        if (t is ResultException) {
            ex = t
        } else if (t is HttpException) {
            ex = when (t.code()) {
                ApiResultCode.UNAUTHORIZED,
                ApiResultCode.FORBIDDEN,
                ApiResultCode.NOT_FOUND -> ResultException(
                    t.code().toString(),
                    getResString(GlobalApp.mContext, R.string.network_error)
                )
                ApiResultCode.REQUEST_TIMEOUT,
                ApiResultCode.GATEWAY_TIMEOUT -> ResultException(
                    t.code().toString(),
                    getResString(GlobalApp.mContext, R.string.network_timeout)
                )
                ApiResultCode.INTERNAL_SERVER_ERROR,
                ApiResultCode.BAD_GATEWAY,
                ApiResultCode.SERVICE_UNAVAILABLE -> ResultException(
                    t.code().toString(),
                    getResString(GlobalApp.mContext, R.string.network_system_error)
                )
                else -> ResultException(
                    t.code().toString(),
                    getResString(GlobalApp.mContext, R.string.network_error)
                )
            }
        } else if (t is JsonParseException
            || t is JSONException
            || t is ParseException
        ) {
            ex = ResultException(
                ApiResultCode.PARSE_ERROR,
                getResString(GlobalApp.mContext, R.string.parse_error)
            )
        } else if (t is SocketException) {
            ex = ResultException(
                ApiResultCode.REQUEST_TIMEOUT.toString(),
                getResString(GlobalApp.mContext, R.string.network_socket_error)

            )
        } else if (t is SocketTimeoutException) {
            ex = ResultException(
                ApiResultCode.REQUEST_TIMEOUT.toString(),
                getResString(GlobalApp.mContext, R.string.network_socket_timeout)
            )
        } else if (t is SSLHandshakeException) {
            ex = ResultException(
                ApiResultCode.SSL_ERROR,
                getResString(GlobalApp.mContext, R.string.network_ssl_error)
            )
            return ex
        } else if (t is UnknownHostException) {
            ex = ResultException(
                ApiResultCode.UNKNOW_HOST,
                getResString(GlobalApp.mContext, R.string.network_unknown_host)
            )
            return ex
        } else if (t is UnknownServiceException) {
            ex = ResultException(
                ApiResultCode.UNKNOW_HOST,
                getResString(GlobalApp.mContext, R.string.network_unknown_host)
            )
        } else if (t is NumberFormatException) {
            ex = ResultException(
                ApiResultCode.UNKNOW_HOST,
                getResString(GlobalApp.mContext, R.string.network_number_format_error)
            )
        } else {
            ex = ResultException(
                ApiResultCode.UNKNOWN,
                getResString(GlobalApp.mContext, R.string.network_unknown_error)
            )
        }
        return ex
    }
}