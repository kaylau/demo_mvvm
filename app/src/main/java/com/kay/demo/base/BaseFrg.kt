package com.kay.demo.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kay.demo.net.util.StateConstants
import com.kay.demo.utils.CustomToast
import com.kay.demo.views.LoadingDialog


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/29 17:27
 * @Description:
 */
abstract class BaseFrg<VM : BaseViewModel<*>, T : ViewDataBinding> : Fragment() {

    protected lateinit var viewModel: VM
    protected lateinit var dataBinding: T
    protected var mActivity: Activity? = null
    private var mLoadingDialog: LoadingDialog? = null//加载框

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = activity
    }

    override fun onAttach(act: Activity) {
        super.onAttach(act)
        mActivity = activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil
            .inflate<T>(inflater, getViewResId(), container, false)
            .apply {
                dataBinding = this
                initViewModel()
                initView(this.root)
                initLiveData()
            }.root
    }

    private fun initViewModel() {
        mActivity?.let {
            viewModel = ViewModelProvider(requireActivity()).get(viewModelClz())
        }
        bindVM()
        dataBinding.lifecycleOwner = this
    }

    private fun initLiveData() {
        viewModel.loadingStateLiveData.observe(viewLifecycleOwner, { entity ->
            entity?.let {
                when (it.state) {
                    StateConstants.LOADING_OPEN -> {
                        onLoading()
                    }

                    StateConstants.LOADING_CLOSE -> {
                        closeLoading()
                    }

                    StateConstants.NET_ERROR -> {
                        onNetError()
                    }
                }
            }
        })

        viewModel.netErrorLiveData.observe(viewLifecycleOwner, {
            CustomToast.getInstance(mActivity).show(it.msg)
        })
    }

    abstract fun getViewResId(): Int

    abstract fun viewModelClz(): Class<VM>

    abstract fun bindVM()

    abstract fun initView(root: View)


    /**
     * 加载中处理
     */
    protected open fun onLoading() {
        if (mLoadingDialog == null) mLoadingDialog = mActivity?.let { LoadingDialog(it) }

        if (mLoadingDialog?.isShowing == false) mLoadingDialog?.show()
    }

    /**
     * 加载完成处理
     */
    protected open fun closeLoading() {
        if (mLoadingDialog?.isShowing == true) {
            mLoadingDialog?.dismiss()
        }
    }

    /**
     * 处理网络异常
     */
    protected open fun onNetError() {

    }
}