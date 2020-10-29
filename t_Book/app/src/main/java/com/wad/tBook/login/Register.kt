package com.wad.tBook.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.wad.tBook.MainActivity
import com.wad.tBook.MyApplication
import com.wad.tBook.R
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.find

class Register : AppCompatActivity() {

    private val CUSTOM_PREF_NAME = "User_data"
    private val sharedPreferences: SharedPreferences = MyApplication.context.getSharedPreferences(CUSTOM_PREF_NAME, Context.MODE_PRIVATE)
    private val editor:SharedPreferences.Editor = sharedPreferences.edit()
    private var originalpassword = sharedPreferences.getString("textpassword","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register.setOnClickListener {
            if (originalpassword.equals("")){
                if (TextUtils.isEmpty(find<EditText>(R.id.aPassword).text.toString()) ||TextUtils.isEmpty(find<EditText>(R.id.bPassword).text.toString())){
                    registerFailure()
                }
                else{
                    when(find<EditText>(R.id.aPassword).text.toString()){
                        find<EditText>(R.id.bPassword).text.toString() -> registerSucess(find<EditText>(R.id.aPassword).text.toString())
                        else -> registerFailure()
                    }
                }
            }
            else{
                registerNotnew()
            }
        }



    }

    private fun registerSucess(password:String){

        editor.putString("textpassword",password)
        editor.apply()
        Toast.makeText(this,"注册成功！", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerFailure(){
        Toast.makeText(this,"两次密码不一致，请重新输入！", Toast.LENGTH_SHORT).show()
        aPassword.setText("")
        bPassword.setText("")
    }

    private fun registerNotnew(){
        Toast.makeText(this,"您已经注册过密码，请回想一下！", Toast.LENGTH_SHORT).show()
        aPassword.setText("")
        bPassword.setText("")
    }
}