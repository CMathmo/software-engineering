package com.wad.tBook.setting

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wad.tBook.MainActivity
import com.wad.tBook.MyApplication
import com.wad.tBook.R
import kotlinx.android.synthetic.main.activity_person_msg.*
import org.jetbrains.anko.find

class PersonMsgActivity : AppCompatActivity(){

    private val pref : SharedPreferences = MyApplication.context.getSharedPreferences("MyPref", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_msg)
    }

    override fun onStart() {
        super.onStart()
        if (pref.contains("email")){
            find<EditText>(R.id.text_email).setText(pref.getString("email",""))
            println(2333333333333333)
            println(pref.getString("email",""))
        }
        if (pref.contains("phone")){
            find<EditText>(R.id.text_phone).setText(pref.getString("phone",""))
        }
        if (pref.contains("address")){
            find<EditText>(R.id.text_address).setText(pref.getString("address",""))
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onResume() {
        super.onResume()
        val email : String = find<EditText>(R.id.text_email).text.toString()
        val phone : String = find<EditText>(R.id.text_phone).text.toString()
        val address : String = find<EditText>(R.id.text_address).text.toString()
        save_button.setOnClickListener {
            if (TextUtils.isEmpty(email) or TextUtils.isEmpty(phone) or TextUtils.isEmpty(address)){
                Toast.makeText(applicationContext,"信息不完整，不能保存！",Toast.LENGTH_SHORT).show()
            }
            else{
                val editor = pref.edit()
                editor.putString("email", find<EditText>(R.id.text_email).editableText.toString())
                editor.putString("phone", find<EditText>(R.id.text_phone).text.toString())
                editor.putString("address", find<EditText>(R.id.text_address).text.toString())
                editor.apply()
                println(find<EditText>(R.id.text_email).text.toString() + find<EditText>(R.id.text_phone).text.toString() + find<EditText>(R.id.text_address).text.toString())
                Toast.makeText(applicationContext,"信息保存！",Toast.LENGTH_SHORT).show()
            }
        }
    }
}