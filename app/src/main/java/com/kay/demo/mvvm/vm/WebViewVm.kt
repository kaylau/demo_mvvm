package com.kay.demo.mvvm.vm

import androidx.lifecycle.MutableLiveData
import com.kay.demo.base.BaseViewModel
import com.kay.demo.mvvm.model.WebViewModel


/**
 * @Author: Administrator
 * @CreateDate: 2021/5/26 11:22
 * @Description:
 */
class WebViewVm: BaseViewModel<WebViewModel>() {

    var title = MutableLiveData("WebView")
}