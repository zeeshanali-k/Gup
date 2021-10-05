package com.techsensei.gupp.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techsensei.gupp.R
import com.techsensei.gupp.ui.theme.Primary
import com.techsensei.gupp.ui.theme.Typography

@Composable
fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp)
            .background(Primary)
            .padding(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = Typography.h2.fontSize,
            color = Typography.h2.color,
            fontFamily = Typography.h2.fontFamily,
        )
    }
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(5.dp))
}

//@Preview
//@Composable
//fun HomeTopTest() {
//    HomeTopBar()
//}