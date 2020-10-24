package com.wad.tBook.Pipeline

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import kotlinx.android.synthetic.main.fragment_pipeline.*

class PipelineFragment : Fragment(){

    private val viewModel by lazy { ViewModelProvider(this).get(PipelineViewModel::class.java) }
    private val application : Application by lazy {
        activity?.application!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val View = inflater.inflate(R.layout.fragment_pipeline,container,false)
        return View
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pipeline_recycle.layoutManager = LinearLayoutManager(context)
        val pipeline_adapter =
            PipelineAdapter(application)
        pipeline_recycle.adapter = pipeline_adapter
        viewModel.readAllData.observe(requireActivity(), Observer {
            accountingList: List<Accounting> ->
            pipeline_adapter.readData(accountingList)
        })


    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            PipelineFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}