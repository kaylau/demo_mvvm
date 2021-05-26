package com.kay.demo.mvvm.vm

import androidx.lifecycle.MutableLiveData
import com.kay.demo.base.BaseViewModel
import com.kay.demo.mvvm.model.TabModel


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/29 17:34
 * @Description:
 */
class TabVm : BaseViewModel<TabModel>() {

    var text = MutableLiveData("TabVm")


}