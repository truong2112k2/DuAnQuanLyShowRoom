package com.example.da1_quanlyshowroom

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import fragment.FragmentManagerCar
import kotlinx.android.synthetic.main.activity_screen_add_data_car.btnAddImageCar
import kotlinx.android.synthetic.main.activity_screen_add_data_car.btnConfirmAddData
import kotlinx.android.synthetic.main.activity_screen_add_data_car.btnPickDateScreenAddProduct
import kotlinx.android.synthetic.main.activity_screen_add_data_car.btnResetContent
import kotlinx.android.synthetic.main.activity_screen_add_data_car.edtInputProductBrand
import kotlinx.android.synthetic.main.activity_screen_add_data_car.edtInputProductCapacity
import kotlinx.android.synthetic.main.activity_screen_add_data_car.edtInputProductColor
import kotlinx.android.synthetic.main.activity_screen_add_data_car.edtInputProductName
import kotlinx.android.synthetic.main.activity_screen_add_data_car.edtInputProductPrice
import kotlinx.android.synthetic.main.activity_screen_add_data_car.edtInputSumOfSeats
import kotlinx.android.synthetic.main.activity_screen_add_data_car.edtShowDateScreenAddProduct
import kotlinx.android.synthetic.main.activity_screen_add_data_car.progres_loadingImage

import viewmodel.dataModel
import java.util.Calendar


class ScreenAddDataCar : AppCompatActivity() {

    private lateinit var animation: Animation
    private val viewModel: dataModel by viewModels()
    private lateinit var db: StorageReference
    private  var linkImageFirebase: Uri? = null





    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_add_data_car)
        animation = AnimationUtils.loadAnimation(this@ScreenAddDataCar, R.anim.animation)
        db = FirebaseStorage.getInstance().reference.child("avt")


        val fragmentA = supportFragmentManager.findFragmentByTag("FragmentA") as? FragmentManagerCar
        khoi_tao_chon_ngay()
        thuc_hien_chon_anh()
        nhap_lai()
        thuc_hien_them_san_pham()






    }

    private fun nhap_lai() {
        btnResetContent.setOnClickListener {
            reset_bieumau()
        }
    }

    private fun khoi_tao_chon_ngay() { // tạo datepicker
        btnPickDateScreenAddProduct.setOnClickListener {
            btnPickDateScreenAddProduct.startAnimation(animation)
            val timeStart = Calendar.getInstance()
            timeStart.add(Calendar.MONTH, -2000)
            val getTimeStart = timeStart.timeInMillis
            val timeEnd = Calendar.getInstance()
            timeEnd.add(Calendar.YEAR, 51 )
            val getTimeEnd = timeEnd.timeInMillis

            val build1 = CalendarConstraints.Builder()
            build1.setStart(getTimeStart)
            build1.setEnd(getTimeEnd)

            val build2 = build1.build()
            val build3 = MaterialDatePicker.Builder.datePicker()
            build3.setTitleText("Chọn ngày")
            build3.setCalendarConstraints(build2)

            val datePicker = build3.build()
            datePicker.show(supportFragmentManager,"datePicker")

            datePicker.addOnPositiveButtonClickListener {
                val timeSelected = it as Long
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = timeSelected

                val date = calendar.get(Calendar.DAY_OF_MONTH)
                val month = calendar.get(Calendar.MONTH) + 1
                val year = calendar.get(Calendar.YEAR)
                val stringDate = "${date}/${month}/${year}"
                edtShowDateScreenAddProduct.setText(stringDate)


            }

        }
    }

    private fun thuc_hien_them_san_pham() {
        btnConfirmAddData.setOnClickListener {

            if (kiem_tra_noi_dung()) {
                tao_thong_bao("Điền đầy đủ thông tin!")
            }else if(edtShowDateScreenAddProduct.text.toString() == "Ngày sản xuất"){
                tao_thong_bao("Điền ngày sản xuất")
            }

            else if(linkImageFirebase == null ){
                tao_thong_bao("Thêm ảnh cho sản phẩm!")
            }else {
                viewModel.pushDataCar(
                    linkImageFirebase.toString(),
                    edtInputProductName.text.toString(),
                    edtShowDateScreenAddProduct.text.toString(),
                    edtInputProductBrand.text.toString(),
                    edtInputProductColor.text.toString(),
                    edtInputSumOfSeats.text.toString(),
                    edtInputProductCapacity.text.toString(),
                    edtInputProductPrice.text.toString())
            }
        }
        viewModel.resultAddDataCar.observe(this@ScreenAddDataCar, Observer { result->
            if( result == true ){
                tao_thong_bao("Thêm sản phẩm thành công !")
                reset_bieumau()
            }else{
                tao_thong_bao("Thêm sản phẩm thất bại !")

            }
        })
    }








    @SuppressLint("SetTextI18n")
    private fun reset_bieumau() {
        edtInputProductName.text.clear()
        edtInputProductBrand.text.clear()
        edtInputProductColor.text.clear()
        edtInputSumOfSeats.text.clear()
        edtInputProductCapacity.text.clear()
        edtInputProductPrice.text.clear()
        edtShowDateScreenAddProduct.setText("Chọn ngày")
        Picasso.get().load(R.drawable.icon_pickimage).into(btnAddImageCar)
        linkImageFirebase = null
    }


    private fun thuc_hien_chon_anh(){
        btnAddImageCar.setOnClickListener {
            btnAddImageCar.startAnimation(animation)
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null ){
           data.data?.let { imageUri->
             viewModel.pushImageInFireBase(imageUri)

           }
        }

        viewModel.resultImageLink.observe(this@ScreenAddDataCar, Observer {
            Toast.makeText(this, "Chọn ảnh thành công", Toast.LENGTH_SHORT).show()
            linkImageFirebase = it // gán link ảnh vào biến imageLinkAnh
            // đã lấy xong link ảnh, tối nay thực hiện thêm ảnh là ok
            Picasso.get().load(it).into(btnAddImageCar)


            progres_loadingImage.visibility = View.VISIBLE
            btnAddImageCar.visibility = View.GONE

            val thoi_gian_tai = object : CountDownTimer(1000, 5000){
                override fun onTick(p0: Long) {
                    val tien_trinh = ((5000-p0)/50).toInt()
                    progres_loadingImage.progress = tien_trinh
                }

                override fun onFinish() {
                    progres_loadingImage.visibility = View.GONE
                    btnAddImageCar.visibility = View.VISIBLE

                }
            }
            thoi_gian_tai.start()

        })

    }







    private fun kiem_tra_noi_dung(): Boolean { // kiểm tra xem editext có trống không
      if( edtInputProductName.text.isEmpty() ||
          edtInputProductBrand.text.isEmpty() ||
          edtInputProductColor.text.isEmpty() ||
          edtInputSumOfSeats.text.isEmpty() ||
          edtInputProductCapacity.text.isEmpty() ||
          edtInputProductPrice.text.isEmpty()
          ){
          return true
      }
        return false
    }

    private fun tao_thong_bao(string: String){
        val snackBar = Snackbar.make(edtInputProductName, string, Toast.LENGTH_SHORT).show()
    }
}