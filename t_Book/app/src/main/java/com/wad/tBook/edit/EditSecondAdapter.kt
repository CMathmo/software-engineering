package com.wad.tBook.edit

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wad.tBook.accounting.AccountingActivity
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.Property
import com.wad.tBook.room.tBookDatabase
import org.jetbrains.anko.find


class EditSecondAdapter (
    context: Context
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val TAG = context::class.qualifiedName
    val context : Context = context
    var itemList : MutableList<String> = mutableListOf()
    var viewTypeList : MutableList<Int> = mutableListOf()
    val dao = tBookDatabase.getDBInstace(context).proDao()
    var type : String = ""
    var item : String = ""
    var firstClass : String = ""

    class EditViewHolder(view:View):RecyclerView.ViewHolder(view){
        val secondText : TextView = view.find<TextView>(R.id.classTextView)
        val deleteButton = view.find<ImageButton>(R.id.edit_delete_button)
        val singleCard = view.find<CardView>(R.id.single_card)
    }

    class EditAddViewHolder(view:View):RecyclerView.ViewHolder(view){
        val addButton = view.find<Button>(R.id.edit_add_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder{
        return when(viewType){
            0 -> EditViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.single_class_card,parent, false)
            )
            2 -> EditAddViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.add_button_card,parent, false)
            )
            else -> null as RecyclerView.ViewHolder
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypeList[position]
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            0 -> {
                val secondClass = itemList.get(position)
                val viewHolder = holder as EditViewHolder
                viewHolder.secondText.text = secondClass
                viewHolder.singleCard.setBackgroundColor(Color.parseColor("#1E88E5"))
                viewHolder.secondText.setTextColor(Color.parseColor("#FFFFFF"))
                viewHolder.deleteButton.setOnClickListener {
                    dao.deleteAllPropertyClassIs(type,item,firstClass,secondClass)
                }
            }
            2 ->{
                val viewHolder = holder as EditAddViewHolder
                viewHolder.addButton.setBackgroundColor(Color.parseColor("#1E88E5"))
                viewHolder.addButton.setTextColor(Color.parseColor("#FFFFFF"))
                viewHolder.addButton.setOnClickListener {
                    showNormalDialog()
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun readData(secondClassList: List<String>) {
        itemList = secondClassList as MutableList
        viewTypeList = List(itemList.size,{0}) as MutableList<Int>
        itemList.add("")
        viewTypeList.add(2)
        Log.d(TAG,"momo:ESA"+itemList)
        notifyDataSetChanged()
    }

    fun setDateOfParent(type:String,item:String,firstClass:String){
        this.type = type
        this.item = item
        this.firstClass = firstClass
    }

    fun showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        Log.d(TAG,"momoESA:"+firstClass)
        val normalDialog = AlertDialog.Builder(context)
        normalDialog.setMessage("请输入你要增添的类别")
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.edit_second_class_dialog,null)
        normalDialog.setView(dialogView)
        val firstClassText = dialogView.find<TextView>(R.id.first_dialog_textview)
        firstClassText.setText(firstClass)
        normalDialog.setPositiveButton("确定", null)
        normalDialog.setNegativeButton("关闭", DialogInterface.OnClickListener { dialogInterface, i ->})
        val dialog = normalDialog.create()
        // 显示
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val secondClass = dialogView.find<EditText>(R.id.second_dialog_edittext).text.toString()
            if(secondClass == ""){
                Toast.makeText(context, "请输入条目名称", Toast.LENGTH_SHORT).show()
            }else if(dao.checkClass(type,item,firstClass,secondClass) == 0){
                if (item == "类别"){
                    dao.addPropertyData(Property(0,type,item,firstClass,secondClass))
                }else {
                    dao.addPropertyData(Property(0, "收入", item, firstClass, secondClass))
                    dao.addPropertyData(Property(0, "支出", item, firstClass, secondClass))
                    dao.addPropertyData(Property(0, "转账", item, firstClass, secondClass))
                }
                dialog.dismiss()
            }else {
                Toast.makeText(context, "该条目已存在", Toast.LENGTH_SHORT).show()
            }
        }
    }
}