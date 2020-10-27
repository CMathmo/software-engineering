package com.wad.tBook.setting

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wad.tBook.*
import kotlinx.android.synthetic.main.fragment_setting.*
import org.jetbrains.anko.find

class SettingFragment : Fragment(){

    private val pref : SharedPreferences = MyApplication.context.getSharedPreferences("MyPref", 0)

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
        Log.d("status","on Resume")
        //val button = findViewById(R.id.button1) as Button
        view?.find<Button>(R.id.backup_button)?.setOnClickListener {
            dataBackup()
        }
        view?.find<Button>(R.id.restore_button)?.setOnClickListener {
            dataRecover()
        }
        view?.find<Button>(R.id.person_button)?.setOnClickListener {
            val intent = Intent(requireContext(), PersonMsgActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            requireContext().startActivity(intent)
        }


        view?.find<Button>(R.id.password_button)?.setOnClickListener {
            val intent = Intent(requireContext(), SettingPassword::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            requireContext().startActivity(intent)
        }
        view?.find<Button>(R.id.dev_button)?.setOnClickListener {
            val intent = Intent(requireContext(), DevMsgActivity::class.java)

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            requireContext().startActivity(intent)
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onStart() {
        super.onStart()
        Log.d("status","on Start")
        if (pref.contains("email")){
            println(pref.getString("email",""))
            edt_email.text = pref.getString("email","")
        }
        if (pref.contains("phone")){
            edt_phone.text = pref.getString("phone","")
        }
        if (pref.contains("address")){
            edt_address.text = pref.getString("address","")
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("status","on Stop")
        val editor = pref.edit()
        editor.putString("email",edt_email.text.toString())
        editor.putString("phone",edt_phone.text.toString())
        editor.putString("address",edt_address.text.toString())
        editor.apply()
    }

    override fun onPause() {
        super.onPause()
        Log.d("status","on Pause")
    }

    private fun dataRecover() {
        // TODO Auto-generated method stub
        BackupTask(requireContext()).execute("restoreDatabase")
        Toast.makeText(
            requireContext(),
            "还原成功",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun dataBackup() {
        // TODO Auto-generated method stub
        BackupTask(requireContext()).execute("backupDatabase")
        Toast.makeText(
            requireContext(),
            "备份成功",
            Toast.LENGTH_SHORT
        ).show()
    }


    companion object {
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}