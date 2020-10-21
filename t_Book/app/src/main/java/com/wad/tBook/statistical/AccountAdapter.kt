package com.wad.tBook.statistical

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.wad.tBook.MainActivity
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
        private val accountTypeView: TextView = view.findViewById(R.id.textAccountType)
        private val accountAmountView: TextView = view.findViewById(R.id.text_amount)
        private val accountCardView: CardView = view.findViewById(R.id.ActCard)
        private val intent = Intent(view.context, StatisticalActivity::class.java)
        private val context = view.context
        @SuppressLint("SetTextI18n")
        fun bind(model: AccountFragment.TA) {
            accountTypeView.text = model.firstClass + "-" + model.secondClass
            accountAmountView.text = model.amount.toString()
            accountCardView.setOnClickListener {
                context.startActivity(intent)
            }
        }
    }
}
