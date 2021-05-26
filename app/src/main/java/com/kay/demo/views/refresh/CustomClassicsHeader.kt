package com.kay.demo.views.refresh

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.scwang.smart.drawable.ProgressDrawable
import com.scwang.smart.refresh.classics.ArrowDrawable
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.scwang.smart.refresh.layout.util.SmartUtil


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/27 14:17
 * @Description:
 */

class CustomClassicsHeader : LinearLayout, RefreshHeader {

    companion object {
        var REFRESH_HEADER_PULLING = "Pull down to refresh" //"下拉可以刷新";
        var REFRESH_HEADER_REFRESHING = "Refreshing" //"正在刷新...";
        var REFRESH_HEADER_RELEASE = "Release starts" //"释放立即刷新";
        var REFRESH_HEADER_FINISH = "Refresh Done" //"刷新完成";
        var REFRESH_HEADER_FAILED = "Refresh Failed" //"刷新失败";
    }

    //标题文本
    private var mHeaderText: TextView? = null

    //下拉箭头
    private var mArrowView: ImageView? = null

    //刷新动画视图
    private var mProgressView: ImageView? = null

    //刷新动画
    private var mProgressDrawable: ProgressDrawable? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context) {
        gravity = Gravity.CENTER
        mHeaderText = TextView(context)
        mHeaderText?.setTextColor(Color.parseColor("#222222"))
        mHeaderText?.textSize = 14f
        mProgressDrawable = ProgressDrawable()
        val mArrowDrawable = ArrowDrawable()
        mArrowDrawable.setColor(-0xddddde)
        mArrowView = ImageView(context)
        mArrowView?.setImageDrawable(mArrowDrawable)
        mProgressView = ImageView(context)
        mProgressView?.setImageDrawable(mProgressDrawable)
        addView(mProgressView, SmartUtil.dp2px(20f), SmartUtil.dp2px(20f))
        addView(mArrowView, SmartUtil.dp2px(20f), SmartUtil.dp2px(20f))
        addView(View(context), SmartUtil.dp2px(20f), SmartUtil.dp2px(20f))
        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        minimumHeight = SmartUtil.dp2px(80f) // 下拉刷新的最小高度
    }

    override fun getView(): View {
        return this //真实的视图就是自己，不能返回null
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate //指定为平移，不能null
    }

    override fun onStartAnimator(layout: RefreshLayout, headHeight: Int, maxDragHeight: Int) {
        mProgressDrawable?.start() //开始动画
    }

    override fun onFinish(layout: RefreshLayout, success: Boolean): Int {
        mProgressDrawable?.stop() //停止动画
        if (success) {
            mHeaderText?.text = REFRESH_HEADER_FINISH
        } else {
            mHeaderText?.text = REFRESH_HEADER_FAILED
        }
        return 500 //延迟500毫秒之后再弹回
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        when (newState) {
            RefreshState.None, RefreshState.PullDownToRefresh -> {
                mHeaderText?.text = REFRESH_HEADER_PULLING
                mArrowView?.visibility = VISIBLE //显示下拉箭头
                mProgressView?.visibility = GONE //隐藏动画
                mArrowView?.animate()?.rotation(0f) //还原箭头方向
            }
            RefreshState.Refreshing -> {
                mHeaderText?.text = REFRESH_HEADER_REFRESHING
                mProgressView?.visibility = VISIBLE //显示加载动画
                mArrowView?.visibility = GONE //隐藏箭头
            }
            RefreshState.ReleaseToRefresh -> {
                mHeaderText?.text = REFRESH_HEADER_RELEASE
                mArrowView?.animate()?.rotation(180f) //显示箭头改为朝上
            }
            else -> {
            }
        }
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {}
    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {

    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {

    }

    override fun setPrimaryColors(@ColorInt vararg colors: Int) {

    }
}
