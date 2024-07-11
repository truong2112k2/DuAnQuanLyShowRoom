package com.example.da1_quanlyshowroom

import adapter.AdapterRecycleViewShowDateForUser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import data.dataDate
import kotlinx.android.synthetic.main.activity_screen_show_date_for_user.recycle_showDateforUser
import viewmodel.dataModel

class ScreenShowDateForUser : AppCompatActivity() {
    private var id = "nullDate "
    private val viewModel: dataModel by viewModels()
    private lateinit var list : ArrayList<dataDate>
    private lateinit var adatper: AdapterRecycleViewShowDateForUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_show_date_for_user)
           list = ArrayList<dataDate>()
          id = intent.getStringExtra("id").toString()
          hien_thi_thong_tin()


    }

    private fun hien_thi_thong_tin() {
        viewModel.getDataDatefromAccount(id).observe( this@ScreenShowDateForUser, Observer {
            list = it
            adatper = AdapterRecycleViewShowDateForUser(list)
            recycle_showDateforUser.adapter = adatper
            recycle_showDateforUser.layoutManager = LinearLayoutManager(this@ScreenShowDateForUser)
        })


    }
}