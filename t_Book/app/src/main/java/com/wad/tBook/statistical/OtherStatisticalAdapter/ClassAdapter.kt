package com.wad.tBook.statistical.OtherStatisticalAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import com.wad.tBook.statistical.OtherStatisticalRepository

class ClassAdapter (
    context: Context,
    var classList: MutableList<OtherStatisticalRepository.TA>
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
        fun bind(model: OtherStatisticalRepository.TA) {
            textType.text = model.firstClass + "-" + model.secondClass
            textAmount.text = "流入：" + model.inflowAmount.toString() + "  " +
                    "流出：" + model.outflowAmount.toString()
        }
    }

    fun readData(classList: MutableList<OtherStatisticalRepository.TA>) {
        this.classList = classList
        notifyDataSetChanged()
    }

}