package com.kay.demo.mvvm.vm

import androidx.lifecycle.MutableLiveData
import com.kay.demo.base.BaseViewModel
import com.kay.demo.mvvm.model.MineModel


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/29 17:34
 * @Description:
 */
class MineVm : BaseViewModel<MineModel>() {

    var value = MutableLiveData<String>("MineVm")

}