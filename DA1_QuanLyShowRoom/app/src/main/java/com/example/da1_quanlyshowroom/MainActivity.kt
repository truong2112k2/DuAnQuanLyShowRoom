package com.example.da1_quanlyshowroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.style.UnderlineSpan
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.imgGifCarMove
import kotlinx.android.synthetic.main.activity_main.progress_loading

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hien_thi_gif()
        chuyen_den_man_hinh_chinh()



    }

    private fun hien_thi_gif() {
        Glide.with(this@MainActivity)
            .asGif()
            .load(R.drawable.carmove3)
            .into(imgGifCarMove)
    }


    private fun chuyen_den_man_hinh_chinh() {
        val countdown = object : CountDownTimer(5000, 1000){
            override fun onTick(p0: Long) {
                val progress = ((5000-p0)/50).toFloat()
                progress_loading.progress = progress
            }
            override fun onFinish() {
                val i = Intent(this@MainActivity, ScreenLogIn :: class.java)
                startActivity(i)
                finish()
            }
        }
        countdown.start()

    }
}