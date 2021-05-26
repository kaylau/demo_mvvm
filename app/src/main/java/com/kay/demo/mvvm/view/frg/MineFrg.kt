package com.kay.demo.mvvm.view.frg

import android.view.View
import com.kay.demo.R
import com.kay.demo.base.BaseFrg
import com.kay.demo.databinding.FrgMineBinding
import com.kay.demo.mvvm.vm.MineVm


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/29 17:33
 * @Description:
 */
class MineFrg : BaseFrg<MineVm, FrgMineBinding>() {


    override fun getViewResId(): Int = R.layout.frg_mine

    override fun viewModelClz(): Class<MineVm> = MineVm::class.java

    override fun bindVM() {
        dataBinding.vm = viewModel
    }

    override fun initView(root: View) {


    }

}