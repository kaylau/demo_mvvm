package com.kay.demo.views.refresh

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/27 14:17
 * @Description:
 */

class CustomClassicsFooter @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ClassicsFooter(context, attrs) {

    companion object {
        var REFRESH_FOOTER_PULL_UP = "Pull up to load more"
        var REFRESH_FOOTER_RELEASE = "Release starts"
        var REFRESH_FOOTER_LOADING = "loading..."
        var REFRESH_FOOTER_REFRESHING = "refreshing..."
        var REFRESH_FOOTER_FINISH = "load more success"
        var REFRESH_FOOTER_FAILED = "load more failed"
        var REFRESH_FOOTER_ALL_LOADED = "no more data"
    }

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        mTitleText.text = REFRESH_FOOTER_PULL_UP
        mTitleText.setTextColor(Color.parseColor("#222222"))
    }

    override fun onFinish(layout: RefreshLayout, success: Boolean): Int {
        if (!mNoMoreData) {
            mTitleText.text = if (success) REFRESH_FOOTER_FINISH else REFRESH_FOOTER_FAILED
            return mFinishDuration
        }
        return 0
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    override fun setNoMoreData(noMoreData: Boolean): Boolean {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData
            val arrowView: View = mArrowView
            if (noMoreData) {
                mTitleText.text = REFRESH_FOOTER_ALL_LOADED
                arrowView.visibility = GONE
            } else {
                mTitleText.text = REFRESH_FOOTER_PULL_UP
                arrowView.visibility = VISIBLE
            }
        }
        return true
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        val arrowView: View = mArrowView
        if (!mNoMoreData) {
            when (newState) {
                RefreshState.None -> {
                    arrowView.visibility = VISIBLE
                    mTitleText.text = REFRESH_FOOTER_PULL_UP
                    arrowView.animate().rotation(180f)
                }
                RefreshState.PullUpToLoad -> {
                    mTitleText.text = REFRESH_FOOTER_PULL_UP
                    arrowView.animate().rotation(180f)
                }
                RefreshState.Loading, RefreshState.LoadReleased -> {
                    arrowView.visibility = GONE
                    mTitleText.text = REFRESH_FOOTER_LOADING
                }
                RefreshState.ReleaseToLoad -> {
                    mTitleText.text = REFRESH_FOOTER_RELEASE
                    arrowView.animate().rotation(0f)
                }
                RefreshState.Refreshing -> {
                    mTitleText.text = REFRESH_FOOTER_REFRESHING
                    arrowView.visibility = GONE
                }
                else -> {
                }
            }
        }
    }
}
