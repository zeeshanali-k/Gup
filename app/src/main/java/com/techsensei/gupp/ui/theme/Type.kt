package com.techsensei.gupp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.techsensei.gupp.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily(listOf(Font(R.font.josefinsans_bold))),
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = TextColor
    ),
    button = TextStyle(
        fontFamily = FontFamily(listOf(Font(R.font.josefinsans_bold))),
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        color = ButtonTextColor
    ),
    h2 = TextStyle(
        fontFamily = FontFamily(listOf(Font(R.font.josefinsans_bold))),
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = DarkTextColor
    ),
    h4 = TextStyle(
        fontFamily = FontFamily(listOf(Font(R.font.josefinsans_medium))),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = TextColor
    ),
    h5 = TextStyle(
        fontFamily = FontFamily(listOf(Font(R.font.josefinsans_medium))),
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        color = DarkTextColor
    ),
    h1 = TextStyle(
        fontFamily = FontFamily(listOf(Font(R.font.josefinsans_bold))),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = DarkTextColor
    ),
    caption = TextStyle(
        fontFamily = FontFamily(listOf(Font(R.font.josefinsans_light))),
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        color = TextColor
    )
    /* Other default text styles to override
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)