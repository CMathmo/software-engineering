package com.wad.tBook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.wad.tBook.login.ControlActivity
import com.wad.tBook.login.NineLockListener

class LoginwithNinelock : AppCompatActivity() , NineLockListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginwith_ninelock)



        var nine = findViewById<com.wad.tBook.login.NineLockView>(R.id.nine)
        nine.setOnClickListener {nine.invalidate()}
        nine.setLockListener(this)
    }

    override fun onLockResult(result: IntArray?) {
        val stringBuffer=StringBuffer()
        var originalpassword = "54321"
        for(i in 0  until result!!.size){
            stringBuffer.append(result[i])
        }
        when(stringBuffer.toString()){
            originalpassword -> LoginSucess()
            else -> LoginFailure()
        }
    }

    private fun LoginSucess(){
        Toast.makeText(this,"登录成功！", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ControlActivity::class.java)
        startActivity(intent)
    }

    private  fun LoginFailure(){
        Toast.makeText(this,"密码错误，请重新登录", Toast.LENGTH_LONG).show()
    }

    override fun onError() {
        Toast.makeText(this,"出现问题，请重新绘制图案", Toast.LENGTH_LONG).show()
    }
}