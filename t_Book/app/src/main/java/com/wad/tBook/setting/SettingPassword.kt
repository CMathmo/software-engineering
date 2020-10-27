package com.wad.tBook.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.wad.tBook.*
import com.wad.tBook.login.*
import kotlinx.android.synthetic.main.activity_setting_password.*

class SettingPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_password)


        ninelockchange_button.setOnClickListener {
            var intent = Intent(this, RegisterwithNinelock::class.java)
            startActivity(intent)
        }

        goback_button.setOnClickListener {
            finish()
        }

        textchange_button.setOnClickListener {
            var intent = Intent(this, RegisterwithText::class.java)
            startActivity(intent)
        }
    }

}