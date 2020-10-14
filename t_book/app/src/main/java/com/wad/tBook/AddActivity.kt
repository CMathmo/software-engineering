package com.wad.tBook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.type.*
import org.jetbrains.anko.find

class AddActivity : AppCompatActivity() {

    private val TAG = AddActivity::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
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