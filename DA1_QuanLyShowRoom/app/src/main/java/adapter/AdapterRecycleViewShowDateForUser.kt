package adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.da1_quanlyshowroom.R
import data.dataDate
import kotlinx.android.synthetic.main.item_recycle_show_date_for_user.view.txtShowDate_DateForUse
import kotlinx.android.synthetic.main.item_recycle_show_date_for_user.view.txtShowNameCarChooseDateForUse
import kotlinx.android.synthetic.main.item_recycle_show_date_for_user.view.txtShowNameCustomer_ScreenDateForUser

class AdapterRecycleViewShowDateForUser(val list: List<dataDate>): RecyclerView.Adapter<AdapterRecycleViewShowDateForUser.viewHolder>() {

    inner class viewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_show_date_for_user, parent,false)
        return viewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.itemView.apply {
            txtShowNameCustomer_ScreenDateForUser.text = "Tên người hẹn: "+ list[position].uname
            txtShowNameCarChooseDateForUse.text =  "Tên xe: "+list[position].carChoice!!.nameCar
            txtShowDate_DateForUse.text = "Ngày hẹn: "+ list[position].meetDate
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }
}