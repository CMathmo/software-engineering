package com.wad.tBook.statistical


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import java.util.*


class AccountFragment : Fragment() {

    private val accountList = ArrayList<Accounting>()
    //创建数据源
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val View = inflater.inflate(R.layout.fragment_account,container,false)
        val listview = View.findViewById<ListView>(R.id.account_list)
        //初始化数据列表
        dataInfo()
        //创建适配器
        val adapter = AccountAdapter(this,R.layout.list_account_view,accountList)
        //将适配器对象传入ListView,从而建立ListView和数据之间的联系
        listview.adapter = adapter
//        listview.setOnItemClickListener { parent, view, position, id ->
//            val account = accountList[position]
//            Toast.makeText(this,account.accountingAcconut,Toast.LENGTH_SHORT).show()
//        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun dataInfo(){

    }

    companion object {
        private const val newAccountActivityRequestCode = 1
        const val PARAMS_UID = "uid"
        const val PARAMS_SOURCE = "source"
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAMS_UID, param1)
                    putString(PARAMS_SOURCE, param2)
                }
            }
    }
}