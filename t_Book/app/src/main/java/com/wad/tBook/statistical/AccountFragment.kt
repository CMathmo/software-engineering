package com.wad.tBook.statistical

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
import androidx.room.ColumnInfo
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase
import kotlinx.android.synthetic.main.fragment_account.*
import java.util.*


class AccountFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(AccountViewModel::class.java) }
    private val accountList = ArrayList<Accounting>()
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    //创建数据源
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", "onCreateView")
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        setUpRecyclerView()
    }


    private fun setUpRecyclerView() {
        recycle_account.apply {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            println(TypeAccountList)
            adapter = AccountAdapter(TypeAccountList)
        }
    }


    override fun onResume() {
        super.onResume()
        val typeList:List<AccountRepository.TypeAmount> = dataInfo()
        TAdataInfo()
        view?.let { dataDisplay(it, typeList) }
        val readActData : List<Accounting>? =
            activity?.application?.let { tBookDatabase.getDBInstace(it).actDao().readAccountingDataWithoutLiveData() }
        val secondClass = readActData?.let { getAccountType(it) }
        view?.let {
            if (secondClass != null) {
                setUpAccountCardView(it, secondClass)
            }
        }
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
    }

    private fun dataDisplay(view: View, typeList: List<AccountRepository.TypeAmount>): CharSequence? {
        val incomeView : TextView = view.findViewById(R.id.text_income)
        val outcomeView : TextView = view.findViewById(R.id.text_outcome)
        val transferView : TextView = view.findViewById(R.id.text_transaction)
        val liabilityView : TextView = view.findViewById(R.id.text_liability)
        liabilityView.text = (typeList[0].Amount - typeList[1].Amount - typeList[2].Amount).toString()
        incomeView.text = typeList[0].Amount.toString()
        outcomeView.text = typeList[1].Amount.toString()
        transferView.text = typeList[2].Amount.toString()
        return liabilityView.text
    }

    private fun getAccountType(accounting: List<Accounting>): List<AccountRepository.AccountClass> {
        val creditCard = "信用卡"
        val eWallet = "电子钱包"
        val cash = "现金"
        val rechargeableCard = "充值卡"
        val bond = "债券"
        val secondClassList = mutableListOf<String>(
            "平安银行",
            "浦发银行",
            "微信",
            "支付宝",
            "人民币",
            "校园卡",
            "沃尔玛购物卡",
            "债券"
        )
        val n = secondClassList.size
        val secondList = mutableListOf(AccountRepository.AccountClass(secondClassList[0], 0.0))
        for (index in 1 until n) {
            secondList.add(AccountRepository.AccountClass(secondClassList[index], 0.0))
        }
        for (index in 0 until n) {
            for (item in accounting) {
                val second = item.accountingAcconut.secondClass
                if (second == secondClassList[index]) {
                    when(item.accountingType) {
                        "收入" -> secondList[index].Amount += item.accountingAmount
                        else -> secondList[index].Amount -= item.accountingAmount
                    }}
            }
        }
        val firstList = listOf(
            AccountRepository.AccountClass(creditCard, 0.0),
            AccountRepository.AccountClass(eWallet, 0.0),
            AccountRepository.AccountClass(cash, 0.0),
            AccountRepository.AccountClass(rechargeableCard, 0.0),
            AccountRepository.AccountClass(bond, 0.0)
        )
        for (item in secondList){
            when(item.Class) {
                secondClassList[0] -> firstList[0].Amount += item.Amount
                secondClassList[1] -> firstList[0].Amount += item.Amount
                secondClassList[2] -> firstList[1].Amount += item.Amount
                secondClassList[3] -> firstList[1].Amount += item.Amount
                secondClassList[4] -> firstList[2].Amount += item.Amount
                secondClassList[5] -> firstList[2].Amount += item.Amount
                secondClassList[6] -> firstList[3].Amount += item.Amount
            }
        }
        println(secondList)
        return secondList
    }

    private fun setUpAccountCardView(view: View, secondClass: List<AccountRepository.AccountClass>){
        //val accountAmountView : TextView = view.findViewById(R.id.text_amount)
        for (item in TypeAccountList) {
            for (value in secondClass){
                if (item.secondClass == value.Class) {
                    item.amount = value.Amount
                }
            }
        }
        println(TypeAccountList)
    }

    private fun TAdataInfo(){

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
        var TypeAccountList = listOf(
            TA(
                amount = 0.00,
                firstClass = "信用卡",
                secondClass = "平安银行"
            ),
            TA(
                amount = 0.00,
                firstClass = "信用卡",
                secondClass = "浦发银行"
            ),
            TA(
                amount = 0.00,
                firstClass = "电子钱包",
                secondClass = "微信"
            ),
            TA(
                amount = 0.00,
                firstClass = "电子钱包",
                secondClass = "支付宝"
            ),
            TA(
                amount = 0.00,
                firstClass = "现金",
                secondClass = "人民币"
            ),
            TA(
                amount = 0.00,
                firstClass = "充值卡",
                secondClass = "校园卡"
            ),
            TA(
                amount = 0.00,
                firstClass = "充值卡",
                secondClass = "沃尔玛购物卡"
            ),
            TA(
                amount = 0.00,
                firstClass = "债券",
                secondClass = "国债"
            )
        )
    }

}



