package com.wad.tBook

import android.os.Bundle
import android.view.View
import android.widget.ToggleButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wad.tBook.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_show.*
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.wad.tBook.analysis.AnalysisFragment
import com.wad.tBook.statistical.AccountFragment
import com.wad.tBook.statistical.PipelineFragment

class MainActivity : FragmentActivity() {
    var titleList:MutableList<String> = arrayListOf()
    var accountFragment: AccountFragment = AccountFragment()
    var pipelineFragment:PipelineFragment = PipelineFragment()
    var analysisFragment:AnalysisFragment = AnalysisFragment()
    var settingFragment : SettingFragment = SettingFragment()
    var fragmentList: MutableList<Any> = mutableListOf(accountFragment,pipelineFragment,
        analysisFragment,settingFragment)
    var mViewPagerAdapter: ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        initView()
    }

    private fun initView(){
        //关联TabLayout和ViewPager
        tabLayout.setupWithViewPager(viewPager)
        //设置ViewPager
        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = mViewPagerAdapter
        //设置监听
        viewPager.addOnPageChangeListener(TabViewOnPageChangeListener())

        //设置TabLayout
        titleList.add(getString(R.string.main_tab_account))
        titleList.add(getString(R.string.main_tab_pipeline))
        titleList.add(getString(R.string.main_tab_analysis))
        titleList.add(getString(R.string.main_tab_setting))
        tabLayout.tabMode = TabLayout.MODE_FIXED
        var i = 0
        while (i < titleList.size) {
            val tab: TabLayout.Tab? = tabLayout.getTabAt(i)
            if (tab != null) {
                val toggleBtn: ToggleButton = getTabView(i)

                tab.customView = toggleBtn
                toggleBtn.setOnClickListener(TabClickListener())
                toggleBtn.tag = i
            }
            i++
        }
    }

    /**
     * tabLayout中TablView的点击监听，
     * 更改当前显示的Fragment
     */
    private inner class TabClickListener : View.OnClickListener {
        override fun onClick(p0: View?) {
            val viewId: Int = p0?.tag as Int
            viewPager.currentItem = viewId
        }
    }

    private inner class TabViewOnPageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            setTabShow(position)
        }
    }

    private fun setTabShow(id: Int) {
        val count = mViewPagerAdapter?.count as Int
        var i = 0
        while (i < count) {
            val btn: ToggleButton = tabLayout.getTabAt(i)?.customView as ToggleButton
            btn.isChecked = (i == id)
            i++
        }
    }

    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        var fm: FragmentManager? = null

        init {
            this.fm = fm
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList.get(position) as Fragment
        }

        override fun getCount(): Int {
            return fragmentList.size
        }
    }

    fun getTabView(position: Int): ToggleButton {
        var resID: Int = 0
        val mToggleButton: ToggleButton =
            View.inflate(applicationContext, R.layout.main_tab_togglebutton, null) as ToggleButton
        when (position) {
            0 ->
                resID = R.drawable.main_tab_account_selector
            1 ->
                resID = R.drawable.main_tab_pipeline_selector
            2 ->
                resID = R.drawable.main_tab_analysis_selector
            3 ->
                resID = R.drawable.main_tab_setting_selector

            else -> null

        }

        formatToggleButtonTab(mToggleButton, resID, titleList[position])
        return mToggleButton
    }

    fun formatToggleButtonTab(mToggleButton: ToggleButton, resId: Int, txt: String) {
        val width: Int = resources.getDimension(R.dimen.d40).toInt()
        val height = width
        val drawable = AppCompatResources.getDrawable(applicationContext, resId)
        if (drawable != null) {
            drawable.setBounds(0, 0, width, height)
            mToggleButton.setCompoundDrawables(null, drawable, null, null)
        }
        mToggleButton.textOff = txt
        mToggleButton.textOn = txt
        mToggleButton.text = txt
    }
}