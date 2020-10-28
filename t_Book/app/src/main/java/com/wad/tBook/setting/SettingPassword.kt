package com.wad.tBook.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.wad.tBook.*
import com.wad.tBook.login.*
import kotlinx.android.synthetic.main.activity_setting_password.*

class SettingPassword : AppCompatActivity() {

    private val CUSTOM_PREF_NAME = "User_data"
    private val sharedPreferences: SharedPreferences = MyApplication.context.getSharedPreferences(CUSTOM_PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val stateNumber = sharedPreferences.getInt("fingerprintpassword",0)//状态显示，0表示禁用指纹，1表示开启指纹

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_password)

        val actionBar: ActionBar? = supportActionBar

        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        if (stateNumber == 1){
            fingerprint_button.isChecked = true
        }

        ninelockchange_button.setOnClickListener {
            var intent = Intent(this, RegisterwithNinelock::class.java)
            startActivity(intent)
        }


        textchange_button.setOnClickListener {
            var intent = Intent(this, RegisterwithText::class.java)
            startActivity(intent)
        }

        fingerprint_button.setOnClickListener {
            if(stateNumber == 1){
                fingerprint_button.isChecked == false
                editor.putInt("fingerprintpassword",0)
                editor.apply()
            }
            else
            {
                fingerprint_button.isChecked == true
                editor.putInt("fingerprintpassword",1)
                editor.apply()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}