package com.wad.tBook.statistical

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import com.wad.tBook.MainActivity
import com.wad.tBook.R
import com.wad.tBook.getItemDecoration
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase
import com.wad.tBook.statistical.OtherStatisticalAdapter.ClassAdapter
import com.wad.tBook.toMoneyFormatted
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.layout_class_card.*
import java.util.*


class AccountFragment : Fragment() {

    private val TAG = AccountFragment::class.qualifiedName

    private val viewModel by lazy { ViewModelProvider(this).get(AccountViewModel::class.java) }
    var recyclerview: RecyclerView? = null
    var accountAdapter: AccountAdapter? = null

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        Log.d(TAG,"momo:AccountFragment-create")
    }

    //创建数据源
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", "onCreateView")
        return inflater.inflate(R.layout.fragment_account, container, false)
    }



    private fun setUpRecyclerView() {
        println("debug 1024")
        val InfoAccountData : MutableList<OtherStatisticalRepository.TA> = AccountDataInfo()
        recycle_account.layoutManager = LinearLayoutManager(context)
        val account_adapter = activity?.application?.let { AccountAdapter(InfoAccountData) }
        recycle_account.adapter = account_adapter
        viewModel.readAllData.observe(requireActivity()) {
                accountingList: List<Accounting> ->
            var accountList : MutableList<OtherStatisticalRepository.TA> = AccountDataInfo()
            accountList = accountStatistical(accountingList,accountList)
            println(accountList)
            account_adapter?.readData(accountList)
        }
        recyclerview = view?.findViewById(R.id.recycle_account)
        val intent = Intent(context, AccountDetailActivity::class.java)
        account_adapter?.mOnRecyclerViewItemClick = object :
            AccountAdapter.OnRecyclerViewItemClick<String> {
            override fun OnItemClick(view: View?, position: Int) {
                val accountClass: TextView? = view?.findViewById(R.id.text_account_type2)
                if (accountClass != null) {
                    intent.putExtra("account_class",accountClass.text)
                }
                Toast.makeText(
                    context,
                    "点击的item位置是${position}",
                    Toast.LENGTH_SHORT
                ).show()
                context?.startActivity(intent)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        val typeList:List<AccountRepository.TypeAmount> = dataInfo()
        view?.let { dataDisplay(it, typeList) }
        setUpRecyclerView()
    }

    private fun dataInfo(): List<AccountRepository.TypeAmount> {
        val readATAData : List<AccountRepository.TypeAmount>? =
            activity?.application?.let { tBookDatabase.getDBInstace(it).actDao().readAccountTypeData() }
        val income = "收入"
        val outcome = "支出"
        val typeList = listOf(
            AccountRepository.TypeAmount(income, 0.0),
            AccountRepository.TypeAmount(outcome, 0.0)
        )
        if (readATAData != null) {
            for (item in readATAData){
                when(item.Type){
                    income -> typeList[0].Amount += item.Amount
                    outcome -> typeList[1].Amount += item.Amount
                }
            }
        }
        return typeList
    }

    @SuppressLint("SetTextI18n")
    private fun dataDisplay(view: View, typeList: List<AccountRepository.TypeAmount>): CharSequence? {
        val incomeView : TextView = view.findViewById(R.id.text_income)
        val outcomeView : TextView = view.findViewById(R.id.text_outcome)
        val liabilityView : TextView = view.findViewById(R.id.text_liability)
        liabilityView.text = (typeList[0].Amount - typeList[1].Amount).toMoneyFormatted()
        incomeView.text = "流入：" + typeList[0].Amount.toMoneyFormatted()
        outcomeView.text = "流出：" + typeList[1].Amount.toMoneyFormatted()
        return liabilityView.text
    }


    private fun AccountDataInfo(): MutableList<OtherStatisticalRepository.TA> {
        val readAccountData : List<OtherStatisticalRepository.proType> = tBookDatabase.getDBInstace(
            requireActivity().applicationContext
        ).proDao().getClassFrom("账户")
        val accountList = mutableListOf(
            OtherStatisticalRepository.TA(
                0.0,
                0.0,
                readAccountData[0].firstClass,
                readAccountData[0].secondClass
            )
        )
        val n = readAccountData.size
        for (index in 1 until n) {
            accountList.add(OtherStatisticalRepository.TA(0.0,0.0,readAccountData[index].firstClass,readAccountData[index].secondClass))
        }
        return accountList
    }
    private fun accountStatistical(
        accountingList:List<Accounting>,
        accountList:MutableList<OtherStatisticalRepository.TA>):
            MutableList<OtherStatisticalRepository.TA>{
        for (item in accountList){
            val income = tBookDatabase.getDBInstace(requireContext()).actDao().getAllIncomeAccountingDataIn(item.firstClass,item.secondClass)
            val outcome = tBookDatabase.getDBInstace(requireContext()).actDao().getAllExpenditureAccountingDataIn(item.firstClass,item.secondClass)
            item.inflowAmount = income
            item.outflowAmount = outcome
        }
        return accountList
    }


    data class TA(
        var amount: Double,
        @ColumnInfo(name = "accounting_account_first_class") val firstClass: String,
        @ColumnInfo(name = "accounting_account_second_class") val secondClass: String
    )

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        fun newInstance(columnCount: Int) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}



