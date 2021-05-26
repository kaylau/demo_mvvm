package com.kay.demo.views

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.kay.demo.R


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/29 17:30
 * @Description:
 */
class LoadingDialog(activity: Activity) : Dialog(activity) {
    var isAlpha = true

    init {
        setContentView(R.layout.dialog_loading)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //禁用返回键
//        setCancelable(false)
    }

    fun show(isAlpha: Boolean) {
        this.isAlpha = isAlpha
        show()
    }

    override fun show() {
        if (!isAlpha) {
            val lp = window?.attributes
            lp?.dimAmount = 0f
            window?.attributes = lp
        }
        super.show()
    }
}