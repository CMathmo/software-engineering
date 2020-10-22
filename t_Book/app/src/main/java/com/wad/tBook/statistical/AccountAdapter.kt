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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.wad.tBook.R



class AccountAdapter(
    private val values: List<AccountFragment.TA>
) : Adapter<AccountAdapter.MyViewHolder>() {

    var mOnRecyclerViewItemClick: OnRecyclerViewItemClick<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_account_view, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(values[position])
        var context = holder.itemView.context
        // item点击事件的处理
        holder.itemView.setOnClickListener {
            mOnRecyclerViewItemClick?.OnItemClick(holder.itemView, position)
        }
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val accountTypeFView: TextView = itemView.findViewById(R.id.text_account_type)
        private val accountTypeSView: TextView = itemView.findViewById(R.id.text_account_type2)
        private val accountAmountView: TextView = itemView.findViewById(R.id.text_amount)
//        private val accountCardView: CardView = itemView.findViewById(R.id.ActCard)
//        private val intent = Intent(itemView.context, StatisticalActivity::class.java)

        @SuppressLint("SetTextI18n")
        fun bind(model: AccountFragment.TA) {
            accountTypeFView.text = model.firstClass
            accountTypeSView.text = model.secondClass
            accountAmountView.text = model.amount.toString()
            accountCardView.setOnClickListener {
                context.startActivity(intent)
            }
        }
    }

    public interface OnRecyclerViewItemClick<T> {
        fun OnItemClick(view: View?, position: Int)
    }

    override fun getItemCount(): Int = values.size
}

