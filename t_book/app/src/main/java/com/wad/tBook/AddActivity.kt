package com.wad.tBook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.wad.tBook.room.Property
import com.wad.tBook.room.tBookDatabase
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.type.*
import org.jetbrains.anko.find

class AddActivity : AppCompatActivity() {

    private val TAG = AddActivity::class.qualifiedName

    private var choose_tag :Int = 0
    private var OptionsItems_1: MutableList<String> = mutableListOf()
    private var OptionsItems_2: MutableList<MutableList<String>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val pvoptions : OptionsPickerView<String> = OptionsPickerBuilder(this, OnOptionsSelectListener{
            options1, options2, options3, v ->
            val tx = OptionsItems_1.get(options1) + "->" + OptionsItems_2.get(options1).get(options2)
            find<EditText>(R.id.class_editText).setText(tx)
        }).build()
        val roomdb = tBookDatabase.getDBInstace(this.application)
        Thread({
            OptionsItems_1 = roomdb.proDao().getFirstClassFrom("收入","类别") as MutableList<String>
            for(item in OptionsItems_1){
                Log.d(TAG,"momo:"+item)
                val list = roomdb.proDao().getSecondClassFrom("收入","类别",item) as MutableList<String>
                OptionsItems_2.add(list)
                for(sitem in list){
                    Log.d(TAG,"momo:"+item+sitem)
                }
            }
            pvoptions.setPicker(OptionsItems_1, OptionsItems_2)
        }).start()
        find<EditText>(R.id.class_editText).inputType = InputType.TYPE_NULL
        find<EditText>(R.id.class_editText).setFocusable(false)
        find<EditText>(R.id.class_editText).setOnClickListener {
            pvoptions.show()
        }


        val savebutton : Button = find(R.id.save_button)
        savebutton.setOnClickListener {
            intent = Intent()
            intent.putExtra("type",find<RadioButton>(find<RadioGroup>(R.id.type_group).checkedRadioButtonId).text.toString())
            intent.putExtra("amount",find<EditText>(R.id.amount_editText).text.toString())
            intent.putExtra("class",find<EditText>(R.id.class_editText).text.toString())
            intent.putExtra("date",find<EditText>(R.id.date_editText).text.toString())
            intent.putExtra("member",find<EditText>(R.id.member_editText).text.toString())
            intent.putExtra("project",find<EditText>(R.id.project_editText).text.toString())
            intent.putExtra("account",find<EditText>(R.id.account_editText).text.toString())
            intent.putExtra("merchant",find<EditText>(R.id.merchant_editText).text.toString())
            intent.putExtra("remark",find<EditText>(R.id.remark_editText).text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

}