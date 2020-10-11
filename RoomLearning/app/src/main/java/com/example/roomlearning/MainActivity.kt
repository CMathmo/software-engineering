package com.example.roomlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomlearning.room.Accounting
import com.example.roomlearning.room.AccountingViewModel
import kotlinx.android.synthetic.main.alert_add.view.*

class MainActivity : AppCompatActivity() {

    public lateinit var viewModel: AccountingViewModel
    lateinit var customAdapter: CustomAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Accounting List"

        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                AccountingViewModel::class.java
            )
        //        display user information
        displayInfo()
    }

    //    when add floating action button is clicked
    fun addMethod(view: View) {
        val dialog = AlertDialog.Builder(this)
        val vview = LayoutInflater.from(this).inflate(R.layout.alert_add, null)
        dialog.setView(vview)
        addStdMethod(vview)
        dialog.show()
    }

    //    display user information
    private fun displayInfo() {
        recyclerView = findViewById(R.id.recyclerview)
        customAdapter = CustomAdapter(this)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.readActData.observe(this, Observer { accountings ->
            customAdapter.readData(accountings)
        })
    }

    //    add student into database
    private fun addStdMethod(view: View) {
        view.addButton.setOnClickListener {
            val name = view.edtAddName.text.toString()
            val type = view.edtAddType.text.toString()
            val merchant = view.edtAddMerchant.text.toString()
            val time = view.edtAddTime.text.toString()
            val number = view.edtAddNumber.text.toString()

            if (TextUtils.isEmpty(name)) {
                view.edtAddName.error = "Required Field ..."
                return@setOnClickListener
            }


            if (TextUtils.isEmpty(type)) {
                view.edtAddType.error = "Required Field ..."
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(time)) {
                view.edtAddTime.error = "Required Field ..."
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(number)) {
                view.edtAddNumber.error = "Required Field ..."
                return@setOnClickListener
            }

            val accounting = Accounting(0, name, type,merchant,time,number)
            viewModel.addActData(accounting)
            view.edtAddName.setText("")
            view.edtAddType.setText("")
            view.edtAddMerchant.setText("")
            view.edtAddTime.setText("")
            view.edtAddNumber.setText("")
            Toast.makeText(application, "Inserted", Toast.LENGTH_LONG).show()

        }
    }
}