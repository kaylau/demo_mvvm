package com.kay.demo.mvvm.view.act

import android.Manifest
import android.annotation.SuppressLint
import com.kay.demo.R
import com.kay.demo.base.BaseAct
import com.kay.demo.databinding.ActLoginBinding
import com.kay.demo.mvvm.vm.LoginVm
import com.tbruyelle.rxpermissions3.RxPermissions


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/31 16:27
 * @Description:
 */
class LoginAct : BaseAct<LoginVm, ActLoginBinding>() {

    override fun getLayoutId(): Int = R.layout.act_login

    override fun bindVM() {
        dataBinding.vm = viewModel
    }

    @SuppressLint("CheckResult")
    override fun initData() {
        RxPermissions(this)
            .request(Manifest.permission.CAMERA)
            .subscribe {

            }
    }


}