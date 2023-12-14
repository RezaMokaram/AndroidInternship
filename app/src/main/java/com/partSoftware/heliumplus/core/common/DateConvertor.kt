package com.partSoftware.heliumplus.core.common

import saman.zamani.persiandate.PersianDate

fun convertApiTimeToJalaliCalendarDate(time: String?): String {
    if (time.isNullOrEmpty()) return ""
    val date = time.split("T").first()
    val grgYearMonthDay: List<String> = date.split("-")
    val persianDate = PersianDate()
    persianDate.grgYear = grgYearMonthDay[0].toInt()
    persianDate.grgMonth = grgYearMonthDay[1].toInt()
    persianDate.grgDay = grgYearMonthDay[2].toInt()
    return "${persianDate.shDay} ${persianDate.monthName()} ${persianDate.shYear}"
}