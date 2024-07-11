package fragment

import adapter.AdapterRecycleViewAdmin
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.da1_quanlyshowroom.R
import com.example.da1_quanlyshowroom.ScreenAddDataCar
import com.example.da1_quanlyshowroom.ScreenDetail
import com.google.firebase.database.FirebaseDatabase
import data.dataCar
import viewmodel.dataModel
class FragmentManagerCar : Fragment(R.layout.fragment_manager_car),
    AdapterRecycleViewAdmin.ItemClickListener {
    private lateinit var view: View
    private lateinit var searchView: SearchView
    private lateinit var textAddNewCar: TextView
    private lateinit var animation: Animation
    private lateinit var recycleView: RecyclerView
    private lateinit var database: FirebaseDatabase
    private lateinit var list: ArrayList<dataCar>
    private lateinit var alertUpdate_Delete: AlertDialog
    private lateinit var viewModel: dataModel
    private var resultSale = false
    private lateinit var carAdapter: AdapterRecycleViewAdmin
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(this@FragmentManagerCar).get(dataModel::class.java)
        view = inflater.inflate(R.layout.fragment_manager_car, container, false)
        anh_xa_view()
        animation = AnimationUtils.loadAnimation(requireContext(), R.anim.animation)
        database = FirebaseDatabase.getInstance()
        list = ArrayList<dataCar>()
        xet_su_kien_view()
        return view
    }
    private fun xet_su_kien_view() {
        them_san_pham()
        hien_thi_san_pham()

    }




    @SuppressLint(
        "InflateParams", "MissingInflatedId", "UseSwitchCompatOrMaterialCode",
        "NotifyDataSetChanged"
    )
    private fun hien_thi_san_pham() {

        carAdapter = AdapterRecycleViewAdmin(emptyList(), this)
        recycleView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = carAdapter
        }
        viewModel.getDataCar().observe(viewLifecycleOwner) { listData ->
            carAdapter.list = listData
            carAdapter.notifyDataSetChanged()

        }
    }

    private fun them_san_pham() {
        val a = "Thêm sản phẩm"
        val string = SpannableString(a)
        string.setSpan(UnderlineSpan(), 0, 13, 1)
        textAddNewCar.text = string
        textAddNewCar.setOnClickListener {
            textAddNewCar.startAnimation(animation)
            chuyen_man_hinh_them_san_pham()
        }

    }


    private fun chuyen_man_hinh_them_san_pham() {
        val i = Intent(requireContext(), ScreenAddDataCar::class.java)
        startActivity(i)
//        requireActivity().finish() // hủy fragment sau khi sang màn hình mới
    }


    private fun anh_xa_view() {
        textAddNewCar = view.findViewById(R.id.txtAddNewCar)
        recycleView = view.findViewById(R.id.recycleShowDataAdmin)

    }

    @SuppressLint("UseSwitchCompatOrMaterialCode", "InflateParams")
    override fun onItemClick(dataCar: dataCar) { // xét sự kiện khi click item recycleview, mở màn hình cập nhật khi click vào item
        val i = Intent(requireContext(), ScreenDetail::class.java)
        i.putExtra("id_car", dataCar.carID)
        startActivity(i)
//        requireActivity().finish()// hủy fragment sau khi sang màn hình mới
    }



}