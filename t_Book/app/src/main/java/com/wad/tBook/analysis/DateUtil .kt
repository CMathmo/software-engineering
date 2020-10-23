package com.wad.tBook.analysis

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

//处理从数据库中读取的格式为“XX年XX月XX日”的日期数据
object DateUtil{
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

    //判断是否是闰年
    fun isLeapYear(year: Int) :Boolean = year % 4 == 0 && year % 100 != 0 || year % 400 == 0

    //获取这一天是这一年的第几天，作为唯一标识
    fun getDayNumer(date :String) :Int{
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
        return getDayNumer(date)/ 7 + 1
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


    //获取这天所在周的起始日和截止日日期
    fun getWeekRange(date :String) :List<String> {
        val thisyear = getYear(date)
        val thisday = getWhichDay(date) //这一天是星期几
        val dayNum = getDayNumer(date)  //今年的第几天
        val StartOfYear = 1
        val EndOfYear = if (isLeapYear(getYear(date))) 366 else 365
        val fromEndOfWeek = 7 - thisday
        val fromStartOfWeek = thisday - 1
        val fromEndOfYear = EndOfYear - dayNum
        val fromStartOfYear = dayNum - StartOfYear

//        println("this day: $thisday")
//        println("dayNum: $dayNum")
//        println("fromEndofWeek: $fromEndOfWeek")
//        println("fromStartofWeek: $fromStartOfWeek")
//        println("fromEndOfYear: $fromEndOfYear")
//        println("fromStartOfYear: $fromStartOfYear")
        //如果这一周都在同一年
        if (fromEndOfWeek <= fromEndOfYear && fromStartOfWeek <= fromStartOfYear) {
            val MonDate = getDatefromDayNumber(thisyear, dayNum - fromStartOfWeek)
            val SunDate = getDatefromDayNumber(thisyear, dayNum + fromEndOfWeek)
            return listOf(MonDate, SunDate)
        }

        //如果处于年底，出现跨年
        else if (fromEndOfWeek > fromEndOfYear) {
            val MonDate = getDatefromDayNumber(thisyear, dayNum - fromStartOfWeek)
            val SunDate = (thisyear+1).toString() + "年1月" + (fromEndOfWeek-fromEndOfYear).toString() + "日"
            return listOf(MonDate, SunDate)
        }
        //如果处于年初，出现跨年
        else if (fromStartOfWeek > fromStartOfYear) {
            val MonDate = (thisyear-1).toString() + "年12月" + (31-(fromStartOfWeek-fromStartOfYear)) + "日"
            val SunDate = getDatefromDayNumber(thisyear, dayNum + fromEndOfWeek)
            return listOf(MonDate, SunDate)
        }

        //debug
        return listOf("unexpected error")

    }

    //获取当前时间，并转换为"XX年XX月XX日"的格式
    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocalTimeNow() :String {
        val javaTime = LocalDateTime.now()
        return javaTime.year.toString() + "年" + javaTime.monthValue.toString() + "月" + javaTime.dayOfMonth.toString() + "日"
    }
}


