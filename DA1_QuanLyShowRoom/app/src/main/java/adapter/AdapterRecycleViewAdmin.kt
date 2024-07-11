package adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.da1_quanlyshowroom.R
import com.squareup.picasso.Picasso
import data.dataCar
import kotlinx.android.synthetic.main.item_recycle_admin.view.imgShowImageCar
import kotlinx.android.synthetic.main.item_recycle_admin.view.txtShowCarPrice
import kotlinx.android.synthetic.main.item_recycle_admin.view.txtshowNameCar
import java.text.NumberFormat
import java.util.Locale

class AdapterRecycleViewAdmin(var list: List<dataCar>, private var itemClickListener: ItemClickListener): RecyclerView.Adapter<AdapterRecycleViewAdmin.viewHolder>() {

    inner class viewHolder(view: View): RecyclerView.ViewHolder(view){
        private val nameCar: TextView = view.txtshowNameCar
        private val priceCar: TextView = view.txtShowCarPrice
        private val image : ImageView = view.imgShowImageCar
        fun  bind(car : dataCar){
            nameCar.text = car.nameCar
            priceCar.text = convertToVnd(car.carPrice!!.toDouble())

            Picasso.get().load(car.imageCar).into(image)
            itemView.setOnClickListener {
                itemClickListener.onItemClick(car)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_admin,parent,false)
        return viewHolder(layout)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val car = list[position]
        holder.bind(car)


    }

    override fun getItemCount() = list.size


    interface ItemClickListener {
        fun onItemClick(dataCar: dataCar)
    }

    // Các phương thức khác của adapter

    // Setter cho itemClickListener
    fun setItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

 // hàm update thông tin
    @SuppressLint("NotifyDataSetChanged")
    fun updateCars(updatedUsers: List<dataCar>) {
        list = updatedUsers
        notifyDataSetChanged()
    }
   @SuppressLint("NotifyDataSetChanged")
   fun searchDataList(searchList: List<dataCar>){
       list = searchList
       notifyDataSetChanged()
   }

    fun getAllData(): List<dataCar> {
        return list
    }

    private  fun convertToVnd(amount: Double): String { // hàm chuyển số thành giá trị tiền tệ
        val locale = Locale("vi", "VN")
        val currencyFormatter = NumberFormat.getCurrencyInstance(locale)

        return currencyFormatter.format(amount)
    }

}




