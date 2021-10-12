package com.techsensei.gupp.main.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techsensei.gupp.R
import com.techsensei.gupp.ui.theme.Primary
import com.techsensei.gupp.ui.theme.PrimaryDark
import com.techsensei.gupp.ui.theme.Typography

@Composable
fun HomeTopBar() {
    Column(modifier = Modifier.fillMaxWidth()
        .background(PrimaryDark)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp)
                .background(Primary)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Gupp",
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(id = R.string.app_slogan),
                style = Typography.h1
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
    }
}

@Preview
@Composable
fun HomeTopTest() {
    HomeTopBar()
}