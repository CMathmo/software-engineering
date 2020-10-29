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
import com.wad.tBook.analysis.DateUtil
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.MultilevelClassification
import com.wad.tBook.room.tBookDatabase
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

        view?.find<Button>(R.id.test_button)?.setOnClickListener {
           addTestData()
        }

        view?.find<Button>(R.id.clear_button)?.setOnClickListener {
            val roomdb = tBookDatabase.getDBInstace(requireActivity().application)
            roomdb.actDao().deleteAll()
        }
    }


    fun addTestData(){
        Thread{
            val roomdb = tBookDatabase.getDBInstace(requireActivity().application)
            roomdb.actDao().deleteAll()
            for(i in 1..3000){
                val type = mutableListOf<String>("收入","支出","转账").random()
                val items = mutableListOf<String>("类别","账户","账户","商家","项目","成员")
                val results = mutableListOf<MultilevelClassification>()
                val date = DateUtil.getRandomDateFromTo(2018,2020)
                for(item in items){
                    //Log.d(TAG,"momo-test:"+type+item+roomdb.proDao().getFirstClassFrom("收入","类别"))
                    val fclass = roomdb.proDao().getFirstClassFrom(type,item).random()
                    val sclass = roomdb.proDao().getSecondClassFrom(type,item,fclass).random()
                    results.add(MultilevelClassification(fclass,sclass))
                }
                val accounting = Accounting(
                    accountingType = type,
                    accountingAmount = (1..5000).random().toDouble(),
                    accountingClass = results[0],
                    accountingAcconut = results[1],
                    accountingAcconut_2 = if (type == "转账"){
                        results[2]
                    }else{
                        null
                    },
                    accountingMerchant = results[3],
                    accountingProject = results[4],
                    accountingMember = results[5],
                    accountingTime = date,
                    accountingRemark = ""
                )
                roomdb.actDao().addAccountingData(accounting)
            }
        }.start()
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