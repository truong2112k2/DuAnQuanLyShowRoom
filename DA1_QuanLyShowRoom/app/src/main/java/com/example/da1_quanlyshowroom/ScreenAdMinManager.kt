package com.example.da1_quanlyshowroom

import adapter.AdapterViewPager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_screen_ad_min_manager.tabLayout
import kotlinx.android.synthetic.main.activity_screen_ad_min_manager.viewPager2

class ScreenAdMinManager : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_ad_min_manager)
        khoi_tao_viewPager()
    }

    private fun khoi_tao_viewPager() {
        val adapterViewPager = AdapterViewPager(supportFragmentManager , lifecycle)
        viewPager2.adapter = adapterViewPager
        TabLayoutMediator(tabLayout, viewPager2){ tab, pos ->
            when(pos){
                0 -> {tab.text ="Mặt Hàng"}
                1 -> {tab.text ="Khuyến mãi"}
                2 -> {tab.text ="Tài khoản"}
                3 -> {tab.text ="Lịch hẹn"}
            }
        }.attach()
    }




}