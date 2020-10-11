package com.example.roomlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.roomlearning.room.Accounting
import com.example.roomlearning.room.AccountingViewModel
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {
    lateinit var updateMember: String
    lateinit var updateType: String
    lateinit var updateMerchant: String
    lateinit var updateTime: String
    lateinit var updateNumber: String
    lateinit var viewModel: AccountingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        supportActionBar?.title = "Update Accounting"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                AccountingViewModel::class.java
            )

//        get and set data
        val id = getIntent().getStringExtra("id")
        edtUpdateId.setText(id)
        edtUpdateMember.setText(intent.getStringExtra("name"))
        edtUpdateType.setText(intent.getStringExtra("type"))
        edtUpdateMerchant.setText(intent.getStringExtra("merchant"))
        edtUpdateTime.setText(intent.getStringExtra("time"))
        edtUpdateNumber.setText(intent.getStringExtra("number"))

        edtUpdateId.isEnabled = false

    }

    fun updateButton(view: View) {
        val id = getIntent().getStringExtra("id")
        val uId = id?.toInt()
        updateMember = edtUpdateMember.text.toString()
        updateType = edtUpdateType.text.toString()
        updateMerchant = edtUpdateMerchant.text.toString()
        updateTime = edtUpdateTime.text.toString()
        updateNumber = edtUpdateNumber.text.toString()

        val act = Accounting(uId!!, updateMember,updateType,updateMerchant,updateTime,updateNumber)
        viewModel.updateActData(act)

        Toast.makeText(this,"Updated Data of $updateMember",Toast.LENGTH_LONG).show()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    fun deleteButton(view: View) {
        val id = getIntent().getStringExtra("id")

        val dialog = AlertDialog.Builder(this)
        dialog.setPositiveButton("Yes"){_,_, ->
            viewModel.deleteActData(id)
            Toast.makeText(this,"User Deleted",Toast.LENGTH_LONG).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        dialog.setNegativeButton("No"){_,_, ->}
        dialog.setTitle("Delete Message")
        dialog.setMessage("Are your sure you want to delete?")
        dialog.create().show()
    }
}