package com.wad.tBook

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.wad.tBook.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_registerwith_text.*
import org.jetbrains.anko.find

class RegisterwithText : AppCompatActivity() {

    private val CUSTOM_PREF_NAME = "User_data"
    private val sharedPreferences: SharedPreferences = MyApplication.context.getSharedPreferences(CUSTOM_PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerwith_text)

        Toast.makeText(this,"成功进入页面！", Toast.LENGTH_SHORT).show()

        makesurtochange.setOnClickListener {
            if (find<EditText>(R.id.Passwordone).text.toString().equals("")){
                Toast.makeText(this,"密码不能为空！", Toast.LENGTH_SHORT).show()
            }
            else{
                when(find<EditText>(R.id.Passwordone).text.toString()){
                    find<EditText>(R.id.Passwordtwo).text.toString() -> changeSucess(find<EditText>(R.id.Passwordone).text.toString())
                    else -> changeFailure()
                }
            }
        }
    }

    private fun changeSucess(password:String){
        editor.putString("textpassword",password)
        editor.apply()
        Toast.makeText(this,"修改成功！", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun changeFailure(){
        Toast.makeText(this,"两次密码不一致，请重新输入！", Toast.LENGTH_SHORT).show()
        Passwordone.setText("")
        Passwordtwo.setText("")
    }

}