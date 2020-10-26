package com.wad.tBook.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.wad.tBook.MainActivity
import com.wad.tBook.R
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    val CUSTOM_PREF_NAME = "User_data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var password1 = aPassword.text.toString()
        var password2 = bPassword.text.toString()

        register.setOnClickListener {
            if (password1 == null||password2 == null){
                registerFailure()
            }
            else{
                when(password1){
                    password2 -> registerSucess(password1)
                    else -> registerFailure()
                }
            }
        }



    }

    private fun registerSucess(password:String){
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(CUSTOM_PREF_NAME, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("textpassword",password)
        editor.apply()
        editor.commit()
        Toast.makeText(this,"注册成功！", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun registerFailure(){
        Toast.makeText(this,"两次密码不一致，请重新输入！", Toast.LENGTH_LONG).show()
        aPassword.setText("")
        bPassword.setText("")
    }
}