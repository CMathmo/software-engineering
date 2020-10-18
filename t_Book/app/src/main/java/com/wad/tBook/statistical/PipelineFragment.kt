package com.wad.tBook.statistical

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wad.tBook.R
import com.wad.tBook.room.Accounting

class PipelineFragment : Fragment(){

    private val accountList = ArrayList<Accounting>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val View = inflater.inflate(R.layout.fragment_pipeline,container,false)
        val recycleview = View.findViewById<RecyclerView>(R.id.pipeline_recycle)
        val re_adapter = PipelineAdapter(accountList)
        recycleview.adapter = re_adapter
        return inflater.inflate(R.layout.fragment_pipeline, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            PipelineFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}