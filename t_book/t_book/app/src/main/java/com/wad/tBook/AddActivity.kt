package com.wad.tBook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.find

class AddActivity : AppCompatActivity() {

    private val TAG = AddActivity::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val savebutton : Button = find(R.id.save_button)
        savebutton.setOnClickListener {
            intent = Intent()
            intent.putExtra("money",find<EditText>(R.id.money_editText).text.toString())
            intent.putExtra("type",find<EditText>(R.id.type_editText).text.toString())
            intent.putExtra("date",find<EditText>(R.id.date_editText).text.toString())
            intent.putExtra("remark",find<EditText>(R.id.remark_editText).text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}