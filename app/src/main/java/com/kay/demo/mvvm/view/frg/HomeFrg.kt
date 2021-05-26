package com.kay.demo.mvvm.view.frg

import android.view.View
import com.kay.demo.R
import com.kay.demo.adapter.TipsBannerAdapter
import com.kay.demo.base.BaseFrg
import com.kay.demo.databinding.FrgHomeBinding
import com.kay.demo.mvvm.vm.HomeVm
import com.kay.demo.net.model.respone.ListBean
import com.kay.demo.utils.CustomToast
import com.kay.demo.utils.glide.GlideUtils
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/29 17:33
 * @Description:
 */
class HomeFrg : BaseFrg<HomeVm, FrgHomeBinding>(), OnRefreshLoadMoreListener {


    override fun getViewResId(): Int = R.layout.frg_home

    override fun viewModelClz(): Class<HomeVm> = HomeVm::class.java

    override fun bindVM() {
        dataBinding.vm = viewModel
    }

    override fun initView(root: View) {
        initBanner()
        initBannerTips()
        initRefreshView()
    }

    private fun initBanner() {
        val bannerData = mutableListOf<String>()
        bannerData.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.gdptl.com%2Fd%2Ffile%2Fmoban5%2F202001192027%2F15793983867402800.jpg&refer=http%3A%2F%2Fwww.gdptl.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1624093027&t=f84f7de9ad01e33a9b172c7977a52894")
        bannerData.add("")
        bannerData.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4066712306,2414609633&fm=26&gp=0.jpg")
        dataBinding.banner.indicator = CircleIndicator(mActivity)
        dataBinding.banner.adapter = object : BannerImageAdapter<String>(bannerData) {
            override fun onBindView(
                holder: BannerImageHolder?,
                imageUrl: String?,
                position: Int,
                size: Int
            ) {
                //图片加载
                GlideUtils.load(
                    holder?.imageView,
                    imageUrl,
                    R.mipmap.ic_launcher,
                    R.mipmap.ic_launcher
                )
            }
        }
        dataBinding.banner.setOnBannerListener { data, position ->
            CustomToast.getInstance(mActivity).show("banner: $position")
        }
        dataBinding.banner.setLoopTime(1000 * 3)
        dataBinding.banner.start()
        // dataBinding.banner.setDatas(bannerData)
    }

    private fun initBannerTips() {
        val tipsDataBeans = mutableListOf<String>()
        tipsDataBeans.add("qwertyosdhhsfhalfgfaflflhih    qwertyosdhhsfhalfgfaflflhih")
        tipsDataBeans.add("136547474gsfsdfsd")
        tipsDataBeans.add("AD342gsggsfgfg")
        dataBinding.bannerMsg.adapter = TipsBannerAdapter(tipsDataBeans)
        dataBinding.bannerMsg.setUserInputEnabled(false)
        dataBinding.bannerMsg.setOrientation(Banner.VERTICAL)
        dataBinding.bannerMsg.setLoopTime(1000 * 2)
    }

    private fun initRefreshView() {
        dataBinding.refreshView.setOnRefreshLoadMoreListener(this)
        viewModel.isRefreshList.observe(this, {
            if (it == true) {
                dataBinding.recyclerView.adapter?.notifyDataSetChanged()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        dataBinding.banner.start()
        dataBinding.bannerMsg.start()
    }

    override fun onStop() {
        super.onStop()
        dataBinding.banner.stop()
        dataBinding.bannerMsg.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding.banner.destroy()
        dataBinding.bannerMsg.destroy()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        val newList = mutableListOf<ListBean>()
        val oldList = mutableListOf<ListBean>()
        oldList.addAll(viewModel.itemList)

        val randomsType1 = (0..2).random()
        val randomsType2 = (0..2).random()
        val randomsName1 = (1..100).random()
        val randomsName2 = (1..100).random()
        newList.add(ListBean(name = "refresh $randomsName1", type = randomsType1))
        newList.add(ListBean(name = "refresh $randomsName2", type = randomsType2))
        viewModel.itemList.clear()
        viewModel.itemList.addAll(newList)
        viewModel.itemList.addAll(oldList)
        viewModel.isRefreshList.value = true
        refreshLayout.finishRefresh()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        val randomsType1 = (0..2).random()
        val randomsType2 = (0..2).random()
        val randomsName1 = (1..100).random()
        val randomsName2 = (1..100).random()
        viewModel.itemList.add(ListBean(name = "load more $randomsName1", type = randomsType1))
        viewModel.itemList.add(ListBean(name = "load more $randomsName2", type = randomsType2))
        viewModel.isRefreshList.value = true
        refreshLayout.finishLoadMore()
    }
}