package com.techsensei.gupp.utils
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class PrefsProvider(val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "MBHelperPrefs",
        MODE_PRIVATE
    )

    fun getString(key:String) = sharedPreferences.getString(key,null)

    fun setString(key:String,value:String){
        sharedPreferences.edit().putString(key,value)
            .apply()
    }

    fun getBool(key:String) = sharedPreferences.getBoolean(key,false)

    fun setBool(key:String,value:Boolean){
        sharedPreferences.edit().putBoolean(key,value)
            .apply()
    }

}