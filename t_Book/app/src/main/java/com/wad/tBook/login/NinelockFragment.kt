package com.wad.tBook

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wad.tBook.login.NineLockListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NinelockFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NinelockFragment : Fragment(), NineLockListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var originalpassword = "54321"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ninelock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        var nine = view.findViewById<com.wad.tBook.login.NineLockView>(R.id.nine)
        nine.setOnClickListener {nine.invalidate()}
        nine.setLockListener(this)
    }

    override fun onLockResult(result: IntArray?) {
        val stringBuffer=StringBuffer()

        for(i in 0  until result!!.size){
            stringBuffer.append(result[i])
        }
        when(stringBuffer.toString()){
            originalpassword -> LoginSucess()
            else -> LoginFailure()
        }
    }

    private fun LoginSucess(){
        Toast.makeText(activity,"登录成功！", Toast.LENGTH_LONG).show()
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    private  fun LoginFailure(){
        Toast.makeText(activity,"密码错误，请重新登录", Toast.LENGTH_LONG).show()
        
    }

    override fun onError() {
        Toast.makeText(activity,"出现问题，请重新绘制图案", Toast.LENGTH_LONG).show()
    }
}