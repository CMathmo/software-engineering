package com.example.roomlearning

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomlearning.room.Accounting

class CustomAdapter(var context: Context) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    var itemList = emptyList<Accounting>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMember : TextView ?= itemView.findViewById(R.id.txtMember)
        val txtType : TextView ?= itemView.findViewById(R.id.txtType)
        val txtMerchant : TextView ?= itemView.findViewById(R.id.txtMerchant)
        val txtTime : TextView ?= itemView.findViewById(R.id.txtTime)
        val txtNumber : TextView ?= itemView.findViewById(R.id.txtNumber)
        val txtId : TextView ?= itemView.findViewById(R.id.txtId)
        val imgEdit : CardView ?= itemView.findViewById(R.id.cardViewUpdate)
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_recycler, null)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList.get(position)
        holder.txtId?.text = currentItem.accountingId.toString()
        holder.txtMember?.text = currentItem.accountingMember
        holder.txtType?.text = currentItem.accountingType
        holder.txtMerchant?.text = currentItem.accountingMerchant
        holder.txtTime?.text = currentItem.accountingTime
        holder.txtNumber?.text = currentItem.accountingNumber

        holder.imgEdit?.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)

            intent.putExtra("id",currentItem.accountingId.toString())
            intent.putExtra("name",currentItem.accountingMember)
            intent.putExtra("type",currentItem.accountingType)
            intent.putExtra("merchant",currentItem.accountingMerchant)
            intent.putExtra("time",currentItem.accountingTime)

            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun readData(accounting: List<Accounting>) {
        this.itemList = accounting
        notifyDataSetChanged()
    }
}