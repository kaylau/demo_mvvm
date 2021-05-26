package com.kay.demo.net.model.respone

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.kay.demo.BR


/**
 * @Author: Administrator
 * @CreateDate: 2021/5/25 16:24
 * @Description:
 */
class ItemData() : BaseObservable() {

    private var name: String = ""
    private var imageId: Int = 0

    constructor(name: String, imageId: Int) : this() {
        this.name = name
        this.imageId = imageId
    }

    @Bindable
    fun getName(): String {
        return name
    }

    @Bindable
    fun getImageId(): Int {
        return imageId
    }

    fun setName(name: String) {
        this.name = name
        notifyPropertyChanged(BR.name)
    }

    fun setImageId(imageId: Int) {
        this.imageId = imageId
        notifyPropertyChanged(BR.imageId)
    }
}