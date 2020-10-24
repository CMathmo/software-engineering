package com.wad.tBook

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.CheckBox

class ChangeDialog(context: Context): Dialog(context) {

    private var changeview:View? = null

    init {
        changeview = LayoutInflater.from(context).inflate(R.layout.dialog_change,null)
        this.setCanceledOnTouchOutside(false)
        var endButton = changeview!!.findViewById<Button?>(R.id.canceldialog)
        endButton!!.setOnClickListener{
            cancel()
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(changeview!!)
    }

    fun checktext(){
        var check1 = changeview?.findViewById<CheckBox?>(R.id.text)
        check1!!.isChecked = true
    }
}