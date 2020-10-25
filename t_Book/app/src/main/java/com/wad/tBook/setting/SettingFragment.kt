package com.wad.tBook.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.wad.tBook.BackupTask
import com.wad.tBook.R
import com.wad.tBook.accounting.AccountingActivity
import org.jetbrains.anko.find


class SettingFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_setting, container, false)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val backup_button : Button? = view?.find(R.id.backup_button)
        //val button = findViewById(R.id.button1) as Button
        if (backup_button != null) {
            backup_button.setOnClickListener {
                dataBackup()
            }
        }
        val restore_button : Button? = view?.find(R.id.restore_button)
        if (restore_button != null){
            restore_button.setOnClickListener {
                dataRecover()
            }
        }
    }

    private fun dataRecover() {
        // TODO Auto-generated method stub
        BackupTask(requireContext()).execute("restoreDatabase")
    }

    private fun dataBackup() {
        // TODO Auto-generated method stub
        BackupTask(requireContext()).execute("backupDatabase")
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}