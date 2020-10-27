package com.wad.tBook.statistical.datepipeline

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wad.tBook.R
import kotlinx.android.synthetic.main.activity_date_pipeline.*

@RequiresApi(Build.VERSION_CODES.O)
class DatePipilineActivity : AppCompatActivity() {

    val mViewPagerAdapter : FragmentPagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager)
    }
    val fragmentList:MutableList<Fragment> = mutableListOf(
        DatePipelineFragment("日"),
        DatePipelineFragment("周"),
        DatePipelineFragment("月"),
        DatePipelineFragment("年"),
    )

    val titleList:MutableList<String> = mutableListOf("日","周","月","年")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_pipeline)
        date_tablayout.setupWithViewPager(date_pipe_viewpager)
        date_pipe_viewpager.offscreenPageLimit = 4
        date_pipe_viewpager.adapter = mViewPagerAdapter
        date_tablayout.tabMode = TabLayout.MODE_FIXED
        for (i in 0..titleList.size - 1){
            date_tablayout.getTabAt(i)?.setText(titleList[i])
        }


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