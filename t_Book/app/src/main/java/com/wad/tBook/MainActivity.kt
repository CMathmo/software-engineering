package com.wad.tBook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
import com.wad.tBook.room.*
import com.wad.tBook.statistical.AccountFragment
import com.wad.tBook.statistical.PipelineFragment
import org.jetbrains.anko.find
import kotlin.Any as KotlinAny

class MainActivity : FragmentActivity() {

    private val TAG = MainActivity::class.qualifiedName

    var titleList:MutableList<String> = arrayListOf()
    var accountFragment: AccountFragment = AccountFragment()
    var pipelineFragment: PipelineFragment = PipelineFragment()
    var analysisFragment: AnalysisFragment = AnalysisFragment()
    var settingFragment : SettingFragment = SettingFragment()
    var fragmentList: MutableList<KotlinAny> = mutableListOf(accountFragment,pipelineFragment,
        analysisFragment,settingFragment)
    var mViewPagerAdapter: ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val add_button : Button = find(R.id.add_button)
        //val button = findViewById(R.id.button1) as Button
        add_button.setOnClickListener{startActivityForResult(Intent(this, AddActivity::class.java),1)}
        Log.d(TAG,"create_show")
        initView()
        val roomdb = tBookDatabase.getDBInstace(this.application)
        Thread({
            if(roomdb.proDao().getAllPropertyData().isEmpty()){
                val typeList = mutableListOf<String>("收入","支出","转账")
                val itemList = mutableListOf<String>("类别","账户","项目","商家","成员")
                for(type in typeList){
                    for(item in itemList){
                        for(i in 1..3){
                            for(j in 1..5){
                                roomdb.proDao().addPropertyData(Property(0,type,item,type+item+i,type+item+i+j))
                            }
                        }
                    }
                }
            }
        }).start()

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
            val amount = if (data?.getStringExtra("amount") == null || data?.getStringExtra("amount") == "") 0.00 else data?.getStringExtra("amount")!!
                .toDouble()
            val type = data?.getStringExtra("type") + ""
            val date = data?.getStringExtra("date") + ""
            val first_class = data?.getStringExtra("class") + ""
            val member = if (data?.getStringExtra("member") == "") null else data?.getStringExtra("member")
            val project = if (data?.getStringExtra("project") == "") null else data?.getStringExtra("project")
            val account = data?.getStringExtra("account") + ""
            val merchant = if (data?.getStringExtra("merchant") == "") null else data?.getStringExtra("merchant")
            val remark = if (data?.getStringExtra("remark") == "") null else data?.getStringExtra("remark")
            val roomdb = tBookDatabase.getDBInstace(this.application)
        }
    }
    /**
     * 初始化方法
     * 设置TabLayout
     * 创建ViewPagerAdapter对象
     * 关联Tablayout与ViewPager
     * 给每一个TabView设置点击监听事件
     * 给ViewPager设置pageChange改变的事件
     */
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