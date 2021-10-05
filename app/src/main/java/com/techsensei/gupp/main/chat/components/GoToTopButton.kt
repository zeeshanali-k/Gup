package com.techsensei.gupp.main.chat.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.techsensei.gupp.ui.theme.IconImageColor

@Composable
fun GoToTopButton(onClick:()->Unit) {
    FloatingActionButton(onClick =  onClick) {
        Icon(painter = rememberVectorPainter(image = Icons.Rounded.KeyboardArrowUp),
            contentDescription = "Go To Top",tint = IconImageColor)
    }
}