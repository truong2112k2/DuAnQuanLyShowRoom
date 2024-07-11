package com.example.da1_quanlyshowroom

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import data.dataDate
import kotlinx.android.synthetic.main.activity_screen_detail_date.txtShowMeetDateCreenDetailDate
import kotlinx.android.synthetic.main.activity_screen_detail_date.txtShowMeetLocationCreenDetailDate
import kotlinx.android.synthetic.main.activity_screen_detail_date.txtShowMeetTimeCreenDetailDate
import kotlinx.android.synthetic.main.activity_screen_detail_date.txtShowNameCarCreenDetailDate
import kotlinx.android.synthetic.main.activity_screen_detail_date.txtShowNameUserCreenDetailDate
import kotlinx.android.synthetic.main.activity_screen_detail_date.txtShowPriceCarCreenDetailDate
import kotlinx.android.synthetic.main.activity_screen_detail_date.txtShowUserAddressCreenDetailDate
import kotlinx.android.synthetic.main.activity_screen_detail_date.txtShowUserNumberPhoneCreenDetailDate
import viewmodel.dataModel
import java.text.NumberFormat
import java.util.Locale

class ScreenDetailDate : AppCompatActivity() {
    private lateinit var idDate : String
    private lateinit var date: dataDate
    private val viewModel: dataModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_detail_date)
       lay_du_lieu()



    }
    private fun lay_du_lieu(){
        idDate = intent.getStringExtra("idDate").toString()
        viewModel.getAdate(idDate)
        viewModel.a_date.observe(this@ScreenDetailDate, Observer { resultdate->
            date = resultdate
            hien_thi_du_lieu()
        })
    }
    @SuppressLint("SetTextI18n")
    private fun hien_thi_du_lieu() {
        txtShowNameUserCreenDetailDate.text = date.uname
        txtShowUserNumberPhoneCreenDetailDate.text = date.userNumberPhone
        txtShowUserAddressCreenDetailDate.text = date.userAddress

        txtShowMeetLocationCreenDetailDate.text = date.meetAdrress
        txtShowMeetDateCreenDetailDate.text = date.meetDate
        txtShowMeetTimeCreenDetailDate.text = date.meetTime

        txtShowNameCarCreenDetailDate.text = date.carChoice!!.nameCar
        txtShowPriceCarCreenDetailDate.text = chuyen_doi_tien_te(date.carChoice!!.carPrice!!.toDouble())
        su_ly_edittext(txtShowUserAddressCreenDetailDate) // xử lý nội dung của editext nếu nội dung quá dài
    }



    private fun su_ly_edittext(editText: TextView){
        if( editText.text.length > 10){
            editText.textSize = 12F
        }
    }

    private  fun chuyen_doi_tien_te(amount: Double): String { // hàm chuyển số thành giá trị tiền tệ
        val locale = Locale("vi", "VN")
        val currencyFormatter = NumberFormat.getCurrencyInstance(locale)

        return currencyFormatter.format(amount)
    }


}