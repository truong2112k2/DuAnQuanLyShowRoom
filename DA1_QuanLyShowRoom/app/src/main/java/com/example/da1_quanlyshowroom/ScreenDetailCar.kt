package com.example.da1_quanlyshowroom

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import data.dataCar
import kotlinx.android.synthetic.main.activity_screen_detail_car.btnSetDate
import kotlinx.android.synthetic.main.activity_screen_detail_car.imgShowImageCarForDetail
import kotlinx.android.synthetic.main.activity_screen_detail_car.txtshowBrandCarScreenDetail
import kotlinx.android.synthetic.main.activity_screen_detail_car.txtshowCapacityCarScreenDetail
import kotlinx.android.synthetic.main.activity_screen_detail_car.txtshowColorCarScreenDetail
import kotlinx.android.synthetic.main.activity_screen_detail_car.txtshowNameCarScreenDetail
import kotlinx.android.synthetic.main.activity_screen_detail_car.txtshowSumOfSeatCarScreenDetail
import kotlinx.android.synthetic.main.activity_screen_detail_car.txtshowYearCarScreenDetail


class ScreenDetailCar : AppCompatActivity() {
   private lateinit var animation : Animation
   private lateinit var userIdAccount: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_detail_car)
        animation = AnimationUtils.loadAnimation(this@ScreenDetailCar, R.anim.animation)

        userIdAccount = intent.getStringExtra("userIdAccount").toString() // id account
        hien_thi_thong_tin_chi_tiet()
        dat_lich_xem()

    }

    private fun dat_lich_xem() {
        btnSetDate.setOnClickListener {
            val data = intent.getParcelableExtra<dataCar>("dataCar")

            btnSetDate.startAnimation(animation)
           val i = Intent(this@ScreenDetailCar, ScreenSetDate :: class.java)
            i.putExtra("idUser", userIdAccount)
            i.putExtra("dataCar2", data)
           startActivity(i)

        }
    }

    @SuppressLint("SetTextI18n")
    private fun hien_thi_thong_tin_chi_tiet() {

        val data = intent.getParcelableExtra<dataCar>("dataCar")
       txtshowNameCarScreenDetail.setText(data!!.nameCar.toString())
       txtshowYearCarScreenDetail.setText(data.birdYear.toString())
       txtshowBrandCarScreenDetail.setText(data.carBrand.toString())
       txtshowColorCarScreenDetail.setText(data.carColor.toString())
       txtshowSumOfSeatCarScreenDetail.setText(data.totalNumberOfSeats.toString())
       txtshowCapacityCarScreenDetail.setText(data.capacity.toString() +" L")
       Picasso.get().load(data.imageCar).into(imgShowImageCarForDetail)
    }
}