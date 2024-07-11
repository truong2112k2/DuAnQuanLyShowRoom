package com.example.da1_quanlyshowroom

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import data.dataCar
import data.dataDate
import kotlinx.android.synthetic.main.activity_screen_set_date.btnComFirmSetDate
import kotlinx.android.synthetic.main.activity_screen_set_date.btnPickDateScreenSetDate
import kotlinx.android.synthetic.main.activity_screen_set_date.btnPickTimeScreenSetDate
import kotlinx.android.synthetic.main.activity_screen_set_date.edtInputLocationSetDate
import kotlinx.android.synthetic.main.activity_screen_set_date.edtInputNumberPhoneSetDate
import kotlinx.android.synthetic.main.activity_screen_set_date.edtInputUserNameSetDate
import kotlinx.android.synthetic.main.activity_screen_set_date.spnChoiceLocation
import kotlinx.android.synthetic.main.activity_screen_set_date.txtShowDate
import kotlinx.android.synthetic.main.activity_screen_set_date.txtShowTime
import viewmodel.dataModel
import java.util.Calendar

class ScreenSetDate : AppCompatActivity() {
    private lateinit var animation: Animation
    private lateinit var userId: String
    private lateinit var dataDate: dataDate
    private lateinit var dataCar: dataCar
    private val viewModel : dataModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_set_date)
        animation = AnimationUtils.loadAnimation(this@ScreenSetDate, R.anim.animation)
        xet_noi_dung_spinner()
        xet_chon_ngay_va_gio()
        them_lich_hen()
        userId = intent.getStringExtra("idUser").toString() // id account user
        dataCar = intent.getParcelableExtra<dataCar>("dataCar2")!!// data car

    }

    private fun them_lich_hen() {
     btnComFirmSetDate.setOnClickListener {
         btnPickDateScreenSetDate.startAnimation(animation)
         if(kiem_tra_noi_dung(edtInputUserNameSetDate.text.toString())){
            khoi_tao_snackbar(edtInputUserNameSetDate,"Nhập vào tên !")
         }else if( kiemtraten(edtInputUserNameSetDate.text.toString())){
             khoi_tao_snackbar(edtInputUserNameSetDate,"Tên không được chứa số !")

         }
         else if( kiem_tra_noi_dung(edtInputLocationSetDate.text.toString())){
            khoi_tao_snackbar(edtInputUserNameSetDate,"Nhập địa chỉ !")
         }else if(kiem_tra_noi_dung(edtInputNumberPhoneSetDate.text.toString())){
            khoi_tao_snackbar(edtInputUserNameSetDate,"Nhập số điện thoại !")
         }else if(kiemtrasdt(edtInputNumberPhoneSetDate.text.toString())){
             khoi_tao_snackbar(edtInputUserNameSetDate,"Số điện thoại không hợp lệ !")

         }
         else if(spnChoiceLocation.text.toString().equals("Chọn địa điểm") ){
            khoi_tao_snackbar(edtInputUserNameSetDate,"Chọn địa điểm !")
         }else if( txtShowDate.text.toString().equals("Chọn ngày") ){
            khoi_tao_snackbar(edtInputUserNameSetDate,"Chọn ngày !")
         }else if( txtShowTime.text.toString().equals("Chọn giờ")){
           khoi_tao_snackbar(edtInputUserNameSetDate,"Chọn giờ !")
         }else{
              viewModel.addDate(
                  edtInputUserNameSetDate.text.toString(),
                  edtInputLocationSetDate.text.toString(),
                  edtInputNumberPhoneSetDate.text.toString(),
                  spnChoiceLocation.text.toString(),
                  txtShowDate.text.toString(),
                  txtShowTime.text.toString(),
                  dataCar,
                 userId)
         }

     }

        viewModel.resultAddDate.observe(this@ScreenSetDate, Observer {result->
            if(result == true ){

                val alertDialog = AlertDialog.Builder(this@ScreenSetDate)
                alertDialog.apply {
                   setTitle("Đặt lịch thành công")
                    setPositiveButton("[Ok]"){ dialogInterface: DialogInterface, i: Int ->
                       dialogInterface.dismiss()
                       reset_bieumau()
                    }
                }
                  .show()

            }else{
                val alertDialog = AlertDialog.Builder(this@ScreenSetDate)
                alertDialog.apply {
                   setTitle("Đặt lịch thất bại")
                    setPositiveButton("[Ok]"){ dialogInterface: DialogInterface, i: Int ->
                       dialogInterface.dismiss()
                    }
                }
                   .show()

                      
            }
        })


    }
    /// kiểm tra định dạng số điện thoại
   private fun kiemtrasdt(phoneNumber: String): Boolean {
       if(phoneNumber.length <= 0 || phoneNumber.length > 10){
           return true
       }
        return false
    }
    private fun kiemtraten(name: String): Boolean{
        var so =0 ;
        val list = name.toList()
        for( i in list){
            if( i.isDigit()){
                so++
            }
        }
        if( so > 0){
            return true
        }
        return false
    }
    @SuppressLint("SetTextI18n")
    private fun reset_bieumau() {
        edtInputUserNameSetDate.text.clear()
        edtInputLocationSetDate.text.clear()
        edtInputNumberPhoneSetDate.text.clear()
        spnChoiceLocation.setText("Chọn địa điểm")
        txtShowDate.setText("Chọn ngày")
        xet_noi_dung_spinner()
        txtShowTime.setText("Chọn giờ")
    }

    @SuppressLint("SetTextI18n")
    private fun xet_chon_ngay_va_gio() {
        btnPickDateScreenSetDate.setOnClickListener {
            btnPickDateScreenSetDate.startAnimation(animation)
            val time1 = Calendar.getInstance()
            time1.add(Calendar.MONTH, -1)
            val timeStart = time1.timeInMillis
            val time2 = Calendar.getInstance()
            time2.add(Calendar.YEAR, 20)
            val timeEnd = time2.timeInMillis
            val build1 = CalendarConstraints.Builder()
            build1.setStart(timeStart)
            build1.setEnd(timeEnd)
            val build2 = build1.build()
            val build3 = MaterialDatePicker.Builder.datePicker()
            build3.setTitleText("Chọn ngày")
            build3.setCalendarConstraints(build2)
            val datePicker = build3.build()
            datePicker.show(supportFragmentManager, "datePicker")
            datePicker.addOnPositiveButtonClickListener {
                val timeSelected = it as Long
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = timeSelected
                val date = calendar.get(Calendar.DAY_OF_MONTH)
                val month = calendar.get(Calendar.MONTH) + 1
                val year = calendar.get(Calendar.YEAR)
                txtShowDate.setText("${date} / ${month} / ${year}")
            }
        }
        btnPickTimeScreenSetDate.setOnClickListener {
            btnPickTimeScreenSetDate.startAnimation(animation)
            val calendar = Calendar.getInstance()
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setTitleText("Chọn thời gian")
                .setHour(calendar.get(Calendar.HOUR))
                .setMinute(calendar.get(Calendar.MINUTE))
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val time_hour = timePicker.hour
                val time_minute = timePicker.minute
                var selectedPeriod = ""
                val hourSelected = timePicker.hour
                if( hourSelected <= 12  ) selectedPeriod = "AM" else selectedPeriod ="PM"
                txtShowTime.setText("${time_hour} : ${time_minute} ${selectedPeriod}")
            }
            timePicker.show(supportFragmentManager,"time")
        }
    }

    private fun xet_noi_dung_spinner() {
        val tinhthanhList = resources.getStringArray(R.array.vietnam)
        val adapter = ArrayAdapter(this@ScreenSetDate, android.R.layout.simple_list_item_1, tinhthanhList)
        spnChoiceLocation.setAdapter(adapter)
    }
    private fun kiem_tra_noi_dung(description: String): Boolean{
        if( description.length ==0){
            return true
        }
        return false
    }
    private fun khoi_tao_snackbar(editText: EditText,string: String){
        val snackbar = Snackbar.make(editText,string,Toast.LENGTH_SHORT)
        snackbar.show()

    }
}