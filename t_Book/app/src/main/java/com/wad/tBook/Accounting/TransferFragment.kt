package com.wad.tBook.accounting

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.wad.tBook.R
import com.wad.tBook.edit.EditActivity
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.MultilevelClassification
import com.wad.tBook.room.tBookDatabase
import kotlinx.android.synthetic.main.fragment_income.*
import org.jetbrains.anko.find
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class TransferFragment:Fragment() {

    private val TAG = TransferFragment::class.qualifiedName

    private var choose_tag :Int = 0
    private var OptionsItems_1: MutableList<String> = mutableListOf()
    private var OptionsItems_2: MutableList<MutableList<String>> = mutableListOf()
    private var view_id: Int = 0
    private var type: String ="转账"
    private val application by lazy {
        activity?.application
    }
    private val pvoptions: OptionsPickerView<String> by lazy{
        OptionsPickerBuilder(activity, OnOptionsSelectListener{
                options1, options2, options3, v ->
            val tx = OptionsItems_1.get(options1) + "->" + OptionsItems_2.get(options1).get(options2)
            view?.find<EditText>(view_id)?.setText(tx)
        }).build<String>()
    }

    private val pvTime: TimePickerView by lazy{
        TimePickerBuilder(activity, OnTimeSelectListener{
                date,v ->
            view?.find<EditText>(R.id.date_editText)?.setText(SimpleDateFormat("yyyy年MM月dd日").format(date))
        })
            .setCancelText("取消")//取消按钮文字
            .setSubmitText("确定")//确认按钮文字
            .setLabel("年","月","日","","","")
            .build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_transfer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savebutton : Button = requireView().find(R.id.saveButton)
        InitEditText()
        val accounting_id = (activity as AccountingActivity).id
        InitEditText()
        if(accounting_id != -1){
            InitView(accounting_id)
        }
        class_fab.setOnClickListener {
            jumpEditActivity("类别")
        }
        account_fab.setOnClickListener {
            jumpEditActivity("账户")
        }
        member_fab.setOnClickListener {
            jumpEditActivity("成员")
        }
        project_fab.setOnClickListener {
            jumpEditActivity("项目")
        }
        merchant_fab.setOnClickListener {
            jumpEditActivity("商家")
        }
        savebutton.setOnClickListener {

            val amount = if(requireView().find<EditText>(R.id.amount_editText).text.toString()=="") 0.0
            else requireView().find<EditText>(R.id.amount_editText).text.toString().toDouble()

            val class_list = requireView().find<EditText>(R.id.class_editText).text.toString().split("->")
            val item_class = if(class_list.size == 1) null
            else  MultilevelClassification(firstClass = class_list[0],secondClass = class_list[1])
            if(item_class == null){
                Toast.makeText(activity,"class must be writen", Toast.LENGTH_SHORT).show()
                Log.d(TAG,"momo:class must be writen")
                return@setOnClickListener
            }

            val account_list_out = requireView().find<EditText>(R.id.account_editText_1).text.toString().split("->")
            val account_out = if(account_list_out.size == 1) null
            else  MultilevelClassification(firstClass = account_list_out[0],secondClass = account_list_out[1])
            if(account_out == null) {
                Toast.makeText(activity,"account must be writen", Toast.LENGTH_SHORT).show()
                Log.d(TAG,"momo:account must be writen")
                return@setOnClickListener
            }

            val account_list_in = requireView().find<EditText>(R.id.account_editText_2).text.toString().split("->")
            val account_in = if(account_list_in.size == 1) null
            else  MultilevelClassification(firstClass = account_list_in[0],secondClass = account_list_in[1])
            if(account_in == null) {
                Toast.makeText(activity,"account must be writen", Toast.LENGTH_SHORT).show()
                Log.d(TAG,"momo:account must be writen")
                return@setOnClickListener
            }

            val date = if(requireView().find<EditText>(R.id.date_editText).text.toString()=="") getNow()
            else requireView().find<EditText>(R.id.date_editText).text.toString()

            val member_list = requireView().find<EditText>(R.id.member_editText).text.toString().split("->")
            val member = if(member_list.size == 1) null
            else  MultilevelClassification(firstClass = member_list[0],secondClass = member_list[1])

            val project_list = requireView().find<EditText>(R.id.project_editText).text.toString().split("->")
            val project = if(project_list.size == 1) null
            else  MultilevelClassification(firstClass = project_list[0],secondClass = project_list[1])

            val merchant_list = requireView().find<EditText>(R.id.merchant_editText).text.toString().split("->")
            val merchant = if(merchant_list.size == 1) null
            else  MultilevelClassification(firstClass = merchant_list[0],secondClass = merchant_list[1])

            val remark = requireView().find<EditText>(R.id.remark_editText).text.toString()


            val accounting = Accounting(
                accountingId = if (accounting_id != -1)accounting_id else 0,
                accountingType = type,
                accountingAmount = amount,
                accountingClass = item_class,
                accountingAcconut = account_out,
                accountingAcconut_2 = account_in,
                accountingTime = date,
                accountingMember = member,
                accountingProject = project,
                accountingMerchant = merchant,
                accountingRemark = remark
            )
            val roomdb = tBookDatabase.getDBInstace(requireActivity())
            Thread{
                if(accounting_id != -1){
                    roomdb.actDao().deleteAccountingData(accounting_id)
                }
                Log.d(TAG,"momo:"+accounting_id.toString())
                roomdb.actDao().addAccountingData(accounting)
            }.start()
            activity?.finish()
        }
    }

    fun InitView(id:Int){
        val accounting = tBookDatabase.getDBInstace(requireActivity()).actDao().getAccountingData(id)[0]
        if (accounting.accountingType == "转账"){
            requireView().find<EditText>(R.id.amount_editText).setText(accounting.accountingAmount.toString())
            requireView().find<EditText>(R.id.class_editText).setText(accounting.accountingClass.firstClass + "->" + accounting.accountingClass.secondClass)
            requireView().find<EditText>(R.id.account_editText_1).setText(accounting.accountingAcconut.firstClass + "->" + accounting.accountingAcconut.secondClass)
            requireView().find<EditText>(R.id.account_editText_2).setText(accounting.accountingAcconut_2!!.firstClass + "->" + accounting.accountingAcconut_2!!.secondClass)
            requireView().find<EditText>(R.id.date_editText).setText(accounting.accountingTime)
            requireView().find<EditText>(R.id.member_editText).setText(if(accounting.accountingMember == null)""
            else accounting.accountingMember!!.firstClass + "->" + accounting.accountingMember!!.secondClass)
            requireView().find<EditText>(R.id.project_editText).setText(if(accounting.accountingProject == null)""
            else accounting.accountingProject!!.firstClass + "->" + accounting.accountingProject!!.secondClass)
            requireView().find<EditText>(R.id.merchant_editText).setText(if(accounting.accountingMerchant == null)""
            else accounting.accountingMerchant!!.firstClass + "->" + accounting.accountingMerchant!!.secondClass)
            requireView().find<EditText>(R.id.remark_editText).setText(accounting.accountingRemark)
        }
    }

    fun InitDataList(pvoptions: OptionsPickerView<String>, item:String) {
        val roomdb = tBookDatabase.getDBInstace(requireActivity())
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

        requireView().find<EditText>(R.id.amount_editText).setOnFocusChangeListener { view, b ->
            if(!b){
                val inputMethodManager: InputMethodManager = application?.getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    requireView().find<EditText>(R.id.amount_editText).getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }

        requireView().find<EditText>(R.id.remark_editText).setOnFocusChangeListener { view, b ->
            if(!b){
                val inputMethodManager: InputMethodManager = application?.getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    requireView().find<EditText>(R.id.remark_editText).getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }

        val class_editText = requireView().find<EditText>(R.id.class_editText)
        class_editText.inputType = InputType.TYPE_NULL
        class_editText.setFocusable(false)
        class_editText.setOnClickListener {
            if(view_id != R.id.class_editText){
                view_id = R.id.class_editText
                InitDataList(pvoptions,"类别")
            }
            pvoptions.show()
        }

        val account_editText_out = requireView().find<EditText>(R.id.account_editText_1)
        account_editText_out.inputType = InputType.TYPE_NULL
        account_editText_out.setFocusable(false)
        account_editText_out.setOnClickListener {
            if(view_id != R.id.account_editText_1){
                view_id = R.id.account_editText_1
                InitDataList(pvoptions,"账户")
            }
            pvoptions.show()
        }


        val account_editText_in = requireView().find<EditText>(R.id.account_editText_2)
        account_editText_in.inputType = InputType.TYPE_NULL
        account_editText_in.setFocusable(false)
        account_editText_in.setOnClickListener {
            if(view_id != R.id.account_editText_2){
                view_id = R.id.account_editText_2
                InitDataList(pvoptions,"账户")
            }
            pvoptions.show()
        }

        requireView().find<Button>(R.id.change_button).setOnClickListener {
            val temp_text = account_editText_in.text
            account_editText_in.text = account_editText_out.text
            account_editText_out.text = temp_text
        }


        val date_editText = requireView().find<EditText>(R.id.date_editText)
        date_editText.inputType = InputType.TYPE_NULL
        date_editText.setFocusable(false)
        date_editText.setOnClickListener {
            pvTime.show()
        }

        val member_editText = requireView().find<EditText>(R.id.member_editText)
        member_editText.inputType = InputType.TYPE_NULL
        member_editText.setFocusable(false)
        member_editText.setOnClickListener {
            if(view_id != R.id.member_editText){
                view_id = R.id.member_editText
                InitDataList(pvoptions,"成员")
            }
            pvoptions.show()
        }

        val project_editText = requireView().find<EditText>(R.id.project_editText)
        project_editText.inputType = InputType.TYPE_NULL
        project_editText.setFocusable(false)
        project_editText.setOnClickListener {
            if(view_id != R.id.project_editText){
                view_id = R.id.project_editText
                InitDataList(pvoptions,"项目")
            }
            pvoptions.show()
        }

        val merchant_editText = requireView().find<EditText>(R.id.merchant_editText)
        merchant_editText.inputType = InputType.TYPE_NULL
        merchant_editText.setFocusable(false)
        merchant_editText.setOnClickListener {
            if(view_id != R.id.merchant_editText){
                view_id = R.id.merchant_editText
                InitDataList(pvoptions,"商家")
            }
            pvoptions.show()
        }

    }


    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun getNow(): String {
        if (android.os.Build.VERSION.SDK_INT >= 24){
            return SimpleDateFormat("yyyy年MM月dd日").format(Date())
        }else{
            return ""
        }
    }

    fun jumpEditActivity(item:String){
        val intent = Intent(context,
            EditActivity::class.java)
        intent.putExtra("type",type)
        intent.putExtra("item",item)
        startActivity(intent)
    }
}