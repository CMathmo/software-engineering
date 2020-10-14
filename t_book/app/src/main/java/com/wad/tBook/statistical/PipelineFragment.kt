package com.wad.tBook.statistical

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wad.tBook.R

class PipelineFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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