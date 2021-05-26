package com.kay.demo.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import java.util.regex.Pattern


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/26 16:38
 * @Description:
 */


fun getResColor(context: Context, colorId: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ContextCompat.getColor(context, colorId)
    } else {
        context.resources.getColor(colorId)
    }
}

fun getResString(context: Context, strId: Int): String {
    return context.getString(strId)
}


fun openAppSettings(activity: Activity) {
    try {
        val info = activity.packageManager.getPackageInfo(activity.packageName, 0)
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + info.packageName)
        activity.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
//        activity.finish()
//        exitProcess(0)
    }
}


fun keepDecimalPoint(editText: EditText, firstLen: Int, lastLen: Int) {
    editText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
    editText.filters = arrayOf<InputFilter>(object : InputFilter {
        var decimalNumber = lastLen //小数点后保留位数

        //source:即将输入的内容 dest：原来输入的内容
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            val sourceContent = source.toString()
            val lastInputContent = dest.toString()

            //验证删除等按键
            if (TextUtils.isEmpty(sourceContent)) {
                return ""
            }
            //以小数点"."开头，默认为设置为“0.”开头
            if (sourceContent == "." && lastInputContent.isEmpty()) {
                return "0."
            }
            //输入“0”，默认设置为以"0."开头
            if (sourceContent == "0" && lastInputContent.isEmpty()) {
                return "0."
            }
            //小数点后保留两位
            if (lastInputContent.contains(".")) {
                val index = lastInputContent.indexOf(".")
                if (dend - index >= decimalNumber + 1) {
                    return ""
                }
            }
            return null
        }
    })

    editText.addTextChangedListener(object : TextWatcher {
        var temp: String? = null
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            temp = s.toString()
            val posDot = temp!!.indexOf(".")
            //没有小数点，控制小数点前的位数
            if (posDot <= 0) {
                if (temp!!.length > firstLen) {
                    //删除第二位后面的内容
                    s.delete(firstLen, temp!!.length)
                }
                return
            }
            if (temp == "0.00") {
                //输入“0.00”时清除内容
                s.clear()
            }
        }
    })
}

fun matchEmail(str: String?): Boolean {
    if (str.isNullOrEmpty()) {
        return false
    }
    val regex = "^[a-zA-Z0-9-.]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+\$"
    val pattern = Pattern.compile(regex)
    return pattern.matcher(str).find()
}

fun matchPhoneNum(str: String?): Boolean {
    if (str.isNullOrEmpty()) {
        return false
    }
//    val regex = "^\\d{10}"
    val regex = "^[1-9][0-9]{9}\$"
    val pattern = Pattern.compile(regex)
    return pattern.matcher(str).find()
}


fun showInput(activity: Activity, et: EditText) {
    et.requestFocus()
    val imm: InputMethodManager? =
        activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
}

fun copyText(activity: Activity, text: String) {
    //获取剪贴板管理器：
    val cm: ClipboardManager? =
        activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    // 创建普通字符型ClipData
    val mClipData = ClipData.newPlainText("", text)
    // 将ClipData内容放到系统剪贴板里。
    cm?.setPrimaryClip(mClipData)
}