package com.example.da1_quanlyshowroom

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import data.dataCar
import kotlinx.android.synthetic.main.activity_screen_add_data_car.edtInputProductName
import kotlinx.android.synthetic.main.activity_screen_deital.btnDeleteCar
import kotlinx.android.synthetic.main.activity_screen_deital.btnUpdateCar
import kotlinx.android.synthetic.main.activity_screen_deital.edtInputPercent
import kotlinx.android.synthetic.main.activity_screen_deital.edtShowBrand
import kotlinx.android.synthetic.main.activity_screen_deital.edtShowCapacity
import kotlinx.android.synthetic.main.activity_screen_deital.edtShowColor
import kotlinx.android.synthetic.main.activity_screen_deital.edtShowDate
import kotlinx.android.synthetic.main.activity_screen_deital.edtShowName
import kotlinx.android.synthetic.main.activity_screen_deital.edtShowPrice
import kotlinx.android.synthetic.main.activity_screen_deital.edtShowSumofSeat
import kotlinx.android.synthetic.main.activity_screen_deital.switchSetSale
import viewmodel.dataModel
import java.text.NumberFormat
import java.util.Locale

class ScreenDetail : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var imageLink: String
    private var percent = 0.0
    private var sale = false
    private val viewModel: dataModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_deital)
        cap_nhat_va_xoa_san_pham()

    }
    @SuppressLint("SuspiciousIndentation")
    private fun cap_nhat_va_xoa_san_pham() {

        switchSetSale.setOnCheckedChangeListener { compoundButton,isChecked ->
            if(isChecked == true  ){
                sale = true
                edtInputPercent.visibility = View.VISIBLE


            }else{
                sale = false
                edtInputPercent.visibility = View.GONE
                edtInputPercent.setText("0.0")
            }



        }


        // lấy id sản phẩm được gửi từ fragment trước
        val id = intent.getStringExtra("id_car")
        // lấy thông tin sản phẩm thông quá id
        db = FirebaseDatabase.getInstance().getReference("DATA_CAR").child(id.toString())
            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val data = snapshot.getValue(dataCar::class.java)
                        // hiển thị thông tin sản phẩm lên view
                        edtShowName.setText(data!!.nameCar)
                        edtShowBrand.setText(data.carBrand)
                        edtShowDate.setText(data.birdYear)
                        edtShowColor.setText(data.carColor)
                        edtShowSumofSeat.setText(data.totalNumberOfSeats)
                        edtShowCapacity.setText(data.capacity)
                        edtShowPrice.setText(data.carPrice.toString())
                        edtInputPercent.setText(data.percentSale.toString())
                        imageLink = data.imageCar.toString()

                        /// xét tình trạng cho switch
                        if(data.carSale == true ){
                            switchSetSale.isChecked = true
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    showAlertDialog("Không thể kết nối đến Firebase", "Hủy")
                }
            })

   /// cập nhật sản phẩm
        btnUpdateCar.setOnClickListener {
            percent = edtInputPercent.text.toString().toDouble()
            // calsale lấy từ biến sale
                  //persent lấy từ edittext
                  // còn lại lấy từ data gốc là ok
       viewModel.updateDataCar(id.toString(),imageLink, edtShowName.text.toString(), edtShowDate.text.toString(), edtShowBrand.text.toString(), edtShowColor.text.toString(), edtShowSumofSeat.text.toString() , edtShowCapacity.text.toString(), edtShowPrice.text.toString(), sale, percent.toFloat() )
        }
        // thông báo cập nhật thành công
        viewModel.resultUpdateDataCar.observe(this@ScreenDetail, Observer {
            if( it == true ){
                tao_thong_bao("Cập nhật thành công !")

             }else{
                tao_thong_bao("Err: Cập nhật thất bại !")

            }
        })


        // xóa sản phẩm
        btnDeleteCar.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this@ScreenDetail)
            alertDialog.apply {
                setTitle("Bạn muốn xóa sản phẩm?")
                setNegativeButton("[Hủy]"){ dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                setPositiveButton("[Đồng ý]"){ dialogInterface: DialogInterface, i: Int ->
                   viewModel.deleteDataCar(id.toString())
                }
            }
            alertDialog.show()
        }
        // thông báo khi xóa sản phẩm thành công
        viewModel.resultDeleteDataCar.observe(this@ScreenDetail, Observer {
            if( it == true ){
                tao_thong_bao("Đã xóa sản phẩm !")
                quay_lai_man_hinh_truoc()
            }else{
                tao_thong_bao("Err: Chưa thể xóa sản phẩm !")
            }
        })
    }
    private fun quay_lai_man_hinh_truoc() {
        val i = Intent(this@ScreenDetail, ScreenAdMinManager ::class.java)
        startActivity(i)
        finish()
    }

    private fun showAlertDialog( content: String , notification: String ){
       val alertDialog = AlertDialog.Builder(this@ScreenDetail)
       alertDialog.apply {
           setTitle(content)
           setNegativeButton("[Hủy]"){ dialogInterface: DialogInterface, i: Int ->
                   dialogInterface.dismiss()
           }
           setPositiveButton("[Đồng ý]"){ dialogInterface: DialogInterface, i: Int ->
               Toast.makeText(this@ScreenDetail, notification, Toast.LENGTH_SHORT).show()
           }

       }
       alertDialog.show()

    }
    private fun kiem_tra_noi_dung(): Boolean {
           if(
               edtShowName.text.isEmpty() ||
               edtShowBrand.text.isEmpty() ||
               edtShowDate.text.isEmpty() ||
               edtShowColor.text.isEmpty()||
               edtShowSumofSeat.text.isEmpty() ||
               edtShowCapacity.text.isEmpty() ||
               edtShowPrice.text.isEmpty()
               ){
               return true
           }else{
               return false
           }

    }
    private  fun chuyen_don_vi_tien(amount: Float): String { // hàm chuyển đơn vị tiền tệ
        val locale = Locale("vi", "VN")
        val currencyFormatter = NumberFormat.getCurrencyInstance(locale)

        return currencyFormatter.format(amount)
    }
    private fun tao_thong_bao(string: String){
        val snackBar = Snackbar.make(edtShowPrice, string, Toast.LENGTH_SHORT).show()
    }
    private fun lay_phan_tram_giam_gia(): Float{
        return edtInputPercent.text.toString().toFloat()
    }
}