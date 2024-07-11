package adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog.Builder
import androidx.recyclerview.widget.RecyclerView
import com.example.da1_quanlyshowroom.R
import com.example.da1_quanlyshowroom.ScreenDetailDate
import com.google.firebase.database.FirebaseDatabase
import data.dataDate
import kotlinx.android.synthetic.main.item_recycleview_showdate.view.btnAcceptDate
import kotlinx.android.synthetic.main.item_recycleview_showdate.view.btnCancelDateForAdmin
import kotlinx.android.synthetic.main.item_recycleview_showdate.view.btnDetail
import kotlinx.android.synthetic.main.item_recycleview_showdate.view.txtShowNameCarChoose
import kotlinx.android.synthetic.main.item_recycleview_showdate.view.txtShowNameCustomer

class AdapterRecycleViewSetDate(val list: List<dataDate>): RecyclerView.Adapter<AdapterRecycleViewSetDate.viewholder>() {
    inner class viewholder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycleview_showdate,parent,false)
        return viewholder(view)
    }

    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.itemView.apply {
            txtShowNameCustomer.text = list[position].uname
            txtShowNameCarChoose.text = list[position].carChoice!!.nameCar
            btnDetail.setOnClickListener {
                val intent = Intent(context, ScreenDetailDate::class.java)
                intent.putExtra("idDate", list[position].idDate.toString())
                context.startActivity(intent) // chuyển màn hình
            }
           btnAcceptDate.setOnClickListener {
               val alertDialog = Builder(context)
               alertDialog.apply {
                   setTitle("Xác nhận phê duyệt cuộc hẹn này ?")
                   setPositiveButton("[Xác nhận]"){ dialogInterface: DialogInterface, i: Int -> // khi click nút xác nhận
                       val dbDate = FirebaseDatabase.getInstance().getReference("DATA_DATE").child(list[position].idDate.toString())
                       val idDate = list[position].idDate
                       val uName = list[position].uname
                       val uAddress = list[position].userAddress
                       val uPhone = list[position].userNumberPhone
                       val meeAddress = list[position].meetAdrress
                       val meetDate = list[position].meetDate
                       val meetTime = list[position].meetTime
                       val carChoice = list[position].carChoice
                       val userAccountId = list[position].userIDaccount
                       val cofirmDate = true // t sẽ thay đổi giá trị biến cofirm và cập nhật nó và date
                       val dateUpdate = dataDate(idDate, uName, uAddress,uPhone,meeAddress,meetDate,meetTime,carChoice,userAccountId,cofirmDate)
                       dbDate.setValue(dateUpdate)
                           .addOnFailureListener {
                                   Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show()
                               }
                               .addOnFailureListener {
                                   Toast.makeText(context, "Chưa ok", Toast.LENGTH_SHORT).show()
                               }

                       // sau đó t sẽ thêm date vào account đã đặt lịch hẹn
                       val dbAccount = FirebaseDatabase.getInstance().getReference("USER").child(list[position].userIDaccount.toString()).child("DATE")
                       val idNodeDate = dbAccount.push().key
                           dbAccount.child(idNodeDate.toString()).setValue(dateUpdate)


                       // tiếp theo t sẽ đẩy dataDate date được xác nhận sang node Accept_date và xóa nó khỏi node DATA_DATE

                       // thêm node được xác nhận vào node Accept_Date
                       val dbAccept = FirebaseDatabase.getInstance().getReference("ACCEPT_DATE")
                       val idAccept = dbAccept.push().key
                       dbAccept.child(idAccept.toString()).setValue(dateUpdate)
                       // xóa node được xác nhận khỏi DATA_DATE
                       val database = FirebaseDatabase.getInstance()
                       val reference = database.reference.child("DATA_DATE").child(list[position].idDate.toString())
                       reference.removeValue()



                   }
                   setNegativeButton("[Hủy]"){ dialogInterface: DialogInterface, i: Int ->
                          dialogInterface.dismiss()
                   }
               }
                   .show()
           }

            btnCancelDateForAdmin.setOnClickListener {
               val alerdialog = AlertDialog.Builder(context)
                alerdialog.apply {
                    setTitle("Bạn muốn hủy cuộc hẹn này ?")
                        .setPositiveButton("[Xác nhận]"){ dialogInterface: DialogInterface, i: Int ->
                            // thực hiện xóa lịch hẹn
                            val dbDelete = FirebaseDatabase.getInstance().getReference("DATA_DATE").child(list[position].idDate.toString())
                              dbDelete.removeValue()

                        }
                        .setNegativeButton("[Hủy]"){ dialogInterface: DialogInterface, i: Int ->
                                  dialogInterface.dismiss()
                        }
                }
                    .show()
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}