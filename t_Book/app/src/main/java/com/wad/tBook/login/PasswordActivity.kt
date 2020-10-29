package com.wad.tBook.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wad.tBook.MyApplication
import android.hardware.fingerprint.*
import com.wad.tBook.R
import org.jetbrains.anko.fingerprintManager

class PasswordActivity : AppCompatActivity() {

    private val CUSTOM_PREF_NAME = "User_data"
    private val sharedPreferences: SharedPreferences = MyApplication.context.getSharedPreferences(CUSTOM_PREF_NAME, Context.MODE_PRIVATE)
    private val conditionNumber = sharedPreferences.getInt("fingerprintpassword",0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        if (conditionNumber == 0){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, LoginwithFingerprint::class.java)
            startActivity(intent)
        }
    }
}