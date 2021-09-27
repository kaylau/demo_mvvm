package com.kay.demo.mvvm.vm

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.kay.demo.R
import com.kay.demo.base.BaseViewModel
import com.kay.demo.mvvm.model.MineModel
import com.kay.demo.mvvm.view.act.WebViewAct
import com.kay.demo.views.dialog.WheelViewDialog
import com.kay.demo.views.dialog.bottom.BottomSelectUtil
import java.util.*


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

    var gender = MutableLiveData<String>()
    fun selectGender(view: View) {
        val arrayListOf = arrayListOf<String>()
        arrayListOf.add("Male")
        arrayListOf.add("Female")
        BottomSelectUtil.getInstance().show(
                view.context,
                arrayListOf,
                ""
        ) { select ->
            gender.value = select
        }
    }

    var genderWheelView = MutableLiveData<String>()
    fun selectGenderWheelView(view: View) {
        val arrayListOf = arrayListOf<String>()
        arrayListOf.add("Male")
        arrayListOf.add("Female")
        arrayListOf.add("Androgynous")
        arrayListOf.add("Cis Woman")
        arrayListOf.add("Cis Man")
        WheelViewDialog.getInstance().show(
                view.context,
                arrayListOf,
        ) { select ->
            genderWheelView.value = select
        }
    }

    private fun initView(view: View?) {
        view?.let {
            val btnCancel: Button = it.findViewById(R.id.btnCancel)
            val btnSubmit: Button = it.findViewById(R.id.btnSubmit)
            btnCancel.setOnClickListener { timePickerView?.dismiss() }
            btnSubmit.setOnClickListener {
                timePickerView?.returnData()
                timePickerView?.dismiss()
            }
        }
    }

    var birth = MutableLiveData<String>()
    private var timePickerView: TimePickerView? = null
    fun selectBirth(view: View) {

        val selectedDate: Calendar = Calendar.getInstance()
        val startDate: Calendar = Calendar.getInstance()
        val endDate: Calendar = Calendar.getInstance()

//        selectedDate.set(2019, 6.minus(1), 29)
        //正确设置方式 原因：注意事项有说明
        startDate.set(1900, 0, 1)
//        endDate.set(2030, 11, 31)

        timePickerView = TimePickerBuilder(view.context) { date, v ->
            //选中事件回调
            obtainDate(date)
        }
                .setType(booleanArrayOf(true, true, true, false, false, false)) // 默认全部显示
                .setLayoutRes(R.layout.birth_date_layout) { v -> initView(v) }
                .setOutSideCancelable(false) //点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false) //是否循环滚动
                .setDate(selectedDate) // 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate) //起始终止年月日设定
                .setLabel("", "", "", "", "", "") //默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false) //是否显示为对话框样式
                .setLineSpacingMultiplier(2.5f)
                .setItemVisibleCount(5)
                .setTextColorCenter(Color.parseColor("#2A303C"))//选中字体颜色
                .setTextColorOut(Color.parseColor("#BEBEBE")) //未选中字体颜色
                .setDividerColor(Color.parseColor("#EFEFEF")) //选择线的颜色
//            .setTimeSelectChangeListener {
//                obtainDate(it)
//            }
                .build()
        timePickerView!!.show()
    }

    private fun obtainDate(date: Date) {
        val calendar = Calendar.getInstance() //日历对象
        calendar.time = date //设置当前日期
        val yearStr = calendar[Calendar.YEAR].toString() + "" //获取年份
        val month = calendar[Calendar.MONTH] + 1 //获取月份
        val day = calendar[Calendar.DATE] //获取月份
        birth.value = "$day-$month-$yearStr"
    }
}