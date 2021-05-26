package com.kay.demo.mvvm.vm

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.kay.demo.base.BaseViewModel
import com.kay.demo.mvvm.model.MineModel
import com.kay.demo.mvvm.view.act.WebViewAct


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/29 17:34
 * @Description:
 */
class MineVm : BaseViewModel<MineModel>() {

    var value = MutableLiveData<String>("MineVm")

    fun clickWebView(view: View) {
        val bundle = Bundle()
        bundle.putString("url", "https://www.baidu.com/")
        bundle.putString("title", "WebView")
        val intent = Intent(view.context, WebViewAct::class.java)
        intent.putExtra("bundle", bundle)
        view.context.startActivity(intent)
    }
}