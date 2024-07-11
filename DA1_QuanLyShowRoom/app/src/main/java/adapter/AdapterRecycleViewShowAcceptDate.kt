package adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.da1_quanlyshowroom.R
import data.dataDate
import kotlinx.android.synthetic.main.item_recycle_accept_date.view.txtShowDate_DateAccept

import kotlinx.android.synthetic.main.item_recycle_accept_date.view.txtShowNameCarChooseDateAccept
import kotlinx.android.synthetic.main.item_recycle_accept_date.view.txtShowNameCustomerDateAccept
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AdapterRecycleViewShowAcceptDate( val list: List<dataDate> ) : RecyclerView.Adapter<AdapterRecycleViewShowAcceptDate.viewHolder>() {
    inner class viewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view2 = LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_accept_date,parent, false)
        return viewHolder(view2)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       holder.itemView.apply {
           txtShowNameCustomerDateAccept.text = list[position].uname
           txtShowNameCarChooseDateAccept.text = list[position].carChoice!!.nameCar
          txtShowDate_DateAccept.text = list[position].meetDate
       }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}