package com.kay.demo.mvvm.vm

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import com.kay.demo.net.model.request.FeedbackReq
import com.google.gson.Gson
import com.kay.demo.base.BaseViewModel
import com.kay.demo.mvvm.model.MainModel
import com.kay.demo.net.exc.ResultException
import com.kay.demo.net.listener.OnResultListener
import com.kay.demo.net.model.BaseResponse
import com.kay.demo.net.model.StateEntity
import com.kay.demo.net.util.StateConstants
import com.kay.demo.utils.JSONTool
import com.kay.demo.utils.SystemUtils


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/26 18:44
 * @Description:
 */
class MainVm : BaseViewModel<MainModel>() {

    var fbJsonRsp = MutableLiveData<String>()

    var feedback = MutableLiveData<String>()
    var btnEnable = MutableLiveData(false)


    val feedbackChange = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val text = s.toString()
            btnEnable.value = text.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    fun postReq() {
        val feedbackReq = FeedbackReq(
            SystemUtils.getIMEI(),
            feedback.value
        )
        networkModel.feedback(feedbackReq, object : OnResultListener<Nothing> {

            override fun onLoading() {
                loadingStateLiveData.value = StateEntity(StateConstants.LOADING_OPEN)
            }

            override fun closeLoading() {
                loadingStateLiveData.value = StateEntity(StateConstants.LOADING_CLOSE)
            }

            override fun onSuccess(response: BaseResponse<Nothing>) {
                closeLoading()
                fbJsonRsp.postValue(JSONTool.stringToJSON(Gson().toJson(response)))
            }

            override fun onFailed(error: ResultException) {
                closeLoading()
                netErrorLiveData.postValue(error)
            }
        })
    }

}