package com.wad.tBook.Accounting

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.wad.tBook.MainActivity
import com.wad.tBook.R
import com.wad.tBook.room.tBookDatabase
import kotlinx.android.synthetic.main.activity_accounting.*
import kotlinx.android.synthetic.main.activity_show.*

@RequiresApi(Build.VERSION_CODES.N)
class AccountingActivity : AppCompatActivity() {

    val mViewPagerAdapter : FragmentPagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager)
    }
    val titleList:MutableList<String> = mutableListOf("收入","支出","转账")
    val fragmentList:MutableList<Fragment> = mutableListOf(IncomeFragment(),ExpenditureFragment(),TransferFragment())
    val id by lazy {
        intent.getIntExtra("id",-1)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounting)
        initView()
    }

    fun initView(){
        type_tablayout.setupWithViewPager(accounting_item_list)
        accounting_item_list.offscreenPageLimit = titleList.size
        //设置ViewPager
        accounting_item_list.adapter = mViewPagerAdapter
        //设置TabLayout
        type_tablayout.tabMode = TabLayout.MODE_FIXED
        for (i:Int in 0..titleList.size - 1){
            type_tablayout.getTabAt(i)?.setText(titleList[i])
        }
        if (id != -1){
            val roomdb = tBookDatabase.getDBInstace(this.application)
            when(roomdb.actDao().getAccountingData(id)[0].accountingType){
                "收入"->type_tablayout.getTabAt(0)?.select()
                "支出"->type_tablayout.getTabAt(1)?.select()
                "转账"->type_tablayout.getTabAt(2)?.select()
                else-> null
            }
        }
    }

    /**
     * tabLayout中TablView的点击监听，
     * 更改当前显示的Fragment
     */
    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private var fm: FragmentManager? = null

        init {
            this.fm = fm
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position] as Fragment
        }

        override fun getCount(): Int {
            return fragmentList.size
        }
    }


}