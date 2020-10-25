package com.wad.tBook

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
import androidx.multidex.MultiDex
import com.wad.tBook.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_show.*
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.wad.tBook.accounting.AccountingActivity
import com.wad.tBook.analysis.AnalysisFragment
import com.wad.tBook.room.*
import com.wad.tBook.statistical.*
import com.wad.tBook.Pipeline.PipelineFragment
import org.jetbrains.anko.find
import kotlin.Any as KotlinAny

class MainActivity : FragmentActivity() {

    private val TAG = MainActivity::class.qualifiedName

    var titleList:MutableList<String> = arrayListOf()
    var accountFragment: AccountFragment = AccountFragment()
    var pipelineFragment: PipelineFragment =
        PipelineFragment()
    var analysisFragment: AnalysisFragment = AnalysisFragment()
    var settingFragment : SettingFragment = SettingFragment()
    var fragmentList: MutableList<KotlinAny> = mutableListOf(accountFragment,pipelineFragment,
        analysisFragment,settingFragment)
    var mViewPagerAdapter: ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MultiDex.install(this)
        setContentView(R.layout.activity_show)
        val add_button : Button = find(R.id.add_button)
        //val button = findViewById(R.id.button1) as Button
        add_button.setOnClickListener{startActivity(Intent(this, AccountingActivity::class.java))}
        Log.d(TAG,"create_show")
        initView()
        val roomdb = tBookDatabase.getDBInstace(this.application)
        initPropertyRoom(roomdb)
        val DBpath = applicationContext.getDatabasePath("tBook.db").path
        println(DBpath)
        tBookDatabase.getDBInstace(this).actDao().deleteAll()
        if(roomdb.actDao().getAllAccountingData().isEmpty()){
            for (acc in getTestData()){
                println("testdata: $acc")
                    roomdb.actDao().addAccountingData(acc)
            }

        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"start_show")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG,"restart_show")
    }

    override fun onResume() {
        super.onResume()
        val dataButton : FloatingActionButton = find(R.id.data_button)
        dataButton.setOnClickListener{
            val intent = Intent(applicationContext, StatisticalActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            applicationContext.startActivity(intent)
        }
        val otherButton : FloatingActionButton = find(R.id.other_button)
        otherButton.setOnClickListener{
            val intent = Intent(applicationContext, OtherStatisticalActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            applicationContext.startActivity(intent)
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
        showTabLayout.setupWithViewPager(showViewPager)
        showViewPager.offscreenPageLimit = 4
        //设置ViewPager
        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        showViewPager.adapter = mViewPagerAdapter
        //设置监听
        showViewPager.addOnPageChangeListener(TabViewOnPageChangeListener())
        //设置TabLayout
        titleList.add(getString(R.string.main_tab_account))
        titleList.add(getString(R.string.main_tab_pipeline))
        titleList.add(getString(R.string.main_tab_analysis))
        titleList.add(getString(R.string.main_tab_setting))
        showTabLayout.tabMode = TabLayout.MODE_FIXED
        var i = 0
        while (i < titleList.size) {
            val tab: TabLayout.Tab? = showTabLayout.getTabAt(i)
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
            showViewPager.currentItem = viewId
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
            val btn: ToggleButton = showTabLayout.getTabAt(i)?.customView as ToggleButton
            btn.isChecked = (i == id)
            i++
        }
    }

    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

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

    fun initPropertyRoom(roomdb:tBookDatabase){
        Thread{
            roomdb.proDao().deleteAllPropertyData()
            if (roomdb.proDao().getAllPropertyData().isEmpty()){
                val typeList = arrayListOf<String>("收入","支出","转账")
                val memberList = "本人、家庭公用、老婆、老公、子女、父母".split("、")
                val merchantList = "餐厅、银行、商场、超市、其他".split("、")
                val projectList = "过年、报销、出差、装修".split("、")
                for(type:String in typeList){
                    for(sclass:String in memberList){
                        roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "成员",propertyFirstClass = "所有",propertySecondClass = sclass))
                    }
                    for(sclass:String in merchantList){
                        roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "商家",propertyFirstClass = "所有",propertySecondClass = sclass))
                    }
                    for(sclass:String in projectList){
                        roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "项目",propertyFirstClass = "所有",propertySecondClass = sclass))
                    }
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "现金",propertySecondClass = "现金"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "信用卡",propertySecondClass = "信用卡"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "金融账户",propertySecondClass = "银行卡"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "金融账户",propertySecondClass = "股票"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "金融账户",propertySecondClass = "基金"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "虚拟账户",propertySecondClass = "支付宝"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "虚拟账户",propertySecondClass = "公交卡"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "虚拟账户",propertySecondClass = "饭卡"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "虚拟账户",propertySecondClass = "微信"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "虚拟账户",propertySecondClass = "白条"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "负债",propertySecondClass = "应付款项"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "债权",propertySecondClass = "公司报销"))
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = "账户",propertyFirstClass = "债权",propertySecondClass = "应收款项"))

                }
                var type = "收入"
                var item = "类别"
                for(sclass:String in "三餐、早餐、中餐、晚餐、买菜、水果、零食、加餐、下午茶、烟酒饮品、粮油调料".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "餐饮",propertySecondClass = sclass))
                }
                for(sclass:String in "公交地铁、打车、私家车、飞机火车、共享单车".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "交通",propertySecondClass = sclass))
                }
                for(sclass:String in "日用品、衣帽鞋包、护肤美妆、饰品、数码、电器、家装".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "购物",propertySecondClass = sclass))
                }
                for(sclass:String in "水电煤、话费、网费、房租、物业、维修".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "居家",propertySecondClass = sclass))
                }
                for(sclass:String in "送礼、请客、孝心、亲密付、发红包、借出、还钱".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "购物",propertySecondClass = sclass))
                }
                for(sclass:String in "药品、保健、治疗、美容".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "医疗",propertySecondClass = sclass))
                }
                for(sclass:String in "休闲、约会、聚会、游戏、健身".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "娱乐",propertySecondClass = sclass))
                }
                for(sclass:String in "书籍、培训、网课".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "学习",propertySecondClass = sclass))
                }
                for(sclass:String in "房贷、车贷、购物分期、手续费、保险、养卡".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "金融",propertySecondClass = sclass))
                }
                for(sclass:String in "旅游、装修、宝宝、生意、宠物、坏账、丢失".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "其他",propertySecondClass = sclass))
                }
                type = "支出"
                for(sclass:String in "工资、经营、利息、兼职、投资、奖金、加班".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "职业收入",propertySecondClass = sclass))
                }
                for(sclass:String in "礼金、抢红包、意外来钱、家里给钱、中奖、退税".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "其他收入",propertySecondClass = sclass))
                }
                type = "转账"
                for(sclass:String in "充值、提现、转账".split("、")){
                    roomdb.proDao().addPropertyData(Property(propertyType = type,propertyItem = item,propertyFirstClass = "所有",propertySecondClass = sclass))
                }

            }
        }.start()
    }
}

