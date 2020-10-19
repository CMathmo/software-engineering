package com.wad.tBook.statistical

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wad.tBook.R


class AccountAdapter(
    private val values: List<AccountFragment.TA>
) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_account_view, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val accountTypeView: TextView = view.findViewById(R.id.text_account_type)
        val accountAmountView: TextView = view.findViewById(R.id.text_amount)

        @SuppressLint("SetTextI18n")
        fun bind(model : AccountFragment.TA) {
            accountTypeView.text = model.firstClass + "-" + model.secondClass
            accountAmountView.text = model.amount.toString()
        }
    }
}
