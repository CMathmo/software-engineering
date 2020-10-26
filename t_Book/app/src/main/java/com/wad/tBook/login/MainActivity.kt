package com.wad.tBook.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.wad.tBook.NinelockFragment
import com.wad.tBook.R
import com.wad.tBook.R.id.loginway_list
import com.wad.tBook.TextFragment


class MainActivity : AppCompatActivity() {

    val mViewPagerAdapter: FragmentPagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager)
    }
    val fragmentList:MutableList<Fragment> = mutableListOf(TextFragment(), NinelockFragment())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ViewPager>(R.id.loginway_list).offscreenPageLimit = 2
        //设置ViewPager
        findViewById<ViewPager>(R.id.loginway_list).adapter = mViewPagerAdapter
    }

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

