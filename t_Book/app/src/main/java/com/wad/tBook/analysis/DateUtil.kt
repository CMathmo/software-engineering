package com.wad.tBook.analysis

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.wad.tBook.MainActivity
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*

//处理从数据库中读取的格式为“XX年XX月XX日”的日期数据
object DateUtil{
    private val TAG = DateUtil::class.qualifiedName
    //月份对应的天数
    val NormalYearMap :Map<Int, Int> = mapOf(1 to 31, 2 to 28, 3 to 31, 4 to 30, 5 to 31, 6 to 30, 7 to 31, 8 to 31, 9 to 30, 10 to 31, 11 to 30, 12 to 31)
    val LeapYearMap :Map<Int, Int> = mapOf(1 to 31, 2 to 29, 3 to 31, 4 to 30, 5 to 31, 6 to 30, 7 to 31, 8 to 31, 9 to 30, 10 to 31, 11 to 30, 12 to 31)
    //获取年份
    fun getYear(date :String) :Int{
        return date.split("年")[0].toInt()
    }
    //获取月份
    fun getMonth(date :String) :Int{
        return date.split("年")[1].split("月")[0].toInt()
    }
    //获取日
    fun getDay(date :String) :Int{
        return date.split("年")[1].split("月")[1].split("日")[0].toInt()
    }

    //输入年，月，日，形成规定格式的字符串
    fun date(year: Int, month: Int, day: Int) :String{
        return year.toString() + "年" + month.toString() + "月" + day.toString() + "日"
    }

    //输入年，月，日，形成规定格式的字符串
    fun date_00(year: Int, month: Int, day: Int) :String{
        return String.format("%04d",year) + "年" + String.format("%02d",month) + "月" + String.format("%02d",day) + "日"
    }

    //判断是否是闰年
    fun isLeapYear(year: Int) :Boolean = year % 4 == 0 && year % 100 != 0 || year % 400 == 0

    //获取这一天是这一年的第几天
    fun getDayOfYear(date :String) :Int{
        val YearMap = if (isLeapYear(getYear(date))) LeapYearMap else NormalYearMap
        val month = getMonth(date)
        val day = getDay(date)
        var dayNumber = 0
        for(m in 1..month-1){
            //println("$m 月有 ${YearMap[m]}天")
            dayNumber += YearMap[m]?:0
        }
        return dayNumber + day
    }

    //检查日期是否含有非法字符（如“XX年XX月XX日”中的"XX"为非法字符）
    fun containInvalidToken(date: String) :Boolean{
        val x:String = date.replace("年","").replace("月","").replace("日","")
        for (i in x){
            if (i.isLetter()) {
                return true
            }
        }
        return false
    }
    //返回日期A是否比日期B早（或者是同一天）
    fun AearlierThanB(dateA:String, dateB: String) :Boolean{
        if (containInvalidToken(dateA) || containInvalidToken(dateB)) {
            println("invalid token!")
            return false
        }
        if (DateUtil.getYear(dateA) < DateUtil.getYear(dateB)) return true
        else if (DateUtil.getYear(dateA) > DateUtil.getYear(dateB)) return false
        else if (DateUtil.getMonth(dateA) < DateUtil.getMonth(dateB)) return true
        else if (DateUtil.getMonth(dateA) > DateUtil.getMonth(dateB)) return false
        else return if(DateUtil.getDay(dateA) <= DateUtil.getDay(dateB)) true else false
    }

    //输入这天是这一年的第几天，返回这一天的日期
    fun getDatefromDayNumber(year: Int, dayNum :Int) :String {
        val YearMap = if (isLeapYear(year)) LeapYearMap else NormalYearMap
        var num = dayNum
        var month = 1
        while (num > YearMap[month]?:0) {
            month += 1
            num -= YearMap[month] ?:0
        }
        return year.toString()+"年" + month.toString() + "月" + num.toString() + "日"
    }


    //获取这天处于这一年的第几周
    fun getWeekNumer(date :String) :Int{
        return getDayOfYear(date)/ 7 + 1
    }

    //获取这一天是星期几（周日定义为7)
    fun getWhichDay(date :String) :Int{
        var y = getYear(date)
        val d = getDay(date)
        var m = getMonth(date)
        //该公式需要把今年的1,2月换算成去年的13,14月
        if (m == 1 || m == 2) {
            m += 12
            y -= 1
        }
        val w = (d+2*m+3*(m+1)/5+y+y/4-y/100+y/400) % 7
        return w+1
    }

    //获取前一天的日期
    fun OneDayEarlier(date: String) :String {
        val year = getYear(date)
        val month = getMonth(date)
        val day = getDay(date)
        val YearMap = if (isLeapYear(year)) LeapYearMap else NormalYearMap

        //不是1号
        if (day > 1) {return year.toString() + "年" + month.toString() + "月" + (day-1).toString() + "日"}
        //不是1月
        else if (day == 1 && month > 1) {return year.toString() + "年" + (month-1).toString() + "月" + YearMap[month-1].toString() + "日"}
        //1月1日
        else {return (year-1).toString() + "年12月31日"}
    }
    //获取后一天的日期
    fun OneDayAfter(date: String) :String {
        val year = getYear(date)
        val month = getMonth(date)
        val day = getDay(date)
        val YearMap = if (isLeapYear(year)) LeapYearMap else NormalYearMap
        val lastDayOfMonth = YearMap[month]

        //不是最后一天
        if (day < lastDayOfMonth?:31) {return year.toString() + "年" + month.toString() + "月" + (day+1).toString() + "日"}
        //不是12月
        else if (day == lastDayOfMonth && month < 12) {return year.toString() + "年" + (month+1).toString() + "月1日" }
        //12月31日
        else {return (year+1).toString() + "年1月1日"}
    }

    //获取前一个月的起止日期
    fun OneMonthEarlier(date: String) :List<String> {
        val year = getYear(date)
        val month = getMonth(date)
        val YearMap = if (isLeapYear(year)) LeapYearMap else NormalYearMap
        //不是一月
        if (month > 1) {
            val lastDayOfMonth = YearMap[month-1]
            val start = DateUtil.date(year, month-1, 1)
            val end = DateUtil.date(year, month-1, lastDayOfMonth?:31)
            return listOf(start, end)
        }
        //一月
        else {
            val start = DateUtil.date(year-1, 12,1)
            val end = DateUtil.date(year-1, 12, 31)
            return listOf(start, end)
        }
    }

    //获取后一个月的起止日期
    fun OneMonthAfter(date: String) :List<String> {
        val year = getYear(date)
        val month = getMonth(date)
        val YearMap = if (isLeapYear(year)) LeapYearMap else NormalYearMap
        //不是12月
        if (month < 12) {
            val lastDayOfMonth = YearMap[month+1]
            val start = DateUtil.date(year, month+1, 1)
            val end = DateUtil.date(year, month+1, lastDayOfMonth?:31)
            return listOf(start, end)
        }
        //12月
        else {
            val start = DateUtil.date(year+1, 1,1)
            val end = DateUtil.date(year+1, 1, 31)
            return listOf(start, end)
        }
    }

    //获取前一天的日期
    fun OneDayEarlier_00(date: String) :String {
        val year = getYear(date)
        val month = getMonth(date)
        val day = getDay(date)
        val YearMap = if (isLeapYear(year)) LeapYearMap else NormalYearMap

        //不是1号
        if (day > 1) {return String.format("%04d",year) + "年" + String.format("%02d",month) + "月" + String.format("%02d",day - 1) + "日"}
        //不是1月
        else if (day == 1 && month > 1) {return String.format("%04d",year) + "年" + String.format("%02d",month - 1) + "月" + String.format("%02d",YearMap[month-1]) + "日"}
        //1月1日
        else {return String.format("%04d",year - 1) + "年12月31日"}
    }
    //获取后一天的日期
    fun OneDayAfter_00(date: String) :String {
        val year = getYear(date)
        val month = getMonth(date)
        val day = getDay(date)
        val YearMap = if (isLeapYear(year)) LeapYearMap else NormalYearMap
        val lastDayOfMonth = YearMap[month]

        //不是最后一天
        if (day < lastDayOfMonth?:31) {return String.format("%04d",year) + "年" + String.format("%02d",month) + "月" + String.format("%02d",day + 1) + "日"}
        //不是12月
        else if (day == lastDayOfMonth && month < 12) {return String.format("%04d",year) + "年" + String.format("%02d",month + 1) + "月01日" }
        //12月31日
        else {return String.format("%04d",year + 1) + "年01月01日"}
    }

    //获取前一个月的起止日期
    fun OneMonthEarlier_00(date: String) :List<String> {
        val year = getYear(date)
        val month = getMonth(date)
        val YearMap = if (isLeapYear(year)) LeapYearMap else NormalYearMap
        //不是一月
        if (month > 1) {
            val lastDayOfMonth = YearMap[month-1]
            val start = DateUtil.date_00(year, month-1, 1)
            val end = DateUtil.date_00(year, month-1, lastDayOfMonth?:31)
            return listOf(start, end)
        }
        //一月
        else {
            val start = DateUtil.date_00(year-1, 12,1)
            val end = DateUtil.date_00(year-1, 12, 31)
            return listOf(start, end)
        }
    }

    //获取后一个月的起止日期
    fun OneMonthAfter_00(date: String) :List<String> {
        val year = getYear(date)
        val month = getMonth(date)
        val YearMap = if (isLeapYear(year)) LeapYearMap else NormalYearMap
        //不是12月
        if (month < 12) {
            val lastDayOfMonth = YearMap[month+1]
            val start = DateUtil.date_00(year, month+1, 1)
            val end = DateUtil.date_00(year, month+1, lastDayOfMonth?:31)
            return listOf(start, end)
        }
        //12月
        else {
            val start = DateUtil.date_00(year+1, 1,1)
            val end = DateUtil.date_00(year+1, 1, 31)
            return listOf(start, end)
        }
    }

    //获取前一年的起止日期
    fun OneYearEarlier_00(date: String) :List<String> {
        val year = getYear(date)
        val start = DateUtil.date_00(year - 1, 1, 1)
        val end = DateUtil.date_00(year - 1, 12, 31)
        return listOf(start, end)
    }

    //获取后一年的起止日期
    fun OneYearAfter_00(date: String) :List<String> {
        val year = getYear(date)
        val start = DateUtil.date_00(year + 1, 1, 1)
        val end = DateUtil.date_00(year + 1, 12, 31)
        return listOf(start, end)
    }

    //获取前一周的起止日期
    fun OneWeekEarlier_00(date: String) :List<String> {
        val weekday = OneDayEarlier_00(getWeekRange_00(date)[0])
        return getWeekRange_00(weekday)
    }

    //获取后一周的起止日期
    fun OneWeekAfter_00(date: String) :List<String> {
        val weekday = OneDayAfter_00(getWeekRange_00(date)[1])
        return getWeekRange_00(weekday)
    }

    //获取这天所在周的起始日和截止日日期
    fun getWeekRange(date :String) :List<String> {
        //val thisyear = getYear(date)
        val thisday = getWhichDay(date) //这一天是星期几
        //val dayNum = getDayOfYear(date)  //今年的第几天
        //val StartOfYear = 1
        //val EndOfYear = if (isLeapYear(getYear(date))) 366 else 365
        val fromEndOfWeek = 7 - thisday
        val fromStartOfWeek = thisday - 1
        //val fromEndOfYear = EndOfYear - dayNum
        //val fromStartOfYear = dayNum - StartOfYear

//        //如果这一周都在同一年
//        if (fromEndOfWeek <= fromEndOfYear && fromStartOfWeek <= fromStartOfYear) {
//            val MonDate = getDatefromDayNumber(thisyear, dayNum - fromStartOfWeek)
//            val SunDate = getDatefromDayNumber(thisyear, dayNum + fromEndOfWeek)
//            return listOf(MonDate, SunDate)
//        }
//
//        //如果处于年底，出现跨年
//        else if (fromEndOfWeek > fromEndOfYear) {
//            val MonDate = getDatefromDayNumber(thisyear, dayNum - fromStartOfWeek)
//            val SunDate = (thisyear+1).toString() + "年1月" + (fromEndOfWeek-fromEndOfYear).toString() + "日"
//            return listOf(MonDate, SunDate)
//        }
//        //如果处于年初，出现跨年
//        else if (fromStartOfWeek > fromStartOfYear) {
//            val MonDate = (thisyear-1).toString() + "年12月" + (31-(fromStartOfWeek-fromStartOfYear)) + "日"
//            val SunDate = getDatefromDayNumber(thisyear, dayNum + fromEndOfWeek)
//            return listOf(MonDate, SunDate)
//        }
//
//        //debug
//        return listOf("unexpected error")

        var MonDate :String = date
        var SunDate :String = date
        for (i in 1..fromStartOfWeek) MonDate = DateUtil.OneDayEarlier(MonDate)
        for (i in 1..fromEndOfWeek) SunDate = DateUtil.OneDayAfter(SunDate)

        return listOf(MonDate, SunDate)

    }

    fun getWeekRange_00(date :String) :List<String> {
        //val thisyear = getYear(date)
        val thisday = getWhichDay(date) //这一天是星期几
        //val dayNum = getDayOfYear(date)  //今年的第几天
        //val StartOfYear = 1
        //val EndOfYear = if (isLeapYear(getYear(date))) 366 else 365
        val fromEndOfWeek = 7 - thisday
        val fromStartOfWeek = thisday - 1
        //val fromEndOfYear = EndOfYear - dayNum
        //val fromStartOfYear = dayNum - StartOfYear

//        //如果这一周都在同一年
//        if (fromEndOfWeek <= fromEndOfYear && fromStartOfWeek <= fromStartOfYear) {
//            val MonDate = getDatefromDayNumber(thisyear, dayNum - fromStartOfWeek)
//            val SunDate = getDatefromDayNumber(thisyear, dayNum + fromEndOfWeek)
//            return listOf(MonDate, SunDate)
//        }
//
//        //如果处于年底，出现跨年
//        else if (fromEndOfWeek > fromEndOfYear) {
//            val MonDate = getDatefromDayNumber(thisyear, dayNum - fromStartOfWeek)
//            val SunDate = (thisyear+1).toString() + "年1月" + (fromEndOfWeek-fromEndOfYear).toString() + "日"
//            return listOf(MonDate, SunDate)
//        }
//        //如果处于年初，出现跨年
//        else if (fromStartOfWeek > fromStartOfYear) {
//            val MonDate = (thisyear-1).toString() + "年12月" + (31-(fromStartOfWeek-fromStartOfYear)) + "日"
//            val SunDate = getDatefromDayNumber(thisyear, dayNum + fromEndOfWeek)
//            return listOf(MonDate, SunDate)
//        }
//
//        //debug
//        return listOf("unexpected error")

        var MonDate :String = date
        var SunDate :String = date
        for (i in 1..fromStartOfWeek) MonDate = DateUtil.OneDayEarlier_00(MonDate)
        for (i in 1..fromEndOfWeek) SunDate = DateUtil.OneDayAfter_00(SunDate)

        return listOf(MonDate, SunDate)

    }

    //获取这天所在月的起始日和截止日日期
    fun getMonthRange_00(date :String) :List<String> {
        val year = getYear(date)
        val month = getMonth(date)
        val startDate = String.format("%04d年%02d月01日",year,month)
        val endDate = if(isLeapYear(year)){
                String.format("%04d年%02d月%02d日",year,month, LeapYearMap[month])
            }else{
                String.format("%04d年%02d月%02d日",year,month, NormalYearMap[month])
        }
        return listOf(startDate, endDate)
    }

    //获取这天所在年的起始日和截止日日期
    fun getYearRange_00(date :String) :List<String> {
        val year = getYear(date)
        val startDate = String.format("%04d年01月01日",year)
        val endDate = String.format("%04d年12月31日",year)
        return listOf(startDate, endDate)
    }

    //获取当前时间，并转换为"XX年XX月XX日"的格式
    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocalTimeNow() :String {
        val javaTime = LocalDateTime.now()
        val year = javaTime.year.toString()
        val month = javaTime.monthValue.toString()
        val day = javaTime.dayOfMonth.toString()
        return year + "年" + month + "月" + day + "日"
    }

    //获取当前时间，并转换为"XX年XX月XX日"的格式
    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocalTimeNow_00() :String {
        val javaTime = LocalDateTime.now()
        val year = String.format("%04d",javaTime.year)
        val month = String.format("%02d",javaTime.monthValue)
        val day = String.format("%02d",javaTime.dayOfMonth)
        Log.d(TAG,"momo:"+ year + "年" + month + "月" + day + "日")
        return year + "年" + month + "月" + day + "日"
    }
}


