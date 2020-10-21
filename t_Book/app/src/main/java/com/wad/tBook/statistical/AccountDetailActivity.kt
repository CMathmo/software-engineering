package com.wad.tBook.statistical

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.wad.tBook.R
import com.wad.tBook.room.tBookDatabase

class AccountDetailActivity : AppCompatActivity() {

    var AccountDetail: String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)
        val AccountClassDetail : TextView = findViewById(R.id.account_class_detail)
        AccountDetail = intent.getStringExtra("account_class")
        AccountClassDetail.text = AccountDetail
        val typeList:List<AccountRepository.TypeAmount> = dataInfo()
        println(typeList)
        dataDisplay(typeList)
    }
    private fun dataInfo(): List<AccountRepository.TypeAmount> {
        val readDACData : List<AccountRepository.TypeAmount>? =
            AccountDetail?.let {
                tBookDatabase.getDBInstace(applicationContext).actDao().readAccountDetailData(
                    it
                )
            }
        val income = "收入"
        val outcome = "支出"
        val transfer = "转账"
        val typeList = listOf(
            AccountRepository.TypeAmount(income, 0.0),
            AccountRepository.TypeAmount(outcome, 0.0),
            AccountRepository.TypeAmount(transfer, 0.0)
        )
        if (readDACData != null) {
            for (item in readDACData){
                when(item.Type){
                    income -> typeList[0].Amount += item.Amount
                    outcome -> typeList[1].Amount += item.Amount
                    transfer -> typeList[2].Amount += item.Amount
                }
            }
        }
        return typeList
    }
    @SuppressLint("SetTextI18n")
    private fun dataDisplay(typeList: List<AccountRepository.TypeAmount>){
        val incomeView : TextView = findViewById(R.id.account_income_detail)
        val outcomeView : TextView = findViewById(R.id.account_outcome_detail)
        val transferView : TextView = findViewById(R.id.account_transfer_detail)
        incomeView.text = "收入：" + typeList[0].Amount.toString()
        outcomeView.text = "支出：" + typeList[1].Amount.toString()
        transferView.text = "转账：" + typeList[2].Amount.toString()
    }
}