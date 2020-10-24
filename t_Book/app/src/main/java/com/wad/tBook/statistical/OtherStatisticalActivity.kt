package com.wad.tBook.statistical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase
import com.wad.tBook.statistical.OtherStatisticalAdapter.ClassAdapter
import com.wad.tBook.statistical.OtherStatisticalAdapter.MemberAdapter
import com.wad.tBook.statistical.OtherStatisticalAdapter.MerchantAdapter
import com.wad.tBook.statistical.OtherStatisticalAdapter.ProjectAdapter
import kotlinx.android.synthetic.main.layout_class_card.*
import kotlinx.android.synthetic.main.layout_member_card.*
import kotlinx.android.synthetic.main.layout_merchant_card.*
import kotlinx.android.synthetic.main.layout_project_card.*

class OtherStatisticalActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(OtherStatisticalViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_statistical)

        val InfoClassData : MutableList<OtherStatisticalRepository.TA> = classDataInfo()
        recycle_class.layoutManager = LinearLayoutManager(applicationContext)
        val class_adapter = ClassAdapter(application,InfoClassData)
        recycle_class.adapter = class_adapter
        viewModel.readAllData.observe(this) {
                accountingList: List<Accounting> ->
            var classList : MutableList<OtherStatisticalRepository.TA> = classDataInfo()
            classList = classStatistical(accountingList,classList)
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
    private fun classDataInfo(): MutableList<OtherStatisticalRepository.TA> {
        val readClassData : List<OtherStatisticalRepository.proType> = tBookDatabase.getDBInstace(applicationContext).proDao().getClassFrom("类别")
        val classList = mutableListOf(
            OtherStatisticalRepository.TA(
                0.0,
                readClassData[0].firstClass,
                readClassData[0].secondClass
            )
        )
        val n = readClassData.size
        for (index in 1 until n) {
            classList.add(OtherStatisticalRepository.TA(0.0,readClassData[index].firstClass,readClassData[index].secondClass))
        }
        println(classList)
        return classList
    }
    private fun classStatistical(
        accountingList:List<Accounting>,
        classList:MutableList<OtherStatisticalRepository.TA>):
            MutableList<OtherStatisticalRepository.TA>{
        val n = accountingList.size
        for (value in accountingList){
            for (item in classList){
                if (item.firstClass == value.accountingClass.firstClass && item.secondClass == value.accountingClass.secondClass){
                    when(value.accountingType){
                        "收入" -> item.amount += value.accountingAmount
                        else -> item.amount -= value.accountingAmount
                    }
                }
            }
        }
        return classList
    }

    private fun memberDataInfo(): MutableList<OtherStatisticalRepository.TA> {
        val readMemberData : List<OtherStatisticalRepository.proType> = tBookDatabase.getDBInstace(applicationContext).proDao().getClassFrom("成员")
        val memberList = mutableListOf(
            OtherStatisticalRepository.TA(
                0.0,
                readMemberData[0].firstClass,
                readMemberData[0].secondClass
            )
        )
        val n = readMemberData.size
        for (index in 1 until n) {
            memberList.add(OtherStatisticalRepository.TA(0.0,readMemberData[index].firstClass,readMemberData[index].secondClass))
        }
        println(memberList)
        return memberList
    }
    private fun memberStatistical(
        accountingList:List<Accounting>,
        memberList:MutableList<OtherStatisticalRepository.TA>):
            MutableList<OtherStatisticalRepository.TA>{
        val n = accountingList.size
        for (value in accountingList){
            for (item in memberList){
                if (item.firstClass == value.accountingMember?.firstClass  && item.secondClass == value.accountingMember?.secondClass){
                    when(value.accountingType){
                        "收入" -> item.amount += value.accountingAmount
                        else -> item.amount -= value.accountingAmount
                    }
                }
            }
        }
        println(memberList)
        return memberList
    }

    private fun MerchantDataInfo(): MutableList<OtherStatisticalRepository.TA> {
        val readMerchantData : List<OtherStatisticalRepository.proType> = tBookDatabase.getDBInstace(applicationContext).proDao().getClassFrom("商家")
        val merchantList = mutableListOf(
            OtherStatisticalRepository.TA(
                0.0,
                readMerchantData[0].firstClass,
                readMerchantData[0].secondClass
            )
        )
        val n = readMerchantData.size
        for (index in 1 until n) {
            merchantList.add(OtherStatisticalRepository.TA(0.0,readMerchantData[index].firstClass,readMerchantData[index].secondClass))
        }
        println(merchantList)
        return merchantList
    }
    private fun merchantStatistical(
        accountingList:List<Accounting>,
        merchantList:MutableList<OtherStatisticalRepository.TA>):
            MutableList<OtherStatisticalRepository.TA>{
        val n = accountingList.size
        for (value in accountingList){
            for (item in merchantList){
                if (item.firstClass == value.accountingMerchant?.firstClass  && item.secondClass == value.accountingMerchant?.secondClass){
                    when(value.accountingType){
                        "收入" -> item.amount += value.accountingAmount
                        else -> item.amount -= value.accountingAmount
                    }
                }
            }
        }
        println(merchantList)
        return merchantList
    }

    private fun ProjectDataInfo(): MutableList<OtherStatisticalRepository.TA> {
        val readProjectData : List<OtherStatisticalRepository.proType> = tBookDatabase.getDBInstace(applicationContext).proDao().getClassFrom("项目")
        val projectList = mutableListOf(
            OtherStatisticalRepository.TA(
                0.0,
                readProjectData[0].firstClass,
                readProjectData[0].secondClass
            )
        )
        val n = readProjectData.size
        for (index in 1 until n) {
            projectList.add(OtherStatisticalRepository.TA(0.0,readProjectData[index].firstClass,readProjectData[index].secondClass))
        }
        println(projectList)
        return projectList
    }
    private fun projectStatistical(
        accountingList:List<Accounting>,
        projectList:MutableList<OtherStatisticalRepository.TA>):
            MutableList<OtherStatisticalRepository.TA>{
        val n = accountingList.size
        for (value in accountingList){
            for (item in projectList){
                if (item.firstClass == value.accountingMerchant?.firstClass  && item.secondClass == value.accountingMerchant?.secondClass){
                    when(value.accountingType){
                        "收入" -> item.amount += value.accountingAmount
                        else -> item.amount -= value.accountingAmount
                    }
                }
            }
        }
        println(projectList)
        return projectList
    }
}