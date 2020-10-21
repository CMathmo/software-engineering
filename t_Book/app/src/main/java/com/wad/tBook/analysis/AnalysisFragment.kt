package com.wad.tBook.analysis

import android.accounts.Account
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.wad.tBook.R
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase
import org.jetbrains.anko.find
import java.lang.reflect.Type
import java.util.logging.Level
import kotlin.properties.Delegates

//记账类型
enum class TypeList{
    INCOME, EXPENSE
}

//分类级别
enum class LevelList{
    MAIN, SUB, SUM
}

//单用户或多用户
enum class AccountList{
    SINGLE, MULTI
}

//图标类型
enum class ChartList{
    PIE, BAR
}
class AnalysisFragment : Fragment() {
<<<<<<< Updated upstream
=======
    //测试用的标签
    val TAG = AnalysisFragment::class.qualifiedName
    //记账类型，默认为支出
    var TypeSelected :TypeList = TypeList.EXPENSE
    //分类级别，默认为一级
    var LevelSelected :LevelList = LevelList.MAIN
    //是否多用户，默认为否
    var Account :AccountList = AccountList.SINGLE
    //图标类型，默认为饼图
    var ChartType :ChartList = ChartList.PIE



>>>>>>> Stashed changes
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }

<<<<<<< Updated upstream
=======
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")

        //图表视图
        val aaChartView : AAChartView = view.findViewById(R.id.AAChartView)

        //获取图表类型
        val ChartRadioGroup :RadioGroup = view.findViewById(R.id.chart_radiogroup)
        getChartType(aaChartView, ChartRadioGroup)

        //下拉框
        val TypeSelectButton = view.findViewById<ImageButton>(R.id.type_select_button)
        loadTypePopMenu(aaChartView, TypeSelectButton)

        //时间段选择
        val TimePeriodText = view.findViewById<TextView>(R.id.time_period_text)
        loadPeriodPopMenu(aaChartView, TimePeriodText)

        this.UpdateChart(aaChartView)



        //context?.let { tBookDatabase.getDBInstace(it).actDao().getAllAccountingData() }
    }

    override fun onStart() {
        super.onStart()

        Log.i(TAG, "onStart: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    //获取图表类型（饼图或者柱状图）
    private fun getChartType(aaChartView: AAChartView, ChartRadioGroup:RadioGroup){
        //获取图表类型
        ChartRadioGroup.setOnCheckedChangeListener{buttonView, isChecked ->
            val checkedButtonId = ChartRadioGroup.checkedRadioButtonId
            when(checkedButtonId){
                R.id.Pie_radioButton -> {
                    this.ChartType = ChartList.PIE
                    this.UpdateChart(aaChartView)
                    //Toast.makeText(context, "饼图", Toast.LENGTH_SHORT).show()
                }

                R.id.Bar_radioButton -> {
                    this.ChartType = ChartList.BAR
                    this.UpdateChart(aaChartView)
                    //Toast.makeText(context, "柱状图", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //弹出类型菜单
    private fun loadTypePopMenu(aaChartView: AAChartView, TypeSelectButton:ImageButton){
        TypeSelectButton.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(context, TypeSelectButton)
            popupMenu.menuInflater.inflate(R.menu.chart_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.MainIn ->{
                        this.TypeSelected = TypeList.INCOME
                        this.LevelSelected = LevelList.MAIN
                        this.Account = AccountList.SINGLE
                        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.SubIn ->{
                        this.TypeSelected = TypeList.INCOME
                        this.LevelSelected = LevelList.SUB
                        this.Account = AccountList.SINGLE
                        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.MainEx ->{
                        this.TypeSelected = TypeList.EXPENSE
                        this.LevelSelected = LevelList.MAIN
                        this.Account = AccountList.SINGLE
                        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.SubEx ->{
                        this.TypeSelected = TypeList.EXPENSE
                        this.LevelSelected = LevelList.SUB
                        this.Account = AccountList.SINGLE
                        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.AccIn ->{
                        this.TypeSelected = TypeList.INCOME
                        this.LevelSelected = LevelList.SUM
                        this.Account = AccountList.MULTI
                        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.AccEx ->{
                        this.TypeSelected = TypeList.EXPENSE
                        this.LevelSelected = LevelList.SUM
                        this.Account = AccountList.MULTI
                        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                }
                this.UpdateChart(aaChartView)
                true
            })
            popupMenu.show()
        }
    }

    //弹出是简单选择菜单
    private fun loadPeriodPopMenu(aaChartView: AAChartView, TimePeriodText:TextView){
        TimePeriodText.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(context, TimePeriodText)
            popupMenu.menuInflater.inflate(R.menu.chart_period_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.day ->{
                        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.week ->{
                        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.month ->{
                        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }

                    R.id.year ->{
                        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
                    }
                }
                this.UpdateChart(aaChartView)
                true
            })
            popupMenu.show()
        }
    }
    //更新图表
    private fun UpdateChart(aaChartView: AAChartView){
        Toast.makeText(context, "new chart", Toast.LENGTH_SHORT).show()
        val Accountings = getAccountingsWithinSelectedPeriod()
        //一级类型
        var MainCateMap :MutableMap<String, Double> = mutableMapOf()
        //二级类型
        var SubCateMap :MutableMap<String, Double> = mutableMapOf()
        //账户类型
        var AccountMap :MutableMap<String, Double> = mutableMapOf()

        //统计数据
        when(this.TypeSelected){
            TypeList.INCOME -> {
                Log.i(TAG, "Hey")
                if (Accountings != null) {
                    for (account in Accountings ){
                        if (account.accountingType == "收入"){
                            var mainclass = account.accountingClass.firstClass
                            var subclass = account.accountingClass.secondClass
                            var accname = account.accountingMember?.secondClass ?: "未知账户"
                            MainCateMap[mainclass] = MainCateMap[mainclass]?:0 + account.accountingAmount
                            SubCateMap[subclass] = SubCateMap[subclass]?:0 + account.accountingAmount
                            AccountMap[accname] = AccountMap[accname]?:0 + account.accountingAmount
                        }
                    }
                }
            }
            TypeList.EXPENSE -> {
                if (Accountings != null) {
                    for (account in Accountings ){
                        if (account.accountingType == "支出"){
                            var mainclass = account.accountingClass.firstClass
                            var subclass = account.accountingClass.secondClass
                            var accname = account.accountingMember?.secondClass ?: "未知账户"
                            MainCateMap[mainclass] = MainCateMap[mainclass]?:0 + account.accountingAmount
                            SubCateMap[subclass] = SubCateMap[subclass]?:0 + account.accountingAmount
                            AccountMap[accname] = AccountMap[accname]?:0 + account.accountingAmount
                        }
                    }
                }
            }
        }

        //依据用户选择的图表类型传递相应的数据
        var dataMap :Map<String, Double> = when(this.LevelSelected){
            //一级分类
            LevelList.MAIN -> MainCateMap
            //二级分类
            LevelList.SUB -> SubCateMap
            //按账户分类
            LevelList.SUM -> AccountMap
        }

        //生成图表
        generateChartwithData(aaChartView,dataMap)
    }

    //获取满足时间段要求的数据
    private fun getAccountingsWithinSelectedPeriod() : List<Accounting>? {
        return context?.let { tBookDatabase.getDBInstace(it).actDao().getAllAccountingData() }
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






>>>>>>> Stashed changes
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

