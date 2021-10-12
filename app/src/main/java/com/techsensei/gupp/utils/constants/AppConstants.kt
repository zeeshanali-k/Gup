package com.techsensei.gupp.utils.constants

import java.text.SimpleDateFormat
import java.util.*

object AppConstants {

    fun getCurrentTime() = getSdf().format(Date())

    fun getSdf() = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aaa", Locale.getDefault())
    fun getServerSdf() = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
    fun getTimeSdf() = SimpleDateFormat("hh:mm aaa", Locale.getDefault())

    fun getTimeFromDate(date:String) = getTimeSdf().format(getServerSdf().parse(date))

}