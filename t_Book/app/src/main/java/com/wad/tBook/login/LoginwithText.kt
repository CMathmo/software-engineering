package com.wad.tBook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.wad.tBook.login.ControlActivity

class LoginwithText : AppCompatActivity() {

    private var gotodialog:ChangeDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginwith_text)

        var choosewaytologin = findViewById<CheckBox?>(R.id.choose_loginway)
        choosewaytologin!!.setOnCheckedChangeListener { buttonView, isChecked ->true
            gotodialog = ChangeDialog(this)
            gotodialog!!.checktext()
            gotodialog!!.show()
        }
    }

    //登录成功的显示
    private fun loginSuccess(){
        Toast.makeText(this,"登录成功！", Toast.LENGTH_LONG).show()
        val intent = Intent(this, ControlActivity::class.java)
        startActivity(intent)
    }

    //登录失败的显示
    private fun loginFailed(){
        Toast.makeText(this,"用户不存在，请重新登录", Toast.LENGTH_LONG).show()
    }

    //点击登录时的显示
    fun onClickcontrolactivity(view: View){
        var password = findViewById<EditText?>(R.id.loginPassword)
        var newpassword = password?.text.toString()
        var originalpassword = "wadupupup"//到时候在注册那里修改originalpassword即可
        when(newpassword){
            originalpassword -> loginSuccess()
            else -> loginFailed()
        }
    }

}

