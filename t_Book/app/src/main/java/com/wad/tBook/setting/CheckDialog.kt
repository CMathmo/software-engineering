package com.wad.tBook.setting

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.wad.tBook.R
import kotlinx.android.synthetic.main.dialog_check.*
import kotlinx.android.synthetic.main.dialog_check.view.*

class CheckDialog (context: Context): Dialog(context){
    private var fview: View?=null

    init {
        fview= LayoutInflater.from(context).inflate(R.layout.dialog_check,null)
        fview!!.securityButton.setOnClickListener { cancel() }
        this.setCanceledOnTouchOutside(false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(fview!!)
    }

}