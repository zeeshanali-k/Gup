package com.techsensei.gupp.authentication.login_register.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.techsensei.gupp.ui.theme.*


@Composable
fun LoginRegisterTopBar(title: String) {
    var lineWidthVal by remember {
        mutableStateOf(0.dp)
    }
    val lineWidth = animateDpAsState(
        targetValue = lineWidthVal, animationSpec = tween(
            durationMillis = 1000, delayMillis = 100, easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        lineWidthVal = 100.dp
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.10f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Primary,
                        PrimaryGradient1,
                        PrimaryGradient2,
                        PrimaryDark,
                        PrimaryDark
                    )
                )
            )
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = title,
            color = Typography.body1.color,
            fontSize = Typography.body1.fontSize,
            fontFamily = Typography.body1.fontFamily,
            fontWeight = Typography.body1.fontWeight
        )
        Spacer(modifier = Modifier.size(0.dp, 5.dp))
        Spacer(
            modifier = Modifier
                .size(lineWidth.value, (1.5).dp)
                .background(Secondary)
        )

    }
}