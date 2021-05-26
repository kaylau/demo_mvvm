package com.kay.demo.views.refresh

import android.content.Context
import android.util.AttributeSet
import com.scwang.smart.refresh.layout.SmartRefreshLayout


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/27 14:16
 * @Description:
 */
class CommonRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SmartRefreshLayout(context, attrs) {
    init {
//        val header = MaterialHeader(context)
//        header.setColorSchemeResources(R.color.color_1B9FF8) // 内部转圈箭头颜色
//        header.setProgressBackgroundColorSchemeResource(R.color.transparent) // 圆圈背景颜色
//        header.setBackgroundResource(R.color.color_0E1825) // CommonRefreshLayout背景颜色
        val header = CustomClassicsHeader(context)
        setRefreshHeader(header)
        val footer = CustomClassicsFooter(context)
        setRefreshFooter(footer)
    }
}
