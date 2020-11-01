package com.wad.tBook.statistical.datepipeline

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wad.tBook.accounting.AccountingActivity
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase


class DatePipelineAdapter (
    context: Context
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val context : Context = context
    var itemList : List<Accounting> = emptyList<Accounting>()
    var viewTypeList : MutableList<Int> = mutableListOf()

    class PipelineViewHolder(view:View):RecyclerView.ViewHolder(view){
        val textID : TextView = view.findViewById(R.id.textID)
        val textType : TextView = view.findViewById(R.id.textType)
        val textAmount : TextView = view.findViewById(R.id.textAmount)
        val textTime : TextView = view.findViewById(R.id.textTime)
        val textClass : TextView = view.findViewById(R.id.textClass)
        val moreButton : ImageButton = view.findViewById(R.id.moreButton)
    }

    class PipelineViewHolder_detail(view:View):RecyclerView.ViewHolder(view){
        val textID : TextView = view.findViewById(R.id.textID)
        val textType : TextView = view.findViewById(R.id.textType)
        val textAmount : TextView = view.findViewById(R.id.textAmount)
        val textTime : TextView = view.findViewById(R.id.textTime)
        val textClass : TextView = view.findViewById(R.id.textClass)
        val textAccount : TextView = view.findViewById(R.id.detail_textAccount)
        val textMember : TextView = view.findViewById(R.id.detail_textMember)
        val textProject : TextView = view.findViewById(R.id.detail_textProject)
        val textMerchant : TextView = view.findViewById(R.id.detail_textMerchant)
        val textRemark : TextView = view.findViewById(R.id.detail_textRemark)
        val upButton : ImageButton = view.findViewById(R.id.upButton)
        val editButton : ImageButton = view.findViewById(R.id.editButton)
        val deleteButton : ImageButton = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder{
        return when(viewType){
            0 -> PipelineViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.recycle_pipeline_view, parent,false)
            )
            else -> PipelineViewHolder_detail(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycle_pipeline_detail_view,  parent,false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypeList[position]
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            0 -> {
                val currentItem = itemList.get(position)
                val viewHolder = holder as PipelineViewHolder
                viewHolder.textID.text = currentItem.accountingId.toString()
                viewHolder.textType.text = currentItem.accountingType
                viewHolder.textAmount.text = currentItem.accountingAmount.toString()
                viewHolder.textTime.text = currentItem.accountingTime
                viewHolder.textClass.text = currentItem.accountingClass.firstClass + " : " + currentItem.accountingClass.secondClass
                viewHolder.moreButton.setOnClickListener {
                    viewTypeList[position] = 1
                    notifyItemChanged(position)
                }
                println(currentItem)
            }
            else ->{
                val currentItem = itemList.get(position)
                val viewHolder = holder as PipelineViewHolder_detail
                viewHolder.textID.text = currentItem.accountingId.toString()
                viewHolder.textType.text = currentItem.accountingType
                viewHolder.textAmount.text = currentItem.accountingAmount.toString()
                viewHolder.textTime.text = currentItem.accountingTime
                viewHolder.textClass.text = currentItem.accountingClass.firstClass + " : " + currentItem.accountingClass.secondClass
                viewHolder.textAccount.text = "账户：" + currentItem.accountingAcconut.firstClass + " : " + currentItem.accountingAcconut.secondClass
                viewHolder.textMember.text = "成员：" + if (currentItem.accountingMember == null) ""
                else currentItem.accountingMember!!.firstClass + " : " + currentItem.accountingMember!!.secondClass
                viewHolder.textProject.text = "项目：" + if (currentItem.accountingProject == null) ""
                else currentItem.accountingProject!!.firstClass + " : " + currentItem.accountingProject!!.secondClass
                viewHolder.textMerchant.text = "商家：" + if (currentItem.accountingMerchant == null) ""
                    else currentItem.accountingMerchant!!.firstClass + " : " + currentItem.accountingMerchant!!.secondClass
                viewHolder.textRemark.text = "备注：" + currentItem.accountingRemark
                viewHolder.upButton.setOnClickListener {
                    viewTypeList[position] = 0
                    notifyItemChanged(position)
                }
                viewHolder.deleteButton.setOnClickListener {
                    val roomdb = tBookDatabase.getDBInstace(context)
                    Thread{
                        roomdb.actDao().deleteAccountingData((viewHolder.textID.text as String).toInt())
                    }.start()
                }
                viewHolder.editButton.setOnClickListener {
                    val intent = Intent(context,
                        AccountingActivity::class.java)
                    intent.putExtra("id",(viewHolder.textID.text as String).toInt())
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun readData(accounting: List<Accounting>) {
        itemList = accounting
        viewTypeList = List(itemList.size,{0}) as MutableList<Int>
        notifyDataSetChanged()
    }

}