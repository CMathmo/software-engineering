package com.wad.tBook.statistical.others

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase
import kotlinx.android.synthetic.main.layout_class_card.*
import kotlinx.android.synthetic.main.layout_member_card.*
import kotlinx.android.synthetic.main.layout_merchant_card.*
import kotlinx.android.synthetic.main.layout_project_card.*

class OtherStatisticalActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(OtherStatisticalViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_statistical)
    }

    override fun onStart() {
        super.onStart()
        val InfoClassData : MutableList<OtherStatisticalRepository.TA_fc> = classDataInfo()
        recycle_class.layoutManager = LinearLayoutManager(applicationContext)
        val class_adapter = ClassAdapter(application,InfoClassData)
        recycle_class.adapter = class_adapter
        viewModel.readAllData.observe(this) {
                accountingList: List<Accounting> ->
            var classList : MutableList<OtherStatisticalRepository.TA_fc> = classDataInfo()
            classList = classStatistical(accountingList,classList)
            println(classList)
            class_adapter.readData(classList)
        }

        val InfoMemberData : MutableList<OtherStatisticalRepository.TA> = memberDataInfo()
        recycle_member.layoutManager = LinearLayoutManager(applicationContext)
        val member_adapter = MemberAdapter(application,InfoMemberData)
        recycle_member.adapter = member_adapter
        viewModel.readAllData.observe(this) {
                accountingList: List<Accounting> ->
            var memberList : MutableList<OtherStatisticalRepository.TA> = memberDataInfo()
            memberList = memberStatistical(accountingList,memberList)
            member_adapter.readData(memberList)
        }

        val InfoMerchantData : MutableList<OtherStatisticalRepository.TA> = MerchantDataInfo()
        recycle_merchant.layoutManager = LinearLayoutManager(applicationContext)
        val merchant_adapter = MerchantAdapter(application,InfoMerchantData)
        recycle_merchant.adapter = merchant_adapter
        viewModel.readAllData.observe(this) {
                accountingList: List<Accounting> ->
            var merchantList : MutableList<OtherStatisticalRepository.TA> = MerchantDataInfo()
            merchantList = merchantStatistical(accountingList,merchantList)
            merchant_adapter.readData(merchantList)
        }

        val InfoProjectData : MutableList<OtherStatisticalRepository.TA> = ProjectDataInfo()
        recycle_project.layoutManager = LinearLayoutManager(applicationContext)
        val project_adapter = ProjectAdapter(application,InfoProjectData)
        recycle_project.adapter = project_adapter
        viewModel.readAllData.observe(this) {
                accountingList: List<Accounting> ->
            var projectList : MutableList<OtherStatisticalRepository.TA> = ProjectDataInfo()
            projectList = projectStatistical(accountingList,projectList)
            project_adapter.readData(projectList)
        }
    }

    private fun classDataInfo(): MutableList<OtherStatisticalRepository.TA_fc> {
        val readClassData : List<String> = tBookDatabase.getDBInstace(applicationContext).proDao().getFirstClassType("类别")
        val classList = mutableListOf(
            OtherStatisticalRepository.TA_fc(
                0.0,
                0.0,
                readClassData[0]
            )
        )
        val n = readClassData.size
        for (index in 1 until n) {
            classList.add(OtherStatisticalRepository.TA_fc(0.0, 0.0, readClassData[index]))
        }
        return classList
    }
    private fun classStatistical(
        accountingList:List<Accounting>,
        classList:MutableList<OtherStatisticalRepository.TA_fc>): MutableList<OtherStatisticalRepository.TA_fc>{
        for (item in classList){
            val income = tBookDatabase.getDBInstace(applicationContext).actDao().getFirstClassIncomeClassDataIn(item.firstClass)
            val outcome = tBookDatabase.getDBInstace(applicationContext).actDao().getFirstClassExpenditureClassDataIn(item.firstClass)
            item.inflowAmount = income
            item.outflowAmount = outcome
        }
        return classList
    }

    private fun memberDataInfo(): MutableList<OtherStatisticalRepository.TA> {
        val readMemberData : List<OtherStatisticalRepository.proType> = tBookDatabase.getDBInstace(applicationContext).proDao().getClassFrom("成员")
        val memberList = mutableListOf(
            OtherStatisticalRepository.TA(
                0.0,
                0.0,
                readMemberData[0].firstClass,
                readMemberData[0].secondClass
            )
        )
        val n = readMemberData.size
        for (index in 1 until n) {
            memberList.add(
                OtherStatisticalRepository.TA(
                    0.0,
                    0.0,
                    readMemberData[index].firstClass,
                    readMemberData[index].secondClass
                )
            )
        }
        println(memberList)
        return memberList
    }
    private fun memberStatistical(
        accountingList:List<Accounting>,
        memberList:MutableList<OtherStatisticalRepository.TA>):
            MutableList<OtherStatisticalRepository.TA>{
        for (item in memberList){
            val income = tBookDatabase.getDBInstace(applicationContext).actDao().getAllIncomeMemberDataIn(item.firstClass,item.secondClass)
            val outcome = tBookDatabase.getDBInstace(applicationContext).actDao().getAllExpenditureMemberDataIn(item.firstClass,item.secondClass)
            item.inflowAmount = income
            item.outflowAmount = outcome
        }
        println(memberList)
        return memberList
    }

    private fun MerchantDataInfo(): MutableList<OtherStatisticalRepository.TA> {
        val readMerchantData : List<OtherStatisticalRepository.proType> = tBookDatabase.getDBInstace(applicationContext).proDao().getClassFrom("商家")
        val merchantList = mutableListOf(
            OtherStatisticalRepository.TA(
                0.0,
                0.0,
                readMerchantData[0].firstClass,
                readMerchantData[0].secondClass
            )
        )
        val n = readMerchantData.size
        for (index in 1 until n) {
            merchantList.add(
                OtherStatisticalRepository.TA(
                    0.0,
                    0.0,
                    readMerchantData[index].firstClass,
                    readMerchantData[index].secondClass
                )
            )
        }
        println(merchantList)
        return merchantList
    }
    private fun merchantStatistical(
        accountingList:List<Accounting>,
        merchantList:MutableList<OtherStatisticalRepository.TA>):
            MutableList<OtherStatisticalRepository.TA>{
        for (item in merchantList){
            val income = tBookDatabase.getDBInstace(applicationContext).actDao().getAllIncomeMerchantDataIn(item.firstClass,item.secondClass)
            val outcome = tBookDatabase.getDBInstace(applicationContext).actDao().getAllExpenditureMerchantDataIn(item.firstClass,item.secondClass)
            item.inflowAmount = income
            item.outflowAmount = outcome
        }
        println(merchantList)
        return merchantList
    }

    private fun ProjectDataInfo(): MutableList<OtherStatisticalRepository.TA> {
        val readProjectData : List<OtherStatisticalRepository.proType> = tBookDatabase.getDBInstace(applicationContext).proDao().getClassFrom("项目")
        val projectList = mutableListOf(
            OtherStatisticalRepository.TA(
                0.0,
                0.0,
                readProjectData[0].firstClass,
                readProjectData[0].secondClass
            )
        )
        val n = readProjectData.size
        for (index in 1 until n) {
            projectList.add(
                OtherStatisticalRepository.TA(
                    0.0,
                    0.0,
                    readProjectData[index].firstClass,
                    readProjectData[index].secondClass
                )
            )
        }
        println(projectList)
        return projectList
    }
    private fun projectStatistical(
        accountingList:List<Accounting>,
        projectList:MutableList<OtherStatisticalRepository.TA>):
            MutableList<OtherStatisticalRepository.TA>{
        for (item in projectList){
            val income = tBookDatabase.getDBInstace(applicationContext).actDao().getAllIncomeProjectDataIn(item.firstClass,item.secondClass)
            val outcome = tBookDatabase.getDBInstace(applicationContext).actDao().getAllExpenditureProjectDataIn(item.firstClass,item.secondClass)
            item.inflowAmount = income
            item.outflowAmount = outcome
        }
        println(projectList)
        return projectList
    }
}