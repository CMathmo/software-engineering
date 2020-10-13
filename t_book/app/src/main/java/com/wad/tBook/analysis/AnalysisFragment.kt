package com.wad.tBook.analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wad.tBook.R

class AnalysisFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }

    companion object {
        const val PARAMS_UID = "uid"
        const val PARAMS_SOURCE = "source"
        fun newInstance(param1: String, param2: String) =
            AnalysisFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAMS_UID, param1)
                    putString(PARAMS_UID, param2)
                }
            }
    }
}