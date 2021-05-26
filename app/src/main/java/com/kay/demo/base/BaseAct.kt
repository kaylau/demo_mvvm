package com.kay.demo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.gyf.barlibrary.ImmersionBar
import com.kay.demo.R
import com.kay.demo.net.util.StateConstants
import com.kay.demo.utils.CustomToast
import com.kay.demo.views.LoadingDialog
import java.lang.reflect.ParameterizedType


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/26 18:57
 * @Description:
 */
abstract class BaseAct<VM : BaseViewModel<*>, DB : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewModel: VM
    protected lateinit var dataBinding: DB
    private var mLoadingDialog: LoadingDialog? = null//加载框

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewDataBinding() //初始化 DataBinding
        initViewModel()//初始化 ViewModel
        initLiveData()
//        ImmersionBar.with(this).reset().fitsSystemWindows(false)
//            .navigationBarColor(R.color.black).statusBarDarkFont(false, 0.2f).init()

        initImmersionBar()

        initData()
    }

    open fun initImmersionBar() {
        ImmersionBar.with(this)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.color_1A000000)
            .navigationBarColor(R.color.black)
            .statusBarDarkFont(true, 0.2f)
            .init()
    }

    abstract fun initData()

    private fun initViewDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        dataBinding.lifecycleOwner = this
    }

    /**
     * @param index 获取泛型的位置,  0为第一个泛型
     * @return 泛型class
     */
    @Suppress("UNCHECKED_CAST")
    private fun getTClass(index: Int): Class<VM> {
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        return parameterizedType.actualTypeArguments[index] as Class<VM>
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(getTClass(0))
        bindVM()
    }

    private fun initLiveData() {
        viewModel.loadingStateLiveData.observe(this, { entity ->
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

        viewModel.netErrorLiveData.observe(this, {
            CustomToast.getInstance(this).show(it.msg)
        })

        viewModel.toastLiveData.observe(this, {
            CustomToast.getInstance(this).show(it)
        })
    }


    /**
     * 加载中处理
     */
    protected open fun onLoading() {
        if (mLoadingDialog == null) mLoadingDialog = LoadingDialog(this)

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

    abstract fun getLayoutId(): Int

    abstract fun bindVM()
}