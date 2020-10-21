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
<<<<<<< HEAD
        Log.d("TAG","onCreateView")
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
        //val secondClass = viewModel.getAccountType(accountList)
        //viewModel.readAllData.observe(viewLifecycleOwner){
        //    view?.let { it1 -> setUpAccountCardView(it1,secondClass) }
        //}
<<<<<<< Updated upstream
=======

        val secondClass = viewModel.getAccountType(accountList)
        viewModel.readAllData.observe(viewLifecycleOwner){
//            view?.let { it1 -> setUpAccountCardView(it1,secondClass) }
        }

>>>>>>> Stashed changes
        recycle_account.apply {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = AccountAdapter(TypeAccountList)
        }
    }

    override fun onResume() {
        super.onResume()
        val typeList:List<AccountRepository.TypeAmount> = dataInfo()
        TAdataInfo()
        view?.let { dataDisplay(it,typeList) }
    }

    private fun dataInfo(): List<AccountRepository.TypeAmount> {
        val readATAData : List<AccountRepository.TypeAmount>? =
            activity?.application?.let { tBookDatabase.getDBInstace(it).actDao().readAccountTypeData() }
        val income = "收入"
        val outcome = "支出"
        val transfer = "转账"
        val typeList = listOf(
            AccountRepository.TypeAmount(income, 0.0),
            AccountRepository.TypeAmount(outcome, 0.0),
            AccountRepository.TypeAmount(transfer, 0.0)
        )
        if (readATAData != null) {
            for (item in readATAData){
                when(item.Type){
                    income -> typeList[0].Amount += item.Amount
                    outcome -> typeList[1].Amount += item.Amount
                    transfer -> typeList[2].Amount += item.Amount
                }
            }
        }
        return typeList
=======
        val View = inflater.inflate(R.layout.fragment_account,container,false)
        val listview = View.findViewById<ListView>(R.id.account_list)
        //初始化数据列表
        dataInfo()
        //创建适配器
        val list_adapter = AccountAdapter(this,R.layout.list_account_view,accountList)
        //将适配器对象传入ListView,从而建立ListView和数据之间的联系
        listview.adapter = list_adapter
//        listview.setOnItemClickListener { parent, view, position, id ->
//            val account = accountList[position]
//            Toast.makeText(this,account.accountingAcconut,Toast.LENGTH_SHORT).show()
//        }
        return super.onCreateView(inflater, container, savedInstanceState)
>>>>>>> parent of b9f5d09... 按你们要求改的数据
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