package com.kay.demo.mvvm.view.frg

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kay.demo.R
import com.kay.demo.base.BaseFrg
import com.kay.demo.databinding.FrgRecommendBinding
import com.kay.demo.databinding.TabItemLayoutBinding
import com.kay.demo.mvvm.vm.RecommendVm
import kotlinx.android.synthetic.main.tab_item_layout.view.*


/**
 * @Author: Administrator
 * @CreateDate: 2021/3/29 17:33
 * @Description:
 */
class RecommendFrg : BaseFrg<RecommendVm, FrgRecommendBinding>() {

    private var titleList = mutableListOf<String>()
    private var customViewList = mutableListOf<View>()


    override fun getViewResId(): Int = R.layout.frg_recommend

    override fun viewModelClz(): Class<RecommendVm> = RecommendVm::class.java

    override fun bindVM() {
        dataBinding.vm = viewModel
    }

    override fun initView(root: View) {
        addTabTitleList()
        // 添加tab
        for (i in titleList.indices) {
            dataBinding.tabLayout.addTab(dataBinding.tabLayout.newTab().setText(titleList[i]))
        }

        val fragments = mutableListOf<Fragment>()

        for (i in titleList.indices) {
            val listFragment = TabFrg()
            val bundle = Bundle()
            val sb = StringBuffer()
            for (j in 1..2) {
                sb.append(titleList[i]).append(" --> ")
            }
            bundle.putString("content", sb.toString())
            bundle.putInt("position", i)
            listFragment.arguments = bundle
            fragments.add(listFragment)
        }

        val viewPageAdapter = ViewPageAdapter(childFragmentManager, fragments)
        dataBinding.viewPager.adapter = viewPageAdapter
        dataBinding.tabLayout.setupWithViewPager(dataBinding.viewPager)

        // 添加 tab customView
        for (i in titleList.indices) {
            dataBinding.tabLayout.getTabAt(i)?.customView = customViewList[i]
            // 默认选中第一个
            if (i == 0) {
                selectTab(i, 0)
            }
        }

        dataBinding.viewPager.setPageTransformer(true, MTransformer())

        dataBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab!!.position
                for (i in customViewList.indices) {
                    selectTab(i, position)
                }
                dataBinding.viewPager.setCurrentItem(position, false)
            }
        })
    }

    private fun addTabTitleList() {
        addTab("推荐")
        addTab("视频")
        addTab("热点")
        addTab("娱乐")
        addTab("体育")
        addTab("北京")
        addTab("财经")
        addTab("科技")
        addTab("汽车")
        addTab("社会")
        addTab("搞笑")
        addTab("军事")
        addTab("历史")
        addTab("涨知识")
        addTab("NBA")
    }

    private fun addTab(tab: String) {
        titleList.add(tab)
        val customView = getTabView(tab, 16)
        customViewList.add(customView)
        dataBinding.tabLayout.addTab(dataBinding.tabLayout.newTab().setCustomView(customView))
    }

    private fun getTabView(text: String, textSize: Int): View {
        val inflateBinding = DataBindingUtil.inflate<TabItemLayoutBinding>(
            layoutInflater,
            R.layout.tab_item_layout,
            null,
            false
        )
        inflateBinding.tabItemText.text = text
        inflateBinding.tabItemText.textSize = textSize.toFloat()

        return inflateBinding.root
    }

    private fun selectTab(i: Int, position: Int) {
        val view = customViewList[i]
        val itemText = view.tab_item_text
        if (i == position) {
            itemText.setTextColor(Color.RED)
        } else {
            itemText.setTextColor(Color.BLACK)
        }
    }

    /**
     * BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
     *表示只有当前片段会在{@link Lifecycle.State＃RESUMED}中
     * 状态。 所有其他片段的上限为{@link Lifecycle.State＃STARTED}。
     */
    inner class ViewPageAdapter(fm: FragmentManager, list: List<Fragment>) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val mList = list

        override fun getItem(position: Int): Fragment {
            return mList[position]
        }

        override fun getCount(): Int {
            return mList.size
        }
    }

    inner class MTransformer : ViewPager.PageTransformer {

        /**
         * 回调方法,重写viewpager的切换动画
         */
        override fun transformPage(view: View, position: Float) {
            val pageWidth = view.width
            val wallpaper = view.findViewById<View>(R.id.bgFragment)

            when {
                position < -1 -> {
                    wallpaper.translationX = 0.toFloat()
                    view.translationX = 0.toFloat()
                }
                position <= 1 -> {
                    wallpaper.translationX = pageWidth * getFactor(position)
                    view.translationX = 8 * position
                }
                else -> {
                    wallpaper.translationX = 0.toFloat()
                    view.translationX = 0.toFloat()
                }
            }
        }

        private fun getFactor(position: Float): Float {
            return -position / 2
        }
    }

}