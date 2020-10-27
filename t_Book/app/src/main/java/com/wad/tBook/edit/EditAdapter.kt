package com.wad.tBook.edit

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wad.tBook.R
import org.jetbrains.anko.find
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import com.melnykov.fab.FloatingActionButton
import com.wad.tBook.MainActivity
import com.wad.tBook.room.Property
import com.wad.tBook.room.tBookDatabase


class EditAdapter (
    context: Context
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val TAG = context::class.qualifiedName
    val context : Context = context
    var itemList : MutableList<String> = mutableListOf()
    var viewTypeList : MutableList<Int> = mutableListOf()
    val dao = tBookDatabase.getDBInstace(context).proDao()
    val type = (context as EditActivity).type
    val item = (context as EditActivity).item

    class EditViewHolder(view:View):RecyclerView.ViewHolder(view){
        val firstText : TextView = view.find<TextView>(R.id.classTextView)
        val deleteButton = view.find<ImageButton>(R.id.edit_delete_button)
    }

    class EditViewHolder_detail(view:View):RecyclerView.ViewHolder(view){
        val firstText = view.find<TextView>(R.id.classTextView)
        val deleteButton = view.find<ImageButton>(R.id.edit_delete_button)
        val secondRecyclerView = view.find<RecyclerView>(R.id.second_class_recyclerview)
        val edit_second_adapter = EditSecondAdapter(view.context)
        val view = view
        init {
            secondRecyclerView.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
            secondRecyclerView.adapter = edit_second_adapter
        }
    }

    class EditAddViewHolder(view:View):RecyclerView.ViewHolder(view){
        val addButton = view.find<Button>(R.id.edit_add_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder{
        return when(viewType){
            0 -> EditViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.single_class_card,parent, false)
            )
            1 -> EditViewHolder_detail(
                LayoutInflater.from(parent.context).inflate(R.layout.mult_class_card,parent, false)
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
                val firstClass = itemList.get(position)
                val viewHolder = holder as EditViewHolder
                viewHolder.firstText.text = firstClass
                viewHolder.firstText.setOnClickListener {
                    viewTypeList[position] = 1
                    notifyItemChanged(position)
                }
                viewHolder.deleteButton.setOnClickListener {
                    showTipsDialog(firstClass)
                }
            }
            1 ->{
                val firstClass = itemList.get(position)
                val viewHolder = holder as EditViewHolder_detail
                viewHolder.firstText.text = firstClass
                viewHolder.firstText.setOnClickListener {
                    viewTypeList[position] = 0
                    notifyItemChanged(position)
                }
                viewHolder.edit_second_adapter.readData(dao.getSecondClassFrom(type,item,firstClass))
                viewHolder.edit_second_adapter.setDateOfParent(type,item,firstClass)
                viewHolder.secondRecyclerView.setOnTouchListener { v, event ->
                    when(event.action){
                        //当用户按下的时候，我们告诉父组件，不要拦截我的事件（这个时候子组件是可以正常响应事件的），拿起之后就会告诉父组件可以阻止。
                        MotionEvent.ACTION_DOWN,MotionEvent.ACTION_MOVE -> v.parent.requestDisallowInterceptTouchEvent(true)
                        MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
                    }
                    false}
                viewHolder.deleteButton.setOnClickListener {
                    showTipsDialog(firstClass)
                }
            }
            2 ->{
                val viewHolder = holder as EditAddViewHolder
                viewHolder.addButton.setOnClickListener {
                    showNormalDialog()
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun readData(firstClassList: List<String>) {
        itemList = firstClassList as MutableList
        val size = itemList.size
        if(viewTypeList.size != size + 1){
            Log.d(TAG,"momo:不同了 "+viewTypeList.size+" "+size)
            viewTypeList = List(itemList.size,{0}) as MutableList<Int>
            viewTypeList.add(2)
        }
        itemList.add("")
        notifyDataSetChanged()
    }


    fun showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        val normalDialog = AlertDialog.Builder(context)
        normalDialog.setMessage("请输入你要增添的类别")
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.edit_first_class_dialog,null)
        normalDialog.setView(dialogView)
        normalDialog.setPositiveButton("确定",DialogInterface.OnClickListener { dialogInterface, i ->
            val firstClass = dialogView.find<EditText>(R.id.first_dialog_edittext).text.toString()
            val secondClass = dialogView.find<EditText>(R.id.second_dialog_edittext).text.toString()
            if (item == "类别"){
                dao.addPropertyData(Property(0,type,item,firstClass,secondClass))
            }else{
                dao.addPropertyData(Property(0,"收入",item,firstClass,secondClass))
                dao.addPropertyData(Property(0,"支出",item,firstClass,secondClass))
                dao.addPropertyData(Property(0,"转账",item,firstClass,secondClass))
            }
        })
        normalDialog.setNegativeButton("关闭",DialogInterface.OnClickListener { dialogInterface, i ->

        })
        // 显示
        normalDialog.show();
    }

    fun showTipsDialog(firstClass:String){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        val normalDialog = AlertDialog.Builder(context)
        normalDialog.setMessage("确定要删除一级类别："+firstClass+" ?")
        normalDialog.setPositiveButton("确定",DialogInterface.OnClickListener { dialogInterface, i ->
            dao.deleteAllPropertyFirstClassIs(type,item,firstClass)
        })
        normalDialog.setNegativeButton("关闭",DialogInterface.OnClickListener { dialogInterface, i ->

        })
        // 显示
        normalDialog.show();
    }
}