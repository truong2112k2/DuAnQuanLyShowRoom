package fragment

import adapter.AdapterRecycleViewSetDate
import adapter.AdapterRecycleViewShowAcceptDate
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.da1_quanlyshowroom.R
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import data.dataDate
import viewmodel.dataModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentManagerDate.newInstance] factory method to
 * create an instance of this fragment.
 */

/// đã hoàn thành xong sử lí true false, sử lý xét duyệt đơn hàng, chỉ c
class FragmentManagerDate : Fragment(R.layout.fragment_manager_bill) {
    private lateinit var viewModel: dataModel
    private lateinit var viewDate: View
    private lateinit var btnNewNoti: ImageButton
    private lateinit var recycle_showAcceptDate: RecyclerView
    private lateinit var animation: Animation
    private lateinit var alerdialog: AlertDialog
    private lateinit var adapterDate: AdapterRecycleViewSetDate
    private lateinit var arrayListDate: List<dataDate>


    private lateinit var adapterDateAccept: AdapterRecycleViewShowAcceptDate
    private lateinit var arrayListDateAccept: List<dataDate>
    private lateinit var  txtNumberNotification: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this@FragmentManagerDate).get(dataModel:: class.java)
        viewDate = inflater.inflate(R.layout.fragment_manager_bill,container, false )
        animation = AnimationUtils.loadAnimation(requireContext(), R.anim.animation)
        arrayListDate = ArrayList<dataDate>()
        anh_xa_view()
        kiem_tra_so_luong_thong_bao()
        kiem_tra_thong_bao()

        hien_thi_cuoc_hen()


        return viewDate
    }

    private fun kiem_tra_so_luong_thong_bao() {
        viewModel.getDataDate().observe( viewLifecycleOwner, Observer {

            txtNumberNotification.text = it.size.toString()


        })

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun hien_thi_cuoc_hen() {
    viewModel.getDateAcceptDate().observe(viewLifecycleOwner, Observer {list->
        arrayListDateAccept = list
        adapterDateAccept = AdapterRecycleViewShowAcceptDate(arrayListDateAccept)
        adapterDateAccept.notifyDataSetChanged()
        recycle_showAcceptDate.layoutManager = LinearLayoutManager(requireContext())
        recycle_showAcceptDate.adapter = adapterDateAccept
    })

    }

    @SuppressLint("InflateParams", "MissingInflatedId", "NotifyDataSetChanged")
    private fun kiem_tra_thong_bao() {
        btnNewNoti.setOnClickListener {
            btnNewNoti.startAnimation(animation)
            val build = AlertDialog.Builder(requireContext())
            val view = layoutInflater.inflate(R.layout.alerdialog_showsetdate, null )
            val recycle = view.findViewById<RecyclerView>(R.id.recycle_showDate)
            val progressbar= view.findViewById<CircularProgressBar>(R.id.progressbar_showSetDate)
            val textView = view.findViewById<TextView>(R.id.txtStatusList)

            val countDownt = object : CountDownTimer(3000, 10000){
                override fun onTick(p0: Long) {
                    val time = ((3000-p0) / 50)
                    progressbar.progress = time.toFloat()
                }

                override fun onFinish() {
                    progressbar.visibility = View.GONE
                    recycle.visibility = View.VISIBLE
                }
            }
            countDownt.start()


            viewModel.getDataDate().observe( viewLifecycleOwner, Observer {
                    arrayListDate = it
                    adapterDate = AdapterRecycleViewSetDate(arrayListDate)
                    adapterDate.notifyDataSetChanged()
                    recycle.layoutManager = LinearLayoutManager(requireContext())
                    recycle.adapter = adapterDate
            })
            build.setView(view)
            alerdialog = build.create()
            alerdialog.show()
        }

    }

    private fun anh_xa_view() {
       btnNewNoti = viewDate.findViewById(R.id.btnNotification)
        recycle_showAcceptDate = viewDate.findViewById(R.id.recycle_showAcceptDate)
        txtNumberNotification = viewDate.findViewById(R.id.txtNumberNofi)
    }
}