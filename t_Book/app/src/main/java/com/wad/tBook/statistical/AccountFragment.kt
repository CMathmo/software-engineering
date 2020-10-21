package com.wad.tBook.statistical


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.ColumnInfo
import com.wad.tBook.R
import com.wad.tBook.room.*
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
    }

    private fun dataDisplay(view : View,typeList : List<AccountRepository.TypeAmount>): CharSequence? {
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

    private fun setUpAccountCardView(view: View,secondClass: List<AccountRepository.AccountClass>){
        val accountAmountView : TextView = view.findViewById(R.id.text_amount)
        for (item in TypeAccountList) {
            for (value in secondClass){
                if (item.secondClass == value.Class) {
                    accountAmountView.text = value.Amount.toString()
                }
            }
        }
    }

    private fun TAdataInfo(){

    }

    data class TA(
        val amount:Double,
        @ColumnInfo(name = "accounting_account_first_class") val firstClass:String,
        @ColumnInfo(name = "accounting_account_second_class") val secondClass:String
    )

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        fun newInstance(columnCount: Int) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
        val TypeAccountList = listOf(
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



