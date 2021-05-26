package com.kay.demo.mvvm.view.frg

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kay.demo.R
import com.kay.demo.adapter.TextAdapter
import com.kay.demo.base.BaseFrg
import com.kay.demo.databinding.FrgTabBinding
import com.kay.demo.mvvm.vm.TabVm
import com.kay.demo.net.model.respone.ItemData
import com.kay.demo.utils.LogUtils


/**
 * @Author: Administrator
 * @CreateDate: 2021/5/20 14:33
 * @Description:
 */
class TabFrg : BaseFrg<TabVm, FrgTabBinding>() {

    override fun getViewResId(): Int = R.layout.frg_tab

    override fun viewModelClz(): Class<TabVm> = TabVm::class.java

    override fun bindVM() {
        dataBinding.vm = viewModel
    }

    override fun initView(root: View) {
        arguments?.let {
            val content = it.getString("content")
            val position = it.getInt("position")
            dataBinding.tvContent.text = "这是标签页 $content, ------position---->$position"

            val listData = mutableListOf<ItemData>()
            for (i in 1..20) {
                listData.add(ItemData("item $content -------> $i", R.mipmap.ic_launcher))
            }
            dataBinding.recyclerView.layoutManager = LinearLayoutManager(mActivity)
            dataBinding.recyclerView.adapter =
                TextAdapter(listData, object : TextAdapter.OnItemClick {
                    override fun itemClick(item: ItemData) {
                        LogUtils.e("item: ${item.getName()}")
                    }
                })
        }

    }
}