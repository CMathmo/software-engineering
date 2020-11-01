package com.wad.tBook.statistical.datepipeline

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wad.tBook.R
import com.wad.tBook.analysis.DateUtil
import com.wad.tBook.pipeline.PipelineAdapter
import com.wad.tBook.pipeline.PipelineFragment
import com.wad.tBook.room.Accounting
import kotlinx.android.synthetic.main.fragment_date_pipeline.*
import kotlinx.android.synthetic.main.fragment_pipeline.pipeline_recycle


/**
 * A simple [Fragment] subclass.
 * Use the [DatePipelineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@RequiresApi(Build.VERSION_CODES.O)
class DatePipelineFragment(type:String) : Fragment() {

    private val TAG = DatePipelineFragment::class.qualifiedName
    private val viewModel by lazy { ViewModelProvider(this).get(DatePipelineViewModel::class.java) }
    private val type = type
    private var date = DateUtil.getLocalTimeNow_00()
    private var startDate :String = ""
    private var endDate :String = ""

    private val application : Application by lazy {
        activity?.application!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val View = inflater.inflate(R.layout.fragment_date_pipeline,container,false)
        return View
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pipeline_recycle.layoutManager = LinearLayoutManager(context)
        val pipeline_adapter = DatePipelineAdapter(application)
        pipeline_recycle.adapter = pipeline_adapter
        setDate()
        setText()
        viewModel.setDate(startDate,endDate)
        viewModel.readAccountingData.observe(requireActivity(), Observer {
                accountingList: List<Accounting> ->
            Log.d(TAG,"momo")
            pipeline_adapter.readData(accountingList)
        })
        back_time_button.setOnClickListener {
            date = when(type){
                "日" -> {
                    DateUtil.OneDayEarlier_00(date)
                }"周" -> {
                    DateUtil.OneWeekEarlier_00(date)[0]
                }"月" -> {
                    DateUtil.OneMonthEarlier_00(date)[0]
                }"年" -> {
                    DateUtil.OneYearEarlier_00(date)[0]
                }else -> ""
            }
            setDate()
            viewModel.setDate(startDate,endDate)
            viewModel.readAccountingData.observe(requireActivity(), Observer {
                    accountingList: List<Accounting> ->
                Log.d(TAG,"momo")
                pipeline_adapter.readData(accountingList)
            })
            setText()
        }
        forth_time_button.setOnClickListener {
            date = when(type){
                "日" -> {
                    DateUtil.OneDayAfter_00(date)
                }"周" -> {
                    DateUtil.OneWeekAfter_00(date)[0]
                }"月" -> {
                    DateUtil.OneMonthAfter_00(date)[0]
                }"年" -> {
                    DateUtil.OneYearAfter_00(date)[0]
                }else -> ""
            }
            setDate()
            viewModel.setDate(startDate,endDate)
            viewModel.readAccountingData.observe(requireActivity(), Observer {
                    accountingList: List<Accounting> ->
                Log.d(TAG,"momo")
                pipeline_adapter.readData(accountingList)
            })
            setText()
        }
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            PipelineFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    fun setDate(){
        when(type){
            "日" -> {
                startDate = date
                endDate = date
            }"周" -> {
                val dateList = DateUtil.getWeekRange_00(date)
                startDate = dateList[0]
                endDate = dateList[1]
            }"月" -> {
                val dateList = DateUtil.getMonthRange_00(date)
                startDate = dateList[0]
                endDate = dateList[1]
            }"年" -> {
                val dateList = DateUtil.getYearRange_00(date)
                startDate = dateList[0]
                endDate = dateList[1]
            }else -> null
        }
    }

    fun setText(){
        date_period_text.setText(
            when(type){
            "日" -> date
            else -> startDate + "~" + endDate
        })
    }
}