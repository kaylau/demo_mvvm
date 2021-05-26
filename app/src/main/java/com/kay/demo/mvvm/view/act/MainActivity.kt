package com.kay.demo.mvvm.view.act

import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kay.demo.R
import com.kay.demo.base.BaseAct
import com.kay.demo.databinding.ActivityMainBinding
import com.kay.demo.mvvm.view.frg.HomeFrg
import com.kay.demo.mvvm.view.frg.MineFrg
import com.kay.demo.mvvm.view.frg.RecommendFrg
import com.kay.demo.mvvm.vm.MainVm
import com.kay.demo.utils.ActManager
import com.kay.demo.utils.SystemUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseAct<MainVm, ActivityMainBinding>() {

    private var lastIndex = 0
    private var mFragments = mutableListOf<Fragment>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun bindVM() {
        dataBinding.vm = viewModel
    }

    override fun initData() {
        initBottomNavigation()

        mFragments.add(HomeFrg())
        mFragments.add(RecommendFrg())
        mFragments.add(MineFrg())
        // 初始化展示MessageFragment
        setFragmentPosition(0)

    }

    private fun initBottomNavigation() {
        bottomNavigationView.itemIconTintList = null
        initToDefaultIcon()
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            resetToDefaultIcon() //重置到默认不选中图片
            when (item.itemId) {
                R.id.menu_home -> {
                    item.setIcon(R.drawable.ic_home_selected)
                    setFragmentPosition(0)
                }
                R.id.menu_recommend -> {
                    item.setIcon(R.drawable.ic_recommend_selected)
                    setFragmentPosition(1)
                }
                R.id.menu_mine -> {
                    item.setIcon(R.drawable.ic_mine_selected)
                    setFragmentPosition(2)
                }
            }
            true
        })
    }

    private fun resetToDefaultIcon() {
        val menuHome = bottomNavigationView.menu.findItem(R.id.menu_home)
        val menuRecommend = bottomNavigationView.menu.findItem(R.id.menu_recommend)
        val menuMine = bottomNavigationView.menu.findItem(R.id.menu_mine)
        menuHome.setIcon(R.drawable.ic_home_unselected)
        menuRecommend.setIcon(R.drawable.ic_recommend_unselected)
        menuMine.setIcon(R.drawable.ic_mine_unselected)
    }

    private fun initToDefaultIcon() {
        val menuHome = bottomNavigationView.menu.findItem(R.id.menu_home)
        val menuRecommend = bottomNavigationView.menu.findItem(R.id.menu_recommend)
        val menuMine = bottomNavigationView.menu.findItem(R.id.menu_mine)
        menuHome.setIcon(R.drawable.ic_home_selected)
        menuRecommend.setIcon(R.drawable.ic_recommend_unselected)
        menuMine.setIcon(R.drawable.ic_mine_unselected)
        SystemUtils.displayItemNum(bottomNavigationView, 1, 6)
    }

    private fun setFragmentPosition(position: Int) {

        val ft = supportFragmentManager.beginTransaction()
        val currentFragment = mFragments[position]
        val lastFragment = mFragments[lastIndex]
        lastIndex = position
        ft.hide(lastFragment)
        if (!currentFragment.isAdded) {
            supportFragmentManager.beginTransaction().remove(currentFragment).commit()
            ft.add(R.id.ll_frameLayout, currentFragment)
        }
        ft.show(currentFragment)
        ft.commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        ActManager.onExit()
    }

}