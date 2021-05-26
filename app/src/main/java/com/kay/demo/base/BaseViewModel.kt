package com.kay.demo.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kay.demo.net.exc.ResultException
import com.kay.demo.net.model.StateEntity
import java.lang.reflect.ParameterizedType


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/26 18:55
 * @Description:
 */
abstract class BaseViewModel <T : BaseModel> : ViewModel {


    protected var networkModel: T

    var loadingStateLiveData = MutableLiveData<StateEntity>()
    var netErrorLiveData = MutableLiveData<ResultException>()
    var toastLiveData = MutableLiveData<String>()

    constructor() {
        getTClass(0).also {
            networkModel = it.getConstructor(ViewModel::class.java)
                .newInstance(this)
        }
    }


    /**
     * @param index 获取泛型的位置,  0为第一个泛型
     * @return 泛型class
     */
    @Suppress("UNCHECKED_CAST")
    private fun getTClass(index: Int): Class<T> {
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        return parameterizedType.actualTypeArguments[index] as Class<T>
    }
}