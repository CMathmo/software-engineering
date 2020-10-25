package com.wad.tBook.accounting

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.MultilevelClassification
import com.wad.tBook.room.tBookDatabase
import org.jetbrains.anko.find
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [IncomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("ResourceType", "SimpleDateFormat")
class IncomeFragment : Fragment() {

    private val TAG = IncomeFragment::class.qualifiedName

    private var choose_tag :Int = 0
    private var OptionsItems_1: MutableList<String> = mutableListOf()
    private var OptionsItems_2: MutableList<MutableList<String>> = mutableListOf()
    private var view_id: Int = 0
    private var type: String ="收入"
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

        return inflater.inflate(R.layout.fragment_income, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savebutton : Button = requireView().find(R.id.save_button)
        val accounting_id = (activity as AccountingActivity).id
        InitEditText()
        if(accounting_id != -1){
            InitView(accounting_id)
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

            val account_list = requireView().find<EditText>(R.id.account_editText).text.toString().split("->")
            val account = if(account_list.size == 1) null
            else  MultilevelClassification(firstClass = account_list[0],secondClass = account_list[1])
            if(account == null) {
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
                accountingAcconut = account,
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
        if(accounting.accountingType == "收入"){
            requireView().find<EditText>(R.id.amount_editText).setText(accounting.accountingAmount.toString())
            requireView().find<EditText>(R.id.class_editText).setText(accounting.accountingClass.firstClass + "->" + accounting.accountingClass.secondClass)
            requireView().find<EditText>(R.id.account_editText).setText(accounting.accountingAcconut.firstClass + "->" + accounting.accountingAcconut.secondClass)
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

        val account_editText = requireView().find<EditText>(R.id.account_editText)
        account_editText.inputType = InputType.TYPE_NULL
        account_editText.setFocusable(false)
        account_editText.setOnClickListener {
            if(view_id != R.id.account_editText){
                view_id = R.id.account_editText
                InitDataList(pvoptions,"账户")
            }
            pvoptions.show()
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


    fun clearALLEditText(){
        view_id = 0
        requireView().find<EditText>(R.id.class_editText).setText("")
        requireView().find<EditText>(R.id.account_editText).setText("")
        requireView().find<EditText>(R.id.date_editText).setText("")
        requireView().find<EditText>(R.id.member_editText).setText("")
        requireView().find<EditText>(R.id.project_editText).setText("")
        requireView().find<EditText>(R.id.merchant_editText).setText("")
        requireView().find<EditText>(R.id.remark_editText).setText("")
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





}