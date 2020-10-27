package com.wad.tBook.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wad.tBook.R
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private val TAG = EditActivity::class.qualifiedName

    val type:String by lazy { intent.getStringExtra("type").toString() }
    val item:String by lazy { intent.getStringExtra("item").toString() }
    private val viewModel: EditViewModel by viewModels { EditViewModelFactory(application, type,item) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        edit_recyclerview.layoutManager = LinearLayoutManager(this)
        editTittleTextView.setText(type+"->"+item)
        val edit_adapter =
            EditAdapter(this)
        edit_recyclerview.adapter = edit_adapter
        viewModel.readAllData.observe(this, Observer {
                firstClassList: List<String> ->
            Log.d(TAG,"momo")
            edit_adapter.readData(firstClassList)
        })
    }
}