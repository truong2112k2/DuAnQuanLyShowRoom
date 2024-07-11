package adapter

import Interface.setOnClickItem
import android.annotation.SuppressLint
import android.opengl.Visibility
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.StrikethroughSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.da1_quanlyshowroom.R
import com.squareup.picasso.Picasso
import data.dataCar
import kotlinx.android.synthetic.main.item_recycle_admin.view.txtShowCarPrice
import kotlinx.android.synthetic.main.item_recycle_admin.view.txtshowNameCar
import kotlinx.android.synthetic.main.item_recycle_showcaruser.view.btnDetail
import kotlinx.android.synthetic.main.item_recycle_showcaruser.view.imgShowImageCarForUser
import kotlinx.android.synthetic.main.item_recycle_showcaruser.view.imgShowSale
import kotlinx.android.synthetic.main.item_recycle_showcaruser.view.txtShowColorCarForUser
import kotlinx.android.synthetic.main.item_recycle_showcaruser.view.txtShowNameCarForUser
import kotlinx.android.synthetic.main.item_recycle_showcaruser.view.txtShowPriceCarForUser
import kotlinx.android.synthetic.main.item_recycle_showcaruser.view.txtShowPriceSaleCarForUser
import java.text.NumberFormat
import java.util.Locale

class AdapterRecycleViewUser(var list: List<dataCar>, val click: setOnClickItem): RecyclerView.Adapter<AdapterRecycleViewUser.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_showcaruser,parent, false)
        return viewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.itemView.apply {
            Picasso.get().load(list[position].imageCar).into(imgShowImageCarForUser)
            txtShowNameCarForUser.text = list[position].nameCar
            txtShowPriceCarForUser.text = convertToVnd(list[position].carPrice!!.toDouble())
            txtShowColorCarForUser.text = list[position].carColor
            Glide.with(context)
                .asGif()
                .load(R.drawable.gif_sale)
                .into(imgShowSale)
            if(list[position].carSale == true ){ // nếu sản phẩm đang được sale
                val valueDiscount = calculateDiscountedPrice(list[position].carPrice!!.toDouble(), list[position].percentSale!!.toDouble())
                txtShowPriceSaleCarForUser.text = convertToVnd(valueDiscount).toString() // hiển thị giá sale
                imgShowSale.visibility = View.VISIBLE // hiểu thị gif sale
                // thực hiện thao tác gạch bỏ giá cũ
                val oriPrice =  convertToVnd(list[position].carPrice!!.toDouble())
                val spanString = SpannableString(oriPrice)
                spanString.setSpan(StrikethroughSpan(), 0, oriPrice.length, 0)
                txtShowPriceCarForUser.text = spanString

            }else{
                txtShowPriceSaleCarForUser.text = "No sale"
            }
            val ani = AnimationUtils.loadAnimation(context, R.anim.animation)
            btnDetail.setOnClickListener { // khi click button xem chi tiết
                btnDetail.startAnimation(ani)
                click.setOnClick(position)
            }

        }

    }

    override fun getItemCount(): Int {
       return list.size
    }

    inner class viewHolder(view: View): RecyclerView.ViewHolder(view)
    private  fun convertToVnd(amount: Double): String { // hàm chuyển số thành giá trị tiền tệ
        val locale = Locale("vi", "VN")
        val currencyFormatter = NumberFormat.getCurrencyInstance(locale)

        return currencyFormatter.format(amount)
    }
    fun searchDataList(newList: List<dataCar>){
        list = newList
        notifyDataSetChanged()

    }

    fun calculateDiscountedPrice(originalPrice: Double, discountPercentage: Double): Double {
        val discountAmount = originalPrice * (discountPercentage / 100)
        val discountedPrice = originalPrice - discountAmount
        return discountedPrice
    }

}