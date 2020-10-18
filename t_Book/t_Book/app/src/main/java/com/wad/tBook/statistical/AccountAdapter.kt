package com.wad.tBook.statistical

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import java.util.ArrayList

class AccountAdapter(
    accountFragment: AccountFragment,
    listAccountView: Int,
    accountList: ArrayList<Accounting>
) : BaseAdapter() {

    var context: Context?=null
    var itemList = emptyList<Accounting>()

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var viewHolder:ViewHolder?=null
        var view:View?=null

        if (convertView == null){
            view = View.inflate(context, R.layout.list_account_view,null)
            viewHolder = ViewHolder()
            viewHolder.accountView = view.findViewById(R.id.textAccounttype)
            viewHolder.amountView = view.findViewById(R.id.textAmount)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view!!.tag as ViewHolder
        }

        viewHolder.accountView!!.text = itemList!![position].accountingAcconut.toString()
        viewHolder.amountView!!.text = itemList!![position].accountingAmount.toString()

        return view
    }
    inner class ViewHolder{
        var accountView:TextView ?= null
        var amountView:TextView ?= null
    }

}