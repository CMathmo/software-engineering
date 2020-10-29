package com.wad.tBook.analysis

import android.accounts.Account
import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.room.Update
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartModel
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartType
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartView
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AASeriesElement
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.wad.tBook.R
import com.wad.tBook.accounting.AccountingActivity
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.AccountingDao
import com.wad.tBook.room.tBookDatabase
import kotlinx.android.synthetic.main.fragment_timeperiod_popupwindow.*
import org.jetbrains.anko.find
import org.w3c.dom.Text
import java.lang.reflect.Type
import java.util.*
import java.util.logging.Level
import java.util.zip.Inflater
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
    PIE, BAR, Line
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

    //时间回退按钮视图
    lateinit var BackTimeButton :Button
    lateinit var ForthTimeButton :Button
    //时间选择弹出窗口视图
    lateinit var timePeriodView:View
    //图表类型弹出窗口视图
    lateinit var chartTypeView:View



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        timePeriodView = inflater.inflate(R.layout.fragment_timeperiod_popupwindow, null)
        chartTypeView = inflater.inflate(R.layout.fragment_charttype_popupwindow, null)
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }


    @SuppressLint("SetTextI18n")
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
        val TypeSelectButton = view.findViewById<Button>(R.id.type_select_button)
        loadTypePopMenu(TypeSelectButton)

        //时间段选择，初始化为本周
        val TimePeriodText = view.findViewById<TextView>(R.id.time_period_text)




        val weekList = DateUtil.getWeekRange_00(DateUtil.getLocalTimeNow_00())
        val weekStartDate = weekList[0]
        val weekEndDate = weekList[1]
        StartDate = weekStartDate
        EndDate = weekEndDate
        CurDate = DateUtil.getLocalTimeNow_00()
        TimePeriodText.setText(thisWeekTag + StartDate.split("年")[1] + " ~ " + EndDate.split("年")[1])
        loadPeriodPopMenu(TimePeriodText, ChartRadioGroup)



        //时间段的前进&后推按钮
        BackTimeButton = view.findViewById<Button>(R.id.back_time_button)
        ForthTimeButton = view.findViewById<Button>(R.id.forth_time_button)
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

                R.id.Line_radioButton ->{
                    this.ChartType = ChartList.Line
                    this.UpdateChart()
                }
            }
        }
    }

    //弹出类型菜单
    private fun loadTypePopMenu(TypeSelectButton:Button){
        val window = PopupWindow(context)
        window.contentView = chartTypeView
        window.setOutsideTouchable(true)
        window.setFocusable(true)
        val ExRadioGroup :RadioGroup = chartTypeView.findViewById(R.id.ex_radiogroup)
        val InRadioGroup :RadioGroup = chartTypeView.findViewById(R.id.in_radiogroup)
        val AccRadioGroup :RadioGroup = chartTypeView.findViewById(R.id.acc_radiogroup)

        //控制弹窗的显示与隐藏
        TypeSelectButton.setOnClickListener {
            println(window.isShowing())
            if(window.isShowing()) {
                window.dismiss()
            }
            else {
                window.showAsDropDown(TypeSelectButton)
            }
        }


        //手动实现互斥
        chartTypeView.findViewById<RadioButton>(R.id.MainEx).setOnClickListener{
            this.TypeSelected = InExType.EXPENSE
            this.LevelSelected = LevelType.MAIN
            this.Account = AccountType.SINGLE
            InRadioGroup.clearCheck()
            AccRadioGroup.clearCheck()
            UpdateChart()
            window.dismiss()
        }
        chartTypeView.findViewById<RadioButton>(R.id.SubEx).setOnClickListener{
            this.TypeSelected = InExType.EXPENSE
            this.LevelSelected = LevelType.SUB
            this.Account = AccountType.SINGLE
            InRadioGroup.clearCheck()
            AccRadioGroup.clearCheck()
            UpdateChart()
            window.dismiss()
        }

        chartTypeView.findViewById<RadioButton>(R.id.MainIn).setOnClickListener{
            this.TypeSelected = InExType.INCOME
            this.LevelSelected = LevelType.MAIN
            this.Account = AccountType.SINGLE
            ExRadioGroup.clearCheck()
            AccRadioGroup.clearCheck()
            UpdateChart()
            window.dismiss()
        }
        chartTypeView.findViewById<RadioButton>(R.id.SubIn).setOnClickListener{
            this.TypeSelected = InExType.INCOME
            this.LevelSelected = LevelType.SUB
            this.Account = AccountType.SINGLE
            ExRadioGroup.clearCheck()
            AccRadioGroup.clearCheck()
            UpdateChart()
            window.dismiss()
        }

        chartTypeView.findViewById<RadioButton>(R.id.AccEx).setOnClickListener{
            this.TypeSelected = InExType.EXPENSE
            this.LevelSelected = LevelType.SUM
            this.Account = AccountType.MULTI
            InRadioGroup.clearCheck()
            ExRadioGroup.clearCheck()
            UpdateChart()
            window.dismiss()
        }
        chartTypeView.findViewById<RadioButton>(R.id.AccIn).setOnClickListener{
            this.TypeSelected = InExType.INCOME
            this.LevelSelected = LevelType.SUM
            this.Account = AccountType.MULTI
            InRadioGroup.clearCheck()
            ExRadioGroup.clearCheck()
            UpdateChart()
            window.dismiss()
        }

    }

    //弹出时间段选择菜单
    private fun loadPeriodPopMenu(TimePeriodText:TextView, ChartRadioGroup:RadioGroup){

        val window = PopupWindow(context)
        window.setOutsideTouchable(true)
        window.setFocusable(false)
        window.contentView = timePeriodView


        var oldTimeRange: TimeRangeType = TimeRange

        val marks: MutableMap<String, TextView> = mutableMapOf()

        val yearView: TextView = timePeriodView.findViewById(R.id.year)
        val monthView: TextView = timePeriodView.findViewById(R.id.month)
        val weekView: TextView = timePeriodView.findViewById(R.id.week)
        val dayView: TextView = timePeriodView.findViewById(R.id.day)

        marks["yearSelSgn"] = timePeriodView.findViewById(R.id.year_selected)
        marks["monthSelSgn"] = timePeriodView.findViewById(R.id.month_selected)
        marks["weekSelSgn"] = timePeriodView.findViewById(R.id.week_selected)
        marks["daySelSgn"] = timePeriodView.findViewById(R.id.day_selected)

        val usrDefSwitch: Switch = timePeriodView.findViewById(R.id.usr_defined_switch)
        usrDefSwitch.setChecked(false)
        val usrDefCard: CardView = timePeriodView.findViewById(R.id.user_defined_cardView)

        val usrStartDate :EditText = timePeriodView.findViewById(R.id.usr_startdate)
        val usrEndDate :EditText = timePeriodView.findViewById(R.id.usr_enddate)

        val usrCfrmBtn :Button = timePeriodView.findViewById(R.id.usrdef_cfrm_btn)

        val pvTimeStart: TimePickerView by lazy{
            TimePickerBuilder(context, OnTimeSelectListener{
                    date,v ->
                usrStartDate.setText(SimpleDateFormat("yyyy年MM月dd日").format(date))
            })
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setLabel("年","月","日","","","")
                .build()
        }

        val pvTimeEnd: TimePickerView by lazy{
            TimePickerBuilder(context, OnTimeSelectListener{
                    date,v ->
                usrEndDate.setText(SimpleDateFormat("yyyy年MM月dd日").format(date))
            })
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setLabel("年","月","日","","","")
                .build()
        }

        TimePeriodText.setOnClickListener {

            if (TimeRange == TimeRangeType.USER_DEFINED){
                dayView.setEnabled(false)
                weekView.setEnabled(false)
                monthView.setEnabled(false)
                yearView.setEnabled(false)
            }
            else{
                dayView.setEnabled(true)
                weekView.setEnabled(true)
                monthView.setEnabled(true)
                yearView.setEnabled(true)
                usrDefSwitch.setChecked(false)
            }

            window.showAtLocation(
                TimePeriodText,
                Gravity.BOTTOM,
                TimePeriodText.getX().toInt(),
                TimePeriodText.getY().toInt()
            )

            dayView.setOnClickListener {
                ChartRadioGroup.findViewById<RadioButton>(R.id.Pie_radioButton).setChecked(true)
                ChartRadioGroup.findViewById<RadioButton>(R.id.Line_radioButton).setVisibility(View.GONE)
                TimeRange = TimeRangeType.DAY
                for (t in marks.keys) {
                    if (t == "daySelSgn") {
                        marks[t]?.setVisibility(View.VISIBLE)
                    } else {
                        marks[t]?.setVisibility(View.INVISIBLE)
                    }
                }
                UpdateTimeRangeView(TimePeriodText)
                window.dismiss()
            }

            monthView.setOnClickListener {
                ChartRadioGroup.findViewById<RadioButton>(R.id.Pie_radioButton).setChecked(true)
                ChartRadioGroup.findViewById<RadioButton>(R.id.Line_radioButton).setVisibility(View.VISIBLE)
                TimeRange = TimeRangeType.MONTH
                for (t in marks.keys) {
                    if (t == "monthSelSgn") {
                        marks[t]?.setVisibility(View.VISIBLE)
                    } else {
                        marks[t]?.setVisibility(View.INVISIBLE)
                    }
                }
                UpdateTimeRangeView(TimePeriodText)
                window.dismiss()
            }

            weekView.setOnClickListener {
                ChartRadioGroup.findViewById<RadioButton>(R.id.Pie_radioButton).setChecked(true)
                ChartRadioGroup.findViewById<RadioButton>(R.id.Line_radioButton).setVisibility(View.VISIBLE)
                TimeRange = TimeRangeType.WEEK
                for (t in marks.keys) {
                    if (t == "weekSelSgn") {
                        marks[t]?.setVisibility(View.VISIBLE)
                    } else {
                        marks[t]?.setVisibility(View.INVISIBLE)
                    }
                }
                UpdateTimeRangeView(TimePeriodText)
                window.dismiss()
            }

            yearView.setOnClickListener {
                ChartRadioGroup.findViewById<RadioButton>(R.id.Pie_radioButton).setChecked(true)
                ChartRadioGroup.findViewById<RadioButton>(R.id.Line_radioButton).setVisibility(View.VISIBLE)
                TimeRange = TimeRangeType.YEAR
                for (t in marks.keys) {
                    if (t == "yearSelSgn") {
                        marks[t]?.setVisibility(View.VISIBLE)
                    } else {
                        marks[t]?.setVisibility(View.INVISIBLE)
                    }
                }
                UpdateTimeRangeView(TimePeriodText)
                window.dismiss()
            }

            usrDefSwitch.setOnCheckedChangeListener(){_, isChecked ->
                if (isChecked){
                    oldTimeRange = TimeRange
                    TimeRange = TimeRangeType.USER_DEFINED
                    println("check unable to change")
                    //window.setOutsideTouchable(false)
                    usrDefCard.setVisibility(View.VISIBLE)
                    dayView.setEnabled(false)
                    weekView.setEnabled(false)
                    monthView.setEnabled(false)
                    yearView.setEnabled(false)
                }
                else{
                    TimeRange = oldTimeRange
                    println("check able to change")
                    //window.setOutsideTouchable(true)
                    usrDefCard.setVisibility(View.GONE)
                    dayView.setEnabled(true)
                    weekView.setEnabled(true)
                    monthView.setEnabled(true)
                    yearView.setEnabled(true)
                }
            }

            usrStartDate.setOnClickListener{
                window.dismiss()
                pvTimeStart.show()
            }
            usrEndDate.setOnClickListener{
                window.dismiss()
                pvTimeEnd.show()
            }

            //恢复显示窗口，并且更新StartDate, EndDate
            usrStartDate.doOnTextChanged { text, start, before, count ->
                if(!window.isShowing) {
                    window.showAtLocation(timePeriodView, Gravity.START or Gravity.BOTTOM, 0, 0) }

            }

            usrEndDate.doOnTextChanged { text, start, before, count ->
                if(!window.isShowing) {window.showAtLocation(timePeriodView, Gravity.START or Gravity.BOTTOM, 0, 0)}
            }

            usrCfrmBtn.setOnClickListener {
                if (DateUtil.AearlierThanB(usrStartDate.text.toString(), usrEndDate.text.toString())){
                    ChartRadioGroup.findViewById<RadioButton>(R.id.Line_radioButton).setVisibility(View.GONE)
                    ChartRadioGroup.findViewById<RadioButton>(R.id.Pie_radioButton).setChecked(true)
                    //window.setOutsideTouchable(true)
                    StartDate = usrStartDate.text.toString()
                    EndDate = usrEndDate.text.toString()
                    for (m in marks.values){
                        m.setVisibility(View.INVISIBLE)
                    }
                    window.dismiss()
                    UpdateTimeRangeView(TimePeriodText)
                }
                else {
                    Toast.makeText(context, "日期输入错误", Toast.LENGTH_SHORT).show()
                }

            }



        }
    }






    //更新图表
    private fun UpdateChart(){
        if (ChartType == ChartList.Line){
            UpdateLineChart()
        }
        else{
            UpdatePieBarChart()
        }
    }

    private fun UpdatePieBarChart(){
        //Toast.makeText(context, "new chart", Toast.LENGTH_SHORT).show()
        val Accountings = getAccountingsWithinSelectedPeriod(StartDate, EndDate)
        println("update getAccountings: $Accountings")
        //一级类型
        var MainCateMap :MutableMap<String, Double> = mutableMapOf()
        //二级类型
        var SubCateMap :MutableMap<String, Double> = mutableMapOf()
        //账户类型
        var AccountMap :MutableMap<String, Double> = mutableMapOf()

        val typeName: String

        //统计数据
        println("type: ${this.TypeSelected}")
        when(this.TypeSelected){
            InExType.INCOME -> {
                typeName = "收入"
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
                typeName = "支出"
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
        generateChartwithData(aaChartView,dataMap, typeName)
    }

    private fun UpdateLineChart(){
        var timeNameList :MutableList<String> = mutableListOf()
        var timeRangeList :MutableList<MutableList<String>> = mutableListOf()
        val sumList :MutableList<Double> = mutableListOf()
        //依据当前时间段，确认时间单位（年对应月，月或者周对应日）
        when(TimeRange){
            TimeRangeType.YEAR ->{
                val curYearNum = DateUtil.getYear(StartDate)
                val curYear = if(curYearNum < 10) "0"+curYearNum.toString() else curYearNum.toString()
                timeNameList = mutableListOf("01月", "02月", "03月","04月", "05月", "06月","07月", "08月", "09月","10月", "11月", "12月", )
                for (month in timeNameList){
                    timeRangeList.add(mutableListOf(curYear+"年"+month+"01日",curYear+"年"+month+"31日" ))
                }
            }
            TimeRangeType.MONTH, TimeRangeType.WEEK ->{
                var tmpDate = StartDate
                while(DateUtil.AearlierThanB(tmpDate, EndDate)){
                    timeNameList.add(tmpDate)
                    tmpDate = DateUtil.OneDayAfter_00(tmpDate)
                }
                for (i in 0..timeNameList.lastIndex){
                    timeRangeList.add(mutableListOf(timeNameList[i], timeNameList[i]))
                    timeNameList[i] = timeNameList[i].split("年")[1].replace("月",".").replace("日","")
                }
            }
        }

        when(TypeSelected){
            InExType.INCOME ->{
                for (timeRange in timeRangeList){
                    println("timeRange, $timeRange")
                    activity?.let {
                        tBookDatabase.getDBInstace(it).actDao().readAllDateFromToAndAbout(timeRange[0], timeRange[1], "收入")
                            .let { sumList.add(it) }
                }
            }
        }
            InExType.EXPENSE ->{
                for (timeRange in timeRangeList){
                    activity?.let {
                        tBookDatabase.getDBInstace(it).actDao().readAllDateFromToAndAbout(timeRange[0], timeRange[1], "支出")
                            .let { sumList.add(it) }
                    }
                }
            }
        }

        val typeName = if(TypeSelected == InExType.INCOME)"收入" else "支出"

        //折线图模型
        val aaLineModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .title("总"+typeName+": "+ sumList.sum()+"元")
            .dataLabelsEnabled(true)
            .legendEnabled(false)
            .yAxisTitle("花费")
            .categories(timeNameList.toTypedArray())
            .backgroundColor("#FFF9C4")
            .series(arrayOf(
                AASeriesElement()
                    .data(sumList.toTypedArray())
            ))

        aaChartView.aa_drawChartWithChartModel(aaLineModel)
    }

    //获取满足时间段要求的数据
    private fun getAccountingsWithinSelectedPeriod(start:String, end: String) : List<Accounting>? {
                return context?.let { tBookDatabase.getDBInstace(it).actDao().readAllDateFromAndTo(start, end) }
    }

        //依据传入的数据表和用户选定的类型生成相应的图表
    private fun generateChartwithData(aaChartView :AAChartView, dataMap :Map<String, Double>, typeName: String) {
            val categories = dataMap.keys.toTypedArray()
            val values :Array<Any> = dataMap.values.toTypedArray()
            //存储 <类型，数值>对 的数组
            val pairs :MutableList<Set<Any>> = mutableListOf()
            for (i in 0..categories.lastIndex) {
                pairs.add(setOf(categories[i], values[i]))
            }

            var sum: Double = 0.0
            for(value in dataMap.values){
                sum += value
            }

            //饼图模型
            val aaPieModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Pie)
                .title("总"+typeName+": "+sum.toString()+"元")
                .dataLabelsEnabled(true)
                .legendEnabled(false)
                .backgroundColor("#FFF9C4")
                .series(arrayOf(
                    AASeriesElement()
                        .data(pairs.toTypedArray())
                ))

            //柱状图模型
            val aaBarModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Bar)
                .title("总"+typeName+": "+sum.toString()+"元")
                .yAxisTitle("花费")
                .dataLabelsEnabled(true)
                .legendEnabled(false)
                .backgroundColor("#FFF9C4")
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
        BackTimeButton.setEnabled(true)
        ForthTimeButton.setEnabled(true)
        when(TimeRange){
            TimeRangeType.DAY ->{
                StartDate = CurDate
                EndDate = StartDate
                TimePeriodText.setText(thisDayTag+ StartDate)
            }
            TimeRangeType.WEEK -> {
                val weekList = DateUtil.getWeekRange_00(CurDate)
                StartDate = weekList[0]
                EndDate = weekList[1]
                TimePeriodText.setText(thisWeekTag+ StartDate.split("年")[1] + " ~ " + EndDate.split("年")[1])
            }
            TimeRangeType.MONTH -> {
                val curmonth = DateUtil.getMonth(CurDate)
                val curyear = DateUtil.getYear(CurDate)
                val lastDayOfMonth = if (DateUtil.isLeapYear(curyear)) DateUtil.LeapYearMap[curmonth] else DateUtil.NormalYearMap[curmonth]
                StartDate = DateUtil.date_00(curyear, curmonth, 1)
                EndDate = DateUtil.date_00(curyear, curmonth, lastDayOfMonth!!)
                TimePeriodText.setText(thisMonthTag+ curmonth.toString() + "月1日" + " ~ " + curmonth.toString() + "月" + lastDayOfMonth.toString() + "日")}
            TimeRangeType.YEAR -> {
                val curyear = DateUtil.getYear(CurDate)
                StartDate = DateUtil.date_00(curyear,1,1)
                EndDate = DateUtil.date_00(curyear,12,31)
                TimePeriodText.setText(thisYearTag+ curyear.toString() + "年1月1日" + " ~ " + "12月31日")
            }
            TimeRangeType.USER_DEFINED -> {
                BackTimeButton.setEnabled(false)
                ForthTimeButton.setEnabled(false)
                TimePeriodText.setText(StartDate+"~"+EndDate)
            }
        }
        UpdateChart()
    }

    //点击后推&前进按钮，更新时间段
    private fun BackForthTimeButtonAction(buttonType: TimeButtonType, TimePeriodText :TextView) {
        when (TimeRange) {
            TimeRangeType.DAY -> {
                StartDate = if(buttonType == TimeButtonType.BACK) DateUtil.OneDayEarlier_00(StartDate) else DateUtil.OneDayAfter_00(EndDate)
                EndDate = StartDate
                val tag = if (CurDate == StartDate) thisDayTag else ""
                TimePeriodText.setText(tag + StartDate)
            }
            TimeRangeType.WEEK -> {
                val weeklst = if(buttonType == TimeButtonType.BACK) DateUtil.getWeekRange_00(DateUtil.OneDayEarlier_00(StartDate)) else DateUtil.getWeekRange_00(DateUtil.OneDayAfter_00(EndDate))
                StartDate = weeklst[0]
                EndDate = weeklst[1]
                val tag = if (DateUtil.AearlierThanB(StartDate, CurDate) && DateUtil.AearlierThanB(CurDate, EndDate)) thisWeekTag else ""
                TimePeriodText.setText(tag + StartDate.split("年")[1] + " ~ " + EndDate.split("年")[1])
            }
            TimeRangeType.MONTH -> {
                val monthlst = if(buttonType == TimeButtonType.BACK) DateUtil.OneMonthEarlier_00(StartDate) else DateUtil.OneMonthAfter_00(EndDate)
                StartDate = monthlst[0]
                EndDate = monthlst[1]
                val tag = if (DateUtil.getMonth(CurDate) == DateUtil.getMonth(StartDate) && DateUtil.getYear(StartDate) == DateUtil.getYear(CurDate)) thisMonthTag else ""
                TimePeriodText.setText(tag+ StartDate + " ~ " + EndDate.split("年")[1])
            }
            TimeRangeType.YEAR -> {
                val year = DateUtil.getYear(StartDate)
                StartDate = if (buttonType == TimeButtonType.BACK) DateUtil.date_00(year-1, 1, 1) else DateUtil.date_00(year+1, 1, 1)
                EndDate = if (buttonType == TimeButtonType.BACK) DateUtil.date_00(year-1, 12, 31) else DateUtil.date_00(year+1, 12, 31)
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

