package com.kay.demo.mvvm.vm

import androidx.lifecycle.MutableLiveData
import com.kay.demo.R
import com.kay.demo.base.BaseViewModel
import com.kay.demo.mvvm.model.HomeModel
import com.kay.demo.net.model.respone.ListBean
import com.kay.demo.utils.LogUtils
import me.tatarka.bindingcollectionadapter2.BR
import me.tatarka.bindingcollectionadapter2.OnItemBind


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/29 17:34
 * @Description:
 */
class HomeVm : BaseViewModel<HomeModel>() {

    var value = MutableLiveData("HomeVm")
    var isRefreshList = MutableLiveData(false)

    interface OnItemClickListener {
        fun onItemClick(itemData: ListBean)
    }


    //        var item: ItemBinding<ListBean>? = null
    var item: OnItemBind<ListBean>? = null
    var itemList = mutableListOf<ListBean>()

    init {
        itemList.add(
            ListBean(
                name = "123",
                type = 0,
                url = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4066712306,2414609633&fm=26&gp=0.jpg"
            )
        )
        itemList.add(ListBean(name = "78", type = 1))
        itemList.add(ListBean(name = "qwe", type = 2))
        itemList.add(ListBean(name = "asd", type = 0))
        itemList.add(ListBean(name = "zxc", type = 2))
        itemList.add(ListBean(name = "567", type = 1))
        itemList.add(ListBean(name = "1ad", type = 2))
        itemList.add(ListBean(name = "432", type = 0))
        itemList.add(ListBean(name = "gfs", type = 2))
        itemList.add(ListBean(name = "zxc", type = 0))
        itemList.add(ListBean(name = "65yt", type = 2))
        itemList.add(ListBean(name = "czx6", type = 1))
        itemList.add(ListBean(name = "7890sd", type = 1))
        itemList.add(ListBean(name = "654fdg", type = 2))
        itemList.add(ListBean(name = "asd3", type = 0))
        itemList.add(ListBean(name = "43cxv", type = 1))
//        item = ItemBinding.of<ListBean>(BR.item, R.layout.item_layout_a)
//            .bindExtra(BR.listener, object : OnItemClickListener {
//                override fun onItemClick(itemData: ListBean) {
//                    LogUtils.e("click ${itemData.name}")
//                }
//            }).bindExtra(BR.listData, itemList)

        item = OnItemBind<ListBean> { itemBinding, position, item ->
            itemBinding?.set(
                BR.item,
                when (item.type) {
                    0 -> R.layout.item_layout_a
                    1 -> R.layout.item_layout_b
                    2 -> R.layout.item_layout_c
                    else -> R.layout.item_layout_a
                }
            )
            itemBinding?.bindExtra(BR.listData, itemList)
            itemBinding?.bindExtra(BR.listener, object : OnItemClickListener {
                override fun onItemClick(itemData: ListBean) {
                    LogUtils.e("click ${itemData.name}")
                }
            })
        }
    }

}