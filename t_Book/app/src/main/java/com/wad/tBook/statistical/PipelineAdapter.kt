package com.wad.tBook.statistical

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wad.tBook.AddActivity
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase
import java.util.ArrayList


class PipelineAdapter (accountList: ArrayList<Accounting>, context: Context):
    RecyclerView.Adapter<PipelineAdapter.PipelineViewHolder>(){

    var itemList = emptyList<Accounting>()
    var context: Context? = context

    class PipelineViewHolder(view:View):RecyclerView.ViewHolder(view){
        val textID : TextView = view.findViewById(R.id.textID)
        val textAccountType : TextView = view.findViewById(R.id.text_account_type)
        val textAmount : TextView = view.findViewById(R.id.text_amount)
        val textTime : TextView = view.findViewById(R.id.textTime)
        val textMerchant : TextView = view.findViewById(R.id.textMerchant)
        val textClass : TextView = view.findViewById(R.id.textClass)
        val cardEdit : CardView = view.findViewById(R.id.pipeline_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PipelineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_pipeline_view,null)
        val roomdb = tBookDatabase.getDBInstace(context!!)
        Thread{
            itemList = roomdb.actDao().getAllAccountingData()
        }.start()
        return PipelineViewHolder(view)
    }

    override fun onBindViewHolder(holder: PipelineViewHolder, position: Int) {
        val currentItem = itemList.get(position)
        holder.textID.text = currentItem.accountingId.toString()
        holder.textAccountType.text = currentItem.accountingAcconut.toString()
        holder.textAmount.text = currentItem.accountingAmount.toString()
        holder.textTime.text = currentItem.accountingTime.toString()
        holder.textMerchant.text = currentItem.accountingMerchant.toString()
        holder.textClass.text = currentItem.accountingClass.toString()

        holder.cardEdit.setOnClickListener {
            val intent = Intent(context,AddActivity::class.java)
            intent.putExtra("id",currentItem.accountingId)
            intent.putExtra("account_type",currentItem.accountingType)
            intent.putExtra("amount",currentItem.accountingAmount)
            intent.putExtra("time",currentItem.accountingTime)
            intent.putExtra("merchant",currentItem.accountingMerchant.toString())
            intent.putExtra("class",currentItem.accountingClass.toString())

            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}