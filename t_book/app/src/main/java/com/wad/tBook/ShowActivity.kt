package com.wad.tBook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.room.Room
import org.jetbrains.anko.find

class ShowActivity : AppCompatActivity() {

    private val TAG = ShowActivity::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val add_button : Button = find(R.id.add_button)
        //val button = findViewById(R.id.button1) as Button
        add_button.setOnClickListener{startActivityForResult(Intent(this, AddActivity::class.java),1)}
        Log.d(TAG,"create_show")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"start_show")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG,"restart_show")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            //toast(:Double.toString())
            val money = if (data?.getStringExtra("money") == null) 0.00 else data?.getStringExtra("money").toDouble()
            val type = data?.getStringExtra("type") + ""
            val date = data?.getStringExtra("date") + ""
            val remark = data?.getStringExtra("remark") + ""

            var roomdb = Room.databaseBuilder(this,SQLDatabase::class.java,"item_db").build()
            Thread({
                roomdb.item().insert(Item(money = money,first_class = type,date = date,remark = remark, index = 0,account = "me"))
                val ilist = roomdb.item().qeuryAll()
                for(it in ilist){
                    Log.d(TAG,"show"+it)
                }
            }).start()
        }
    }
}