package com.example.da1_quanlyshowroom

import Interface.setOnClickItem
import adapter.AdapterRecycleViewUser
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import data.dataCar
import kotlinx.android.synthetic.main.activity_screen_home.btnOpenNavigation
import kotlinx.android.synthetic.main.activity_screen_home.checkBoxSortCar


import kotlinx.android.synthetic.main.activity_screen_home.drawable_layout
import kotlinx.android.synthetic.main.activity_screen_home.imageButton2
import kotlinx.android.synthetic.main.activity_screen_home.nav_infomation
import kotlinx.android.synthetic.main.activity_screen_home.recycleviewShowCarForUser
import kotlinx.android.synthetic.main.activity_screen_home.searchCarforName
import viewmodel.dataModel


class ScreenHome : AppCompatActivity() {
    private val viewmodel : dataModel by viewModels()
    private var getId = "null"
    private var getEmail = "null"
    private var getDate = "null"
    private lateinit var alertDialog_updateAccount : AlertDialog
    private lateinit var listDataCar: List<dataCar>
    private lateinit var adpater: AdapterRecycleViewUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_home)
        listDataCar = ArrayList<dataCar>()

        hien_thi_gif()
        lay_id_tai_khoan {getId = it } // lấy id tài khoản người dùng gán vào biến getID
        lay_email_tai_khoan{getEmail =it} // lấy email tài khoản ng dùng gán vào biến getEmail
        lay_date_tai_khoan{getDate =it} // lấy date tài khoản ng dùng gán vào biến getDate

        khoi_tao_navigationview()
        viewmodel.resutlUpdateAccount.observe(this@ScreenHome, Observer {result-> // sử dụng viewMoel kiểm tra xem đã cập nhật tài khoản thành công chưa
            if( result == true ){
                Toast.makeText(this@ScreenHome, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
                // trở về màn hình đăng nhập khi người dùng thay mật khẩu
                tro_ve_man_hinh_log_in()

            }else{
                Toast.makeText(this@ScreenHome, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show()

            }
        })
        viewmodel.resutlDeleteAccount.observe(this@ScreenHome, Observer {result->// sử dụng viewMoel kiểm tra xem đã xóa tài khoản thành công chưa
            if( result == true ){
                // trở về màn hình đăng nhập khi người dùng xóa tài khoản
                tro_ve_man_hinh_log_in()

            }else{
                Toast.makeText(this@ScreenHome, "Xóa tài khoản thất bại", Toast.LENGTH_SHORT).show()

            }

        })
        khoi_tao_recycle_view()
        tim_kiem_san_pham()
        sap_xep_san_pham()







    }

    private fun sap_xep_san_pham() {
        checkBoxSortCar.setOnCheckedChangeListener { compoundButton, ischecked ->
            if( ischecked){
                viewmodel.getDataCar().observe(this@ScreenHome, Observer { // lấy dữ liệu từ firebase và đưa lên recycleview
                    listDataCar = it.sortedBy { it.carPrice?.toFloat() }
                    adpater = AdapterRecycleViewUser(listDataCar, object : setOnClickItem{
                        override fun setOnClick(pos: Int) {
                            chi_tiet_san_pham(listDataCar[pos])
                        }
                    })
                    recycleviewShowCarForUser.layoutManager = GridLayoutManager(this@ScreenHome, 2 )
                    recycleviewShowCarForUser.adapter = adpater
                })
            }else{
                 khoi_tao_recycle_view()
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")

    private fun tim_kiem_san_pham() {
        searchCarforName.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tim_kiem_theo_ten(newText!!)
                return true
            }
        })
    }

    private fun khoi_tao_recycle_view() {
        viewmodel.getDataCar().observe(this@ScreenHome, Observer { // lấy dữ liệu từ firebase và đưa lên recycleview
            listDataCar = it
            adpater = AdapterRecycleViewUser(listDataCar, object : setOnClickItem{
                override fun setOnClick(pos: Int) {
                     chi_tiet_san_pham(listDataCar[pos])
                }
            })
            recycleviewShowCarForUser.layoutManager = GridLayoutManager(this@ScreenHome, 2 )
            recycleviewShowCarForUser.adapter = adpater
        })

    }

    private fun chi_tiet_san_pham(dataCar: dataCar) {
        val intent = Intent(this@ScreenHome, ScreenDetailCar :: class.java)
        intent.putExtra("dataCar", dataCar)
        intent.putExtra("userIdAccount",getId.toString())

        startActivity(intent)
    }

    private fun tro_ve_man_hinh_log_in() {
        startActivity(Intent(this@ScreenHome, ScreenLogIn ::class.java))
        finish() // giết activity
    }

    private fun lay_date_tai_khoan(callback: (String) -> Unit) { // lấy ngày đăng kí account
        val getUname = intent.getStringExtra("username") // lấy uname và password từ activity trước
        val getUpass = intent.getStringExtra("password")
        viewmodel.getDate(getUname.toString(),getUpass.toString()) // lấy date từ hàm getDate trong class Firebase
        viewmodel.restltGetDateAccount.observe(this@ScreenHome, Observer {
            callback(it.toString())
        })
    }

    private fun lay_email_tai_khoan(callback: (String) -> Unit) { // lấy email account
        val getUname = intent.getStringExtra("username") // lấy uname và password từ activity trước
        val getUpass = intent.getStringExtra("password")
        viewmodel.getEmail(getUname.toString(),getUpass.toString()) // lấy gmail từ hàm getGmail trong class Firebase
        viewmodel.resultGetEmailAccount.observe(this@ScreenHome, Observer {
            callback(it.toString())
        })
    }


    private fun lay_id_tai_khoan(callback: (String)-> Unit) { // sử dụng hàm lamba để lấy id sẽ dễ hơn

        val getUname = intent.getStringExtra("username") // lấy uname và password từ activity trước
        val getUpass = intent.getStringExtra("password")
        viewmodel.getId(getUname.toString(),getUpass.toString()) // lấy id từ hàm getID trong class Firebase
        viewmodel.resultGetIdAccount.observe(this@ScreenHome, Observer {
            callback(it.toString())
        })

    }



    @SuppressLint("SetTextI18n", "RestrictedApi")
    private fun khoi_tao_navigationview() { // khởi tạo navitionview

        nav_infomation.itemIconTintList = null

        btnOpenNavigation.setOnClickListener {
            drawable_layout.openDrawer(nav_infomation)
        }
        nav_infomation.setNavigationItemSelectedListener {// xét sự kiện khi click
           when(it.itemId){
              R.id.icon_changepass ->{ cap_nhat_tai__khoan() }
               R.id.icon_date ->{chuyen_man_hinh_hien_thi_date()}
              R.id.icon_home ->{drawable_layout.closeDrawer(nav_infomation) // đóng navitionview
                   }
             R.id.icon_out ->{ tro_ve_man_hinh_log_in() // trở về màn hình đăng nhập
            }
            R.id.icon_delete_account->{xoa_tai_khoan() }
       }
       true
   }


    }

    private fun chuyen_man_hinh_hien_thi_date() {
        val i = Intent(this@ScreenHome, ScreenShowDateForUser :: class.java)
        i.putExtra("id", getId)
        startActivity(i)
    }

    private fun xoa_tai_khoan() { // xàm xóa tài khoản
        val alertDialog_deleteAccount = AlertDialog.Builder(this@ScreenHome)
            .apply {
                setTitle("Bạn có chắc muốn xóa tài khoản?")
                setMessage("Sau khi xóa, bạn sẽ tự động bị thoát ra!")
                    .setNegativeButton("[Hủy]"){ dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                    }
                    .setPositiveButton("[Ok]"){ dialogInterface: DialogInterface, i: Int ->
                        viewmodel.deleteAccount(getId) // xóa tài khoản thông qua viewmodel

                    }
            }
            .show()

    }

    @SuppressLint("InflateParams", "MissingInflatedId")
    private fun cap_nhat_tai__khoan() { // cập nhật thông tin tài khoản
        val build = AlertDialog.Builder(this@ScreenHome)
        val view = layoutInflater.inflate(R.layout.alertdialog_changepassword, null)
        build.setView(view) // khởi tạo view
        // lấy view để tương tác
        val edtInputOldPass = view.findViewById<EditText>(R.id.edtInputOldPass)
        val edtInputNewPass = view.findViewById<EditText>(R.id.edtInputNewPass)
        val edtReInputNewPass = view.findViewById<EditText>(R.id.edtReInputNewPass)
        val btnChangePass= view.findViewById<Button>(R.id.btnConfirmChangePass)
        // tương tác với view
        val getUname = intent.getStringExtra("username") // lấy uname và password từ activity trước
        val getUpass = intent.getStringExtra("password")

        btnChangePass.setOnClickListener {// khi người dùng click vào nút xác nhận thay đổi mật khẩu
            // kiểm tra xem nội dung có bị bỏ trống không
         if(kiem_tra_noi_dung(edtInputOldPass)){
             khoi_tao_snackBar(edtInputOldPass,"Nhập mật khẩu cũ !")
         }else if( kiem_tra_noi_dung(edtInputNewPass)){
             khoi_tao_snackBar(edtInputNewPass,"Nhập mật khẩu mới !")
         }else if( kiem_tra_noi_dung(edtReInputNewPass)){
             khoi_tao_snackBar(edtReInputNewPass,"Nhập lại mật khẩu !")
         }else if(kiem_tra_mat_khau(edtInputOldPass.text.toString(), getUpass.toString())){ // kiểm tra xem ng dùng có nhập đúng mật khẩu cũ không
             khoi_tao_snackBar(edtReInputNewPass,"Mật khẩu của bạn nhập vào không đúng")
         }else if( kiem_tra_2_mat_khau(edtInputNewPass, edtReInputNewPass)) { // kiểm tra xem 2 lần nhập mật khẩu mới có trùng hay không ?
             khoi_tao_snackBar(edtReInputNewPass, "Mật khẩu bạn nhập không trùng khớp")
         }else{
              viewmodel.updateAccount(getId,getUname.toString(), edtInputNewPass.text.toString(), getEmail, getDate) // gọi hàm upDateData để cập nhật dữ liệu tài khoản ng dùng
             //reset biểu mẫu và đóng alerdialog khi cập nhật xong
             edtInputOldPass.text.clear()
             edtInputNewPass.text.clear()
             edtReInputNewPass.text.clear()
             alertDialog_updateAccount.dismiss()
         }


        }





        alertDialog_updateAccount = build.create() // tạo alert
        alertDialog_updateAccount.show()// hiển thị alert
        drawable_layout.closeDrawer(nav_infomation) // đóng navitionview

    }



    @SuppressLint("ShowToast")
    private fun khoi_tao_snackBar(editText: EditText, string: String) { // kiểm tra xem mật khẩu 2 lần nhập có trùng khớp không
        val snackbar = Snackbar.make(editText, string,Snackbar.LENGTH_SHORT) // tạo snackbar thông báo
        snackbar.show()

    }
    private fun kiem_tra_2_mat_khau(editText: EditText, editText2: EditText): Boolean{ // kiểm tra xem 2 mật khẩu có trùng khớp không
        val mk1 = editText.text.toString()
        val mk2 = editText2.text.toString()
        if( mk1 != mk2){
            return true
        }
        return false

    }

    private fun kiem_tra_noi_dung(editText: EditText): Boolean { // kiểm tra xem nội dung có bị bỏ trống không
        if(editText.text.toString().isEmpty() ){
            return true
        }
        return false
    }
    private fun kiem_tra_mat_khau(oldPass:String, newPass: String): Boolean{ // kiểm tra xem mật khẩu ng dùng nhập vào có đúng không
        if(oldPass != newPass){
            return true
        }
        return false
    }

    private fun hien_thi_gif() { // sử dụng thư viện Glide để hiển thị 1 gif
        Glide.with(this@ScreenHome)
            .asGif()
            .load(R.drawable.gif_car)
            .into(imageButton2)
    }

    fun tim_kiem_theo_ten(text: String){
         val newList = ArrayList<dataCar>()
        for( i in listDataCar){
            if( i.nameCar?.lowercase()!!.contains(text.lowercase())== true){
                newList.add(i)
            }
        }
        if(newList.size == 0){
            Toast.makeText(this@ScreenHome, "Không tìm thấy sản phẩm thích hợp", Toast.LENGTH_SHORT).show()
            // thông báo khi k tìm thấy sản phẩm theo searchview
        }

        adpater.searchDataList(newList)
    }
}