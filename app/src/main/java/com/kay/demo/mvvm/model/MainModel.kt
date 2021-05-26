package com.kay.demo.mvvm.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kay.demo.net.model.request.FeedbackReq
import com.kay.demo.base.BaseModel
import com.kay.demo.net.ApiNetWork
import com.kay.demo.net.listener.OnResultListener
import com.kay.demo.net.model.NetResult
import kotlinx.coroutines.launch


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/27 14:11
 * @Description:
 */
class MainModel(viewModel: ViewModel) : BaseModel(viewModel) {


    fun feedback(req: FeedbackReq, listener: OnResultListener<Nothing>) {
        viewModel.viewModelScope.launch {
            listener.onLoading()
            val rsp = ApiNetWork.feedback(req)
            if (rsp is NetResult.Success) {
                listener.closeLoading()
                listener.onSuccess(rsp.response)

            } else if (rsp is NetResult.Error) {
                listener.closeLoading()
                listener.onFailed(rsp.exception)
            }
        }
    }
}