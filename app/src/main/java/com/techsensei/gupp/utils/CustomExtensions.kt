package com.techsensei.gupp.utils

import androidx.navigation.NavController

fun NavController.navigator(route:String,popUpTo:String,inclusive:Boolean){
    this.navigate(route){
        this.popUpTo(popUpTo){
            this.inclusive = inclusive
        }
    }
}