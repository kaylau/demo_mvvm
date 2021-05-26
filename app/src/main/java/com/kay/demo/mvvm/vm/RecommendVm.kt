package com.kay.demo.mvvm.vm

import androidx.lifecycle.MutableLiveData
import com.kay.demo.base.BaseViewModel
import com.kay.demo.mvvm.model.RecommendModel


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/29 17:34
 * @Description:
 */
class RecommendVm : BaseViewModel<RecommendModel>() {

    var value = MutableLiveData<String>("RecommendVm")

}