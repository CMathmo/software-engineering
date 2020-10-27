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
import com.wad.tBook.toMoneyFormatted

class ClassAdapter (
    context: Context,
    var classList: MutableList<OtherStatisticalRepository.TA_fc>
): RecyclerView.Adapter<ClassAdapter.ClassViewHolder>(){

    var accountingList : List<Accounting> = emptyList<Accounting>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_other_view,parent,false)
        return ClassViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bind(classList[position])
    }

    override fun getItemCount(): Int {
        return classList.size
    }

    class ClassViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val textType : TextView = view.findViewById(R.id.property_type)
        private val textAmount : TextView = view.findViewById(R.id.properth_amout)
        @SuppressLint("SetTextI18n")
        fun bind(model: OtherStatisticalRepository.TA_fc) {
            textType.text = model.firstClass
            textAmount.text = "金额：" + (model.inflowAmount - model.outflowAmount).toMoneyFormatted()
        }
    }

    fun readData(classList: MutableList<OtherStatisticalRepository.TA_fc>) {
        this.classList = classList
        notifyDataSetChanged()
    }

}