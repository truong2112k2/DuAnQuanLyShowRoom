package adapter

import Interface.setOnClickItem
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.da1_quanlyshowroom.R
import com.squareup.picasso.Picasso
import data.dataCar
import kotlinx.android.synthetic.main.item_recycle_admin.view.txtShowCarPrice
import kotlinx.android.synthetic.main.item_recycle_admin.view.txtshowNameCar
import kotlinx.android.synthetic.main.item_recycleview_salecar.view.imgSaleCar
import kotlinx.android.synthetic.main.item_recycleview_salecar.view.txtShowNameCarSale
import kotlinx.android.synthetic.main.item_recycleview_salecar.view.txtShowNumberDisscount
import kotlinx.android.synthetic.main.item_recycleview_salecar.view.txtShowOriginalPrice
import kotlinx.android.synthetic.main.item_recycleview_salecar.view.txtShowPriceSale
import java.text.NumberFormat
import java.util.Locale

class AdapterSaleCar(var list: List<dataCar>): RecyclerView.Adapter<AdapterSaleCar.view>() {
    inner class view(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): view {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_recycleview_salecar, parent, false)
        return view(layout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: view, position: Int) {
        holder.itemView.apply {
            // hiển thị hình ảnh
            Picasso.get().load(list[position].imageCar).into(imgSaleCar)
            txtShowNameCarSale.text = "Tên : "+ list[position].nameCar
            txtShowOriginalPrice.text ="Giá gốc : " + convertToVnd(list[position].carPrice!!.toDouble())
            txtShowNumberDisscount.text = "Giảm giá : "+ list[position].percentSale.toString() +" %"
            val salePrice = calculateDiscountedPrice(list[position].carPrice!!.toDouble() , list[position].percentSale!!.toDouble())
            txtShowPriceSale.text = "Giá hiện tại : "+ convertToVnd(salePrice)
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }
    fun calculateDiscountedPrice(originalPrice: Double, discountPercentage: Double): Double {
        val discountAmount = originalPrice * (discountPercentage / 100)
        val discountedPrice = originalPrice - discountAmount
        return discountedPrice
    }

    private  fun convertToVnd(amount: Double): String { // hàm chuyển số thành giá trị tiền tệ
        val locale = Locale("vi", "VN")
        val currencyFormatter = NumberFormat.getCurrencyInstance(locale)

        return currencyFormatter.format(amount)
    }

}