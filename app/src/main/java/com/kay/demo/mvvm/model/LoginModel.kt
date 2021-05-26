package com.kay.demo.mvvm.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kay.demo.base.BaseModel
import com.kay.demo.net.ApiNetWork
import com.kay.demo.net.listener.OnResultListener
import com.kay.demo.net.model.NetResult
import com.kay.demo.net.model.request.LoginReq
import com.kay.demo.net.model.request.RegisterReq
import com.kay.demo.net.model.respone.LoginSignUpRsp
import kotlinx.coroutines.launch


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/31 16:28
 * @Description:
 */
class LoginModel(viewModel: ViewModel) : BaseModel(viewModel) {

    fun register(req: RegisterReq, listener: OnResultListener<LoginSignUpRsp>) {
        viewModel.viewModelScope.launch {
            listener.onLoading()
            val rsp = ApiNetWork.register(req)
            if (rsp is NetResult.Success) {
                listener.closeLoading()
                listener.onSuccess(rsp.response)

            } else if (rsp is NetResult.Error) {
                listener.closeLoading()
                listener.onFailed(rsp.exception)
            }
        }
    }

    fun login(req: LoginReq, listener: OnResultListener<LoginSignUpRsp>) {
        viewModel.viewModelScope.launch {
            listener.onLoading()
            val rsp = ApiNetWork.login(req)
            if (rsp is NetResult.Success) {
                listener.onSuccess(rsp.response)
                listener.closeLoading()

            } else if (rsp is NetResult.Error) {
                listener.onFailed(rsp.exception)
                listener.closeLoading()
            }
        }
    }

}