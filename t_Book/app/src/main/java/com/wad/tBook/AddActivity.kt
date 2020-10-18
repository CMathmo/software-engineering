package com.wad.tBook

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.*
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.MultilevelClassification
import com.wad.tBook.room.tBookDatabase
import org.jetbrains.anko.find
import org.jetbrains.anko.timePicker
import java.util.*

class AddActivity : AppCompatActivity() {

    private val TAG = AddActivity::class.qualifiedName

    private var choose_tag :Int = 0
    private var OptionsItems_1: MutableList<String> = mutableListOf()
    private var OptionsItems_2: MutableList<MutableList<String>> = mutableListOf()
    private var id: Int = 0
    private var type: String ="收入"
    private val pvoptions:OptionsPickerView<String> by lazy{
        OptionsPickerBuilder(this, OnOptionsSelectListener{
                options1, options2, options3, v ->
            val tx = OptionsItems_1.get(options1) + "->" + OptionsItems_2.get(options1).get(options2)
            find<EditText>(id).setText(tx)
        }).build<String>()
    }

    private val pvTime: TimePickerView by lazy{
        TimePickerBuilder(this, OnTimeSelectListener{
                date,v ->
            find<EditText>(R.id.date_editText).setText(date.toString())
        }).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val savebutton : Button = find(R.id.save_button)
        InitEditText()
        InitTypeButton()

        savebutton.setOnClickListener {

            val amount = if(find<EditText>(R.id.amount_editText).text.toString()=="") 0.0
            else find<EditText>(R.id.amount_editText).text.toString().toDouble()

            val class_list = find<EditText>(R.id.class_editText).text.toString().split("->")
            val item_class = if(class_list.size == 1) null
            else  MultilevelClassification(firstClass = class_list[0],secondClass = class_list[1])
            if(item_class == null){
                Toast.makeText(this,"class must be writen",Toast.LENGTH_SHORT).show()
                Log.d(TAG,"momo:class must be writen")
                return@setOnClickListener
            }

            val account_list = find<EditText>(R.id.account_editText).text.toString().split("->")
            val account = if(account_list.size == 1) null
            else  MultilevelClassification(firstClass = account_list[0],secondClass = account_list[1])
            if(account == null) {
                Toast.makeText(this,"account must be writen",Toast.LENGTH_SHORT).show()
                Log.d(TAG,"momo:account must be writen")
                return@setOnClickListener
            }

            val date = if(find<EditText>(R.id.date_editText).text.toString()=="") getNow()
            else find<EditText>(R.id.date_editText).text.toString()

            val member_list = find<EditText>(R.id.member_editText).text.toString().split("->")
            val member = if(member_list.size == 1) null
            else  MultilevelClassification(firstClass = member_list[0],secondClass = member_list[1])

            val project_list = find<EditText>(R.id.project_editText).text.toString().split("->")
            val project = if(project_list.size == 1) null
            else  MultilevelClassification(firstClass = project_list[0],secondClass = project_list[1])

            val merchant_list = find<EditText>(R.id.merchant_editText).text.toString().split("->")
            val merchant = if(merchant_list.size == 1) null
            else  MultilevelClassification(firstClass = merchant_list[0],secondClass = merchant_list[1])

            val remark = if(find<EditText>(R.id.remark_editText).text.toString()=="") null
            else find<EditText>(R.id.remark_editText).text.toString()


            val accounting = Accounting(
                accountingId = 0,
                accountingType = type,
                accountingAmount = amount,
                accountingClass = item_class,
                accountingAcconut = account,
                accountingTime = date,
                accountingMember = member,
                accountingProject = project,
                accountingMerchant = merchant,
                accountingRemark = remark
            )
            val roomdb = tBookDatabase.getDBInstace(this.application)
            Thread{
                roomdb.actDao().addAccountingData(accounting)
            }.start()
            finish()
        }
    }

    fun InitDataList(pvoptions: OptionsPickerView<String>,item:String) {
        val roomdb = tBookDatabase.getDBInstace(this.application)
        OptionsItems_1 = roomdb.proDao().getFirstClassFrom(type, item) as MutableList<String>
        OptionsItems_2 = mutableListOf()
        for (first_class in OptionsItems_1) {
            Log.d(TAG, "momo:" + first_class)
            val list = roomdb.proDao()
                .getSecondClassFrom(type, item, first_class) as MutableList<String>
            OptionsItems_2.add(list)
            for (second_class in list) {
                Log.d(TAG, "momo:" + first_class + second_class)
            }
        }
        pvoptions.setPicker(OptionsItems_1, OptionsItems_2)
    }

    fun InitEditText(){
        val class_editText = find<EditText>(R.id.class_editText)
        class_editText.inputType = InputType.TYPE_NULL
        class_editText.setFocusable(false)
        class_editText.setOnClickListener {
            if(id != R.id.class_editText){
                id = R.id.class_editText
                InitDataList(pvoptions,"类别")
            }
            pvoptions.show()
        }

        val account_editText = find<EditText>(R.id.account_editText)
        account_editText.inputType = InputType.TYPE_NULL
        account_editText.setFocusable(false)
        account_editText.setOnClickListener {
            if(id != R.id.account_editText){
                id = R.id.account_editText
                InitDataList(pvoptions,"账户")
            }
            pvoptions.show()
        }

        val date_editText = find<EditText>(R.id.date_editText)
        date_editText.inputType = InputType.TYPE_NULL
        date_editText.setFocusable(false)
        date_editText.setOnClickListener {
            pvTime.show()
        }

        val member_editText = find<EditText>(R.id.member_editText)
        member_editText.inputType = InputType.TYPE_NULL
        member_editText.setFocusable(false)
        member_editText.setOnClickListener {
            if(id != R.id.member_editText){
                id = R.id.member_editText
                InitDataList(pvoptions,"成员")
            }
            pvoptions.show()
        }

        val project_editText = find<EditText>(R.id.project_editText)
        project_editText.inputType = InputType.TYPE_NULL
        project_editText.setFocusable(false)
        project_editText.setOnClickListener {
            if(id != R.id.project_editText){
                id = R.id.project_editText
                InitDataList(pvoptions,"项目")
            }
            pvoptions.show()
        }

        val merchant_editText = find<EditText>(R.id.merchant_editText)
        merchant_editText.inputType = InputType.TYPE_NULL
        merchant_editText.setFocusable(false)
        merchant_editText.setOnClickListener {
            if(id != R.id.merchant_editText){
                id = R.id.merchant_editText
                InitDataList(pvoptions,"商家")
            }
            pvoptions.show()
        }

    }

    fun InitTypeButton(){
        find<RadioButton>(R.id.income_radioButton).setOnClickListener{
            type = "收入"
            clearALLEditText()
        }
        find<RadioButton>(R.id.expenditure_radioButton).setOnClickListener{
            type = "支出"
            clearALLEditText()
        }
        find<RadioButton>(R.id.transfer_radioButton).setOnClickListener{
            type = "转账"
            clearALLEditText()
        }


    }

    fun clearALLEditText(){
        id = 0
        find<EditText>(R.id.class_editText).setText("")
        find<EditText>(R.id.account_editText).setText("")
        find<EditText>(R.id.date_editText).setText("")
        find<EditText>(R.id.member_editText).setText("")
        find<EditText>(R.id.project_editText).setText("")
        find<EditText>(R.id.merchant_editText).setText("")
        find<EditText>(R.id.remark_editText).setText("")
    }

    fun getNow(): String {
        if (android.os.Build.VERSION.SDK_INT >= 24){
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date())
        }else{
            var tms = Calendar.getInstance()
            return tms.get(Calendar.YEAR).toString() + "-" + tms.get(Calendar.MONTH).toString() + "-" + tms.get(Calendar.DAY_OF_MONTH).toString() + " " + tms.get(Calendar.HOUR_OF_DAY).toString() + ":" + tms.get(Calendar.MINUTE).toString() +":" + tms.get(Calendar.SECOND).toString() +"." + tms.get(Calendar.MILLISECOND).toString()
        }
    }
}