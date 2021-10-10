package com.kay.demo.mvvm.vm

import androidx.lifecycle.MutableLiveData
import com.kay.demo.base.BaseViewModel
import com.kay.demo.mvvm.model.LoginModel
import com.kay.demo.net.exc.ResultException
import com.kay.demo.net.listener.OnResultListener
import com.kay.demo.net.model.BaseResponse
import com.kay.demo.net.model.StateEntity
import com.kay.demo.net.model.request.LoginReq
import com.kay.demo.net.model.request.RegisterReq
import com.kay.demo.net.model.respone.LoginSignUpRsp
import com.kay.demo.net.util.StateConstants
import com.kay.demo.utils.MMKVUtils


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/31 16:28
 * @Description:
 */
class LoginVm : BaseViewModel<LoginModel>() {

    var registerName = MutableLiveData("9876543210")
    var registerPwd = MutableLiveData("123456")
    var loginName = MutableLiveData("9876543210")
    var loginPwd = MutableLiveData("123456")


    //    {"deviceId":"d045500edfe9f28e","email":"","gpsAdid":"31474879-0534-4950-ab8b-c013f8a5115f","loginName":"6000000000","password":"123456","phoneNumber":""}
    fun register() {
        val req = RegisterReq(
            loginName = registerName.value,
            password = registerPwd.value,
            deviceId = "d045500edfe9f28e",
        )
        networkModel.register(req, object : OnResultListener<LoginSignUpRsp> {
            override fun onLoading() {
                loadingStateLiveData.value = StateEntity(StateConstants.LOADING_OPEN)
            }

            override fun closeLoading() {
                loadingStateLiveData.value = StateEntity(StateConstants.LOADING_CLOSE)
            }

            override fun onFailed(error: ResultException) {
                closeLoading()
                netErrorLiveData.postValue(error)
            }

            override fun onSuccess(response: BaseResponse<LoginSignUpRsp>) {
                response.data?.let {
                    MMKVUtils.put("token", it.token)
                }
                toastLiveData.postValue(response.message)
            }
        })
    }

    fun login() {
        val loginReq = LoginReq(
            loginName = loginName.value,
            password = loginPwd.value
        )
        networkModel.login(loginReq, object : OnResultListener<LoginSignUpRsp> {
            override fun onLoading() {
                loadingStateLiveData.value = StateEntity(StateConstants.LOADING_OPEN)
            }

            override fun closeLoading() {
                loadingStateLiveData.value = StateEntity(StateConstants.LOADING_CLOSE)
            }

            override fun onFailed(error: ResultException) {
                closeLoading()
                netErrorLiveData.postValue(error)
            }

            override fun onSuccess(response: BaseResponse<LoginSignUpRsp>) {
                response.data?.let {
                    MMKVUtils.put("token", it.token)
                }
                toastLiveData.postValue(response.message)
            }
        })
    }

}