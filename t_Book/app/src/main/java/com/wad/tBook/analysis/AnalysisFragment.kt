package com.wad.tBook.analysis

import android.accounts.Account
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartModel
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartType
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartView
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AASeriesElement
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase
import org.jetbrains.anko.find
import java.lang.reflect.Type
import java.util.*
import java.util.logging.Level
import kotlin.properties.Delegates

//记账类型
enum class InExType{
    INCOME, EXPENSE
}

//分类级别
enum class LevelType{
    MAIN, SUB, SUM
}

//单用户或多用户
enum class AccountType{
    SINGLE, MULTI
}

//时间范围
enum class TimeRangeType{
    DAY, WEEK, MONTH, YEAR, SEASON, USER_DEFINED
}

//前进&后推按钮类型
enum class TimeButtonType {
    BACK, FORTH
}
//图表类型
enum class ChartList{
    PIE, BAR
}
class AnalysisFragment : Fragment() {

    //测试用的标签
    val TAG = AnalysisFragment::class.qualifiedName
    //显示
    val thisDayTag = "本日 "
    val thisWeekTag = "本周 "
    val thisMonthTag = "本月 "
    val thisYearTag = "本年 "


    //记账类型，默认为支出
    var TypeSelected :InExType = InExType.EXPENSE
    //分类级别，默认为一级
    var LevelSelected :LevelType = LevelType.MAIN
    //是否多用户，默认为否
    var Account :AccountType = AccountType.SINGLE
    //选定的时间范围，默认为周
    var TimeRange: TimeRangeType = TimeRangeType.WEEK
    //当前时间范围的起始日期
    lateinit var StartDate: String
    //当前时间范围的终止日期
    lateinit var EndDate: String
    //启动应用时的当前日期
    lateinit var CurDate: String
    //图表类型，默认为饼图
    var ChartType :ChartList = ChartList.PIE

    //图表视图
    lateinit  var aaChartView :AAChartView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")

        //图表视图
        aaChartView= view.findViewById(R.id.AAChartView)

        //获取图表类型
        val ChartRadioGroup :RadioGroup = view.findViewById(R.id.chart_radiogroup)
        getChartType(ChartRadioGroup)

        //下拉框
        val TypeSelectButton = view.findViewById<ImageButton>(R.id.type_select_button)
        loadTypePopMenu(TypeSelectButton)

        //时间段选择，初始化为本周
        val TimePeriodText = view.findViewById<TextView>(R.id.time_period_text)

        val weekList = DateUtil.getWeekRange(DateUtil.getLocalTimeNow())
        val weekStartDate = weekList[0]
        val weekEndDate = weekList[1]
        StartDate = weekStartDate
        EndDate = weekEndDate
        CurDate = DateUtil.getLocalTimeNow()
        TimePeriodText.setText(thisWeekTag + StartDate.split("年")[1] + " ~ " + EndDate.split("年")[1])
        loadPeriodPopMenu(TimePeriodText)

        //时间段的前进&后推按钮
        val BackTimeButton = view.findViewById<Button>(R.id.back_time_button)
        val ForthTimeButton = view.findViewById<Button>(R.id.forth_time_button)
        BackTimeButton.setOnClickListener{BackForthTimeButtonAction(TimeButtonType.BACK, TimePeriodText)}
        ForthTimeButton.setOnClickListener{BackForthTimeButtonAction(TimeButtonType.FORTH, TimePeriodText)}


        this.UpdateChart()



        //context?.let { tBookDatabase.getDBInstace(it).actDao().getAllAccountingData() }
    }

    override fun onStart() {
        super.onStart()
        this.UpdateChart()
        Log.i(TAG, "onStart: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    //获取图表类型（饼图或者柱状图）
    private fun getChartType(ChartRadioGroup:RadioGroup){
        //获取图表类型
        ChartRadioGroup.setOnCheckedChangeListener{buttonView, isChecked ->
            val checkedButtonId = ChartRadioGroup.checkedRadioButtonId
            when(checkedButtonId){
                R.id.Pie_radioButton -> {
                    this.ChartType = ChartList.PIE
                    this.UpdateChart()
                    //Toast.makeText(context, "饼图", Toast.LENGTH_SHORT).show()
                }

                R.id.Bar_radioButton -> {
                    this.ChartType = ChartList.BAR
                    this.UpdateChart()
                    //Toast.makeText(context, "柱状图", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //弹出类型菜单
    private fun loadTypePopMenu(TypeSelectButton:ImageButton){
        TypeSelectButton.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(context, TypeSelectButton)
            popupMenu.menuInflater.inflate(R.menu.chart_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.MainIn ->{
                        this.TypeSelected = InExType.INCOME
                        this.LevelSelected = LevelType.MAIN
                        this.Account = AccountType.SINGLE
                        //Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.SubIn ->{
                        this.TypeSelected = InExType.INCOME
                        this.LevelSelected = LevelType.SUB
                        this.Account = AccountType.SINGLE
                        //Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.MainEx ->{
                        this.TypeSelected = InExType.EXPENSE
                        this.LevelSelected = LevelType.MAIN
                        this.Account = AccountType.SINGLE
                        //Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.SubEx ->{
                        this.TypeSelected = InExType.EXPENSE
                        this.LevelSelected = LevelType.SUB
                        this.Account = AccountType.SINGLE
                        //Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.AccIn ->{
                        this.TypeSelected = InExType.INCOME
                        this.LevelSelected = LevelType.SUM
                        this.Account = AccountType.MULTI
                        //Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.AccEx ->{
                        this.TypeSelected = InExType.EXPENSE
                        this.LevelSelected = LevelType.SUM
                        this.Account = AccountType.MULTI
                        //Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                }
                this.UpdateChart()
                true
            })
            popupMenu.show()
        }
    }

    //弹出时间段选择菜单
    private fun loadPeriodPopMenu(TimePeriodText:TextView){
        TimePeriodText.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(context, TimePeriodText)
            popupMenu.menuInflater.inflate(R.menu.chart_period_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.day ->{
                        TimeRange = TimeRangeType.DAY
                        UpdateTimeRangeView(TimePeriodText)
                    }

                    R.id.week ->{
                        TimeRange = TimeRangeType.WEEK
                        UpdateTimeRangeView(TimePeriodText)
                    }

                    R.id.month ->{
                        TimeRange = TimeRangeType.MONTH
                        UpdateTimeRangeView(TimePeriodText)
                    }

                    R.id.year ->{
                        TimeRange = TimeRangeType.YEAR
                        UpdateTimeRangeView(TimePeriodText)
                    }
                }
                this.UpdateChart()
                true
            })
            popupMenu.show()
        }
    }







    //更新图表
    private fun UpdateChart(){
        Toast.makeText(context, "new chart", Toast.LENGTH_SHORT).show()
        val Accountings = getAccountingsWithinSelectedPeriod()
        println("update getAccountings: $Accountings")
        //一级类型
        var MainCateMap :MutableMap<String, Double> = mutableMapOf()
        //二级类型
        var SubCateMap :MutableMap<String, Double> = mutableMapOf()
        //账户类型
        var AccountMap :MutableMap<String, Double> = mutableMapOf()

        //统计数据
        println("type: ${this.TypeSelected}")
        when(this.TypeSelected){
            InExType.INCOME -> {
                Log.i(TAG, "Hey")
                if (Accountings != null) {
                    for (account in Accountings ){
                        if (account.accountingType == "收入"){
                            var mainclass = account.accountingClass.firstClass
                            var subclass = account.accountingClass.secondClass
                            var accname = account.accountingAcconut.secondClass ?: "未知账户"
                            if (!(mainclass in MainCateMap.keys)) {MainCateMap[mainclass] = 0.0}
                            if (!(subclass in SubCateMap.keys)) {SubCateMap[subclass] = 0.0}
                            if (!(accname in AccountMap.keys)) {AccountMap[subclass] = 0.0}
                            MainCateMap[mainclass] = (MainCateMap[mainclass]?:0.0) + account.accountingAmount
                            SubCateMap[subclass] = (SubCateMap[subclass]?:0.0) + account.accountingAmount
                            AccountMap[accname] = (AccountMap[accname]?:0.0) + account.accountingAmount
                        }
                    }
                }
            }
            InExType.EXPENSE -> {
                println("支出类型")
                if (Accountings != null) {
                    for (account in Accountings ){
                        if (account.accountingType == "支出"){
                            var mainclass = account.accountingClass.firstClass
                            var subclass = account.accountingClass.secondClass
                            var accname = account.accountingAcconut.secondClass ?: "未知账户"
                            //println("money: ${account.accountingAmount}")
                            //println("- mainclass = $mainclass, subclass = $subclass, Maincatemap = $MainCateMap")
                            MainCateMap[mainclass] = (MainCateMap[mainclass]?:0.0) + account.accountingAmount
                            SubCateMap[subclass] = (SubCateMap[subclass]?:0.0) + account.accountingAmount
                            AccountMap[accname] = (AccountMap[accname]?:0.0) + account.accountingAmount
                            //println("--- mainclass = $mainclass, subclass = $subclass, Maincatemap = $MainCateMap")
                        }
                    }
                }
            }
        }

        //依据用户选择的图表类型传递相应的数据
        var dataMap :Map<String, Double> = when(this.LevelSelected){
            //一级分类
            LevelType.MAIN -> MainCateMap
            //二级分类
            LevelType.SUB -> SubCateMap
            //按账户分类
            LevelType.SUM -> AccountMap
        }

        println("MainCateMap: $MainCateMap")
        println("SubCateMap: $SubCateMap")
        println("AccountMap: $AccountMap" )

        //生成图表
        generateChartwithData(aaChartView,dataMap)
    }

    //获取满足时间段要求的数据
    private fun getAccountingsWithinSelectedPeriod() : List<Accounting>? {

        return context?.let { tBookDatabase.getDBInstace(it).actDao().getAllAccountingData() }
            ?.filter { DateUtil.AearlierThanB(StartDate, it.accountingTime) && DateUtil.AearlierThanB(it.accountingTime, EndDate) }

    }

        //依据传入的数据表和用户选定的类型生成相应的图表
    private fun generateChartwithData(aaChartView :AAChartView, dataMap :Map<String, Double>) {
            val categories = dataMap.keys.toTypedArray()
            val values :Array<Any> = dataMap.values.toTypedArray()
            //存储 <类型，数值>对 的数组
            val pairs :MutableList<Set<Any>> = mutableListOf()
            for (i in 0..categories.lastIndex) {
                pairs.add(setOf(categories[i], values[i]))
            }

            //饼图模型
            val aaPieModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Pie)
//            .title("title")
                .dataLabelsEnabled(true)
                .legendEnabled(false)
                .series(arrayOf(
                    AASeriesElement()
                        .data(pairs.toTypedArray())
                ))

            //柱状图模型
            val aaBarModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Bar)
//            .title("title")
                .dataLabelsEnabled(true)
                .legendEnabled(false)
                .categories(categories)
                .series(arrayOf(
                    AASeriesElement()
                        .data(values)
                ))

            //画图
            when(this.ChartType){
                ChartList.PIE -> {aaChartView.aa_drawChartWithChartModel(aaPieModel)}
                ChartList.BAR -> {aaChartView.aa_drawChartWithChartModel(aaBarModel)}
            }
        }


    //更新时间段的显示
    private fun UpdateTimeRangeView(TimePeriodText :TextView) {
        when(TimeRange){
            TimeRangeType.DAY ->{
                StartDate = CurDate
                EndDate = StartDate
                TimePeriodText.setText(thisDayTag+ StartDate)
            }
            TimeRangeType.WEEK -> {
                val weekList = DateUtil.getWeekRange(CurDate)
                StartDate = weekList[0]
                EndDate = weekList[1]
                TimePeriodText.setText(thisWeekTag+ StartDate.split("年")[1] + " ~ " + EndDate.split("年")[1])
            }
            TimeRangeType.MONTH -> {
                val curmonth = DateUtil.getMonth(CurDate)
                val curyear = DateUtil.getYear(CurDate)
                val lastDayOfMonth = if (DateUtil.isLeapYear(curyear)) DateUtil.LeapYearMap[curmonth] else DateUtil.NormalYearMap[curmonth]
                StartDate = DateUtil.date(curyear, curmonth, 1)
                EndDate = DateUtil.date(curyear, curmonth, lastDayOfMonth!!)
                TimePeriodText.setText(thisMonthTag+ curmonth.toString() + "月1日" + " ~ " + curmonth.toString() + "月" + lastDayOfMonth.toString() + "日")}
            TimeRangeType.YEAR -> {
                val curyear = DateUtil.getYear(CurDate)
                StartDate = DateUtil.date(curyear,1,1)
                EndDate = DateUtil.date(curyear,12,31)
                TimePeriodText.setText(thisYearTag+ curyear.toString() + "年1月1日" + " ~ " + "12月31日")
            }
        }
        UpdateChart()
    }

    //点击后推&前进按钮，更新时间段
    private fun BackForthTimeButtonAction(buttonType: TimeButtonType, TimePeriodText :TextView) {
        when (TimeRange) {
            TimeRangeType.DAY -> {
                StartDate = if(buttonType == TimeButtonType.BACK) DateUtil.OneDayEarlier(StartDate) else DateUtil.OneDayAfter(EndDate)
                EndDate = StartDate
                val tag = if (CurDate == StartDate) thisDayTag else ""
                TimePeriodText.setText(tag + StartDate)
            }
            TimeRangeType.WEEK -> {
                val weeklst = if(buttonType == TimeButtonType.BACK) DateUtil.getWeekRange(DateUtil.OneDayEarlier(StartDate)) else DateUtil.getWeekRange(DateUtil.OneDayAfter(EndDate))
                StartDate = weeklst[0]
                EndDate = weeklst[1]
                val tag = if (DateUtil.AearlierThanB(StartDate, CurDate) && DateUtil.AearlierThanB(CurDate, EndDate)) thisWeekTag else ""
                TimePeriodText.setText(tag + StartDate.split("年")[1] + " ~ " + EndDate.split("年")[1])
            }
            TimeRangeType.MONTH -> {
                val monthlst = if(buttonType == TimeButtonType.BACK) DateUtil.OneMonthEarlier(StartDate) else DateUtil.OneMonthAfter(EndDate)
                StartDate = monthlst[0]
                EndDate = monthlst[1]
                val tag = if (DateUtil.getMonth(CurDate) == DateUtil.getMonth(StartDate) && DateUtil.getYear(StartDate) == DateUtil.getYear(CurDate)) thisMonthTag else ""
                TimePeriodText.setText(tag+ StartDate + " ~ " + EndDate.split("年")[1])
            }
            TimeRangeType.YEAR -> {
                val year = DateUtil.getYear(StartDate)
                StartDate = if (buttonType == TimeButtonType.BACK) DateUtil.date(year-1, 1, 1) else DateUtil.date(year+1, 1, 1)
                EndDate = if (buttonType == TimeButtonType.BACK) DateUtil.date(year-1, 12, 31) else DateUtil.date(year+1, 12, 31)
                val tag = if (DateUtil.getYear(CurDate) == DateUtil.getYear(StartDate)) thisYearTag else ""
                TimePeriodText.setText(tag+ StartDate + " ~ " + EndDate.split("年")[1])
            }
        }
        UpdateChart()
    }

    companion object {
        const val PARAMS_UID = "uid"
        const val PARAMS_SOURCE = "source"
        fun newInstance(param1: String, param2: String) =
            AnalysisFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAMS_UID, param1)
                    putString(PARAMS_UID, param2)
                }
            }
    }
}

