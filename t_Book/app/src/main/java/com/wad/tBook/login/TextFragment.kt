package com.wad.tBook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.wad.tBook.login.Register
import android.content.SharedPreferences
import android.widget.CheckBox
import kotlinx.android.synthetic.main.fragment_text.*
import org.jetbrains.anko.find
import java.nio.file.Files.find


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TextFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TextFragment : Fragment() {
    private val TAG = TextFragment::class.qualifiedName
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val CUSTOM_PREF_NAME = "User_data"
    private val sharedPreferences: SharedPreferences = MyApplication.context.getSharedPreferences(CUSTOM_PREF_NAME, Context.MODE_PRIVATE)
    private val editor:SharedPreferences.Editor = sharedPreferences.edit()
    var originalpassword = sharedPreferences.getString("textpassword","there is no password")
    var ischeckremeber = sharedPreferences.getInt("remeberpassword",0)//来确定是否记住密码

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //监听输入，观察什么时候输入回车
        loginPassword.addTextChangedListener{
            var password = loginPassword.text.toString()
            afterTextChange(password,originalpassword!!)
        }


        login_button.setOnClickListener(){

            var password = loginPassword.text.toString()
            loginclock(password,originalpassword!!)
        }

        register_entrance.setOnClickListener {
            val intent = Intent(activity, Register::class.java)
            startActivity(intent)
        }

        if (ischeckremeber == 1){
            loginPassword.setText(originalpassword)
            remeber_Password.isChecked = true
        }

    }

    //登录的逻辑实现
    private fun loginclock(password:String,originalpassword:String){

        if (originalpassword.equals("0")){
            Toast.makeText(activity,"没有设置密码，请先注册！", Toast.LENGTH_SHORT).show()
            loginPassword.setText("")
        }
        else{
            when(password){
                originalpassword -> loginSuccess()
                else -> loginFailed()
            }
        }
    }

    //登录成功的显示
    private fun loginSuccess(){
        Toast.makeText(activity,"登录成功！", Toast.LENGTH_SHORT).show()
        if (remeber_Password.isChecked == true){
            editor.putInt("remeberpassword",1)
            editor.apply()
        }
        else{
            editor.putInt("remeberpassword",0)
            editor.apply()
        }
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    //登录失败的显示
    private fun loginFailed(){
        Toast.makeText(activity,"用户不存在，请重新登录", Toast.LENGTH_SHORT).show()
        loginPassword.setText("")
        editor.putInt("remeberpassword",0)
        editor.apply()
    }

    private fun afterTextChange(password: String, originalpassword: String){
        if (password.indexOf('\n') >= 0||password.indexOf('\r') >= 0){
            loginPassword.setText(password.replace("\n","").replace("\r",""))
            var newpassword = loginPassword.text.toString()
            loginclock(newpassword,originalpassword)
        }
    }





}

