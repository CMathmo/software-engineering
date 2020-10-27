package com.wad.tBook

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import com.wad.tBook.login.NineLockListener

class RegisterwithNinelock : AppCompatActivity(), NineLockListener {

    private val CUSTOM_PREF_NAME = "User_data"
    private val sharedPreferences: SharedPreferences = MyApplication.context.getSharedPreferences(CUSTOM_PREF_NAME, Context.MODE_PRIVATE)
    private val editor:SharedPreferences.Editor = sharedPreferences.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerwith_ninelock)

        var nine = findViewById<com.wad.tBook.login.NineLockView>(R.id.nineregister)
        nine.setOnClickListener {nine.invalidate()}
        nine.setLockListener(this)
    }

    override fun onLockResult(result: IntArray?) {
        val stringBuffer=StringBuffer()
        for(i in 0  until result!!.size){
            stringBuffer.append(result[i])
        }
        val password = stringBuffer.toString()
        editor.putString("textpassword",password)
        editor.apply()
        Toast.makeText(this,"修改密码成功！", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onError() {
        Toast.makeText(this,"出现问题，请重新绘制图案", Toast.LENGTH_LONG).show()
    }
}