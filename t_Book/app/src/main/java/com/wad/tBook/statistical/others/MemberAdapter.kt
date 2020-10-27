package com.wad.tBook.statistical.others

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wad.tBook.R
import com.wad.tBook.room.Accounting

class MemberAdapter(
    context: Context,
    var memberList: MutableList<OtherStatisticalRepository.TA>
): RecyclerView.Adapter<MemberAdapter.MemberViewHolder>(){

    var accountingList : List<Accounting> = emptyList<Accounting>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_other_view,parent,false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(memberList[position])
    }

    override fun getItemCount(): Int {
        return memberList.size
    }

    class MemberViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val textType : TextView = view.findViewById(R.id.property_type)
        private val textAmount : TextView = view.findViewById(R.id.properth_amout)
        @SuppressLint("SetTextI18n")
        fun bind(model: OtherStatisticalRepository.TA) {
            textType.text = model.firstClass + "-" + model.secondClass
            textAmount.text = "流入：" + model.inflowAmount.toString() + "  " +
                    "流出：" + model.outflowAmount.toString()
        }
    }

    fun readData(memberList: MutableList<OtherStatisticalRepository.TA>) {
        this.memberList = memberList
        notifyDataSetChanged()
    }

}