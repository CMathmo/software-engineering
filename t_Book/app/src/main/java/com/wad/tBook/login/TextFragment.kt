package com.wad.tBook

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.wad.tBook.login.ControlActivity

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.login_button).setOnClickListener(){
            var password = view?.findViewById<EditText?>(R.id.loginPassword)
            var newpassword = password?.text.toString()
            var originalpassword = "wadupupup"//到时候在注册那里修改originalpassword即可
            when(newpassword){
                originalpassword -> loginSuccess()
                else -> loginFailed()
            }
        }

        //var choosewaytologin = view.findViewById<CheckBox?>(R.id.choose_loginway)
        //choosewaytologin!!.setOnCheckedChangeListener { buttonView, isChecked ->true
            //gotodialog = ChangeDialog(this)
            //gotodialog!!.checktext()
            //gotodialog!!.show()
        //}

    }

    //登录成功的显示
    private fun loginSuccess(){
        Toast.makeText(activity,"登录成功！", Toast.LENGTH_LONG).show()
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    //登录失败的显示
    private fun loginFailed(){
        Toast.makeText(activity,"用户不存在，请重新登录", Toast.LENGTH_LONG).show()
    }


}

