package com.techsensei.gupp.main.chat.chat.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techsensei.gupp.R
import com.techsensei.gupp.ui.theme.ButtonColor
import com.techsensei.gupp.ui.theme.IconImageColor
import com.techsensei.gupp.ui.theme.TextColor
import com.techsensei.gupp.ui.theme.Typography

@ExperimentalAnimationApi
@Composable
fun MessageInput(
    onSendClicked: (message: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var message by remember {
        mutableStateOf("")
    }

    val messageBoxWidth = animateFloatAsState(targetValue = if (message.isEmpty()) 1f else 0.9f)

    Row(
        modifier =modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = message,
            onValueChange = {
                message = it
            },
            modifier = Modifier
                .fillMaxWidth(messageBoxWidth.value)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(10.dp)),
//                .border(BorderStroke(2.dp, color = TextColor)),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.enter_message),
                    style = Typography.h4,
                    color = TextColor
                )
            },
            textStyle = Typography.h4
        )
        AnimatedVisibility(visible = message.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(1.dp))
                Icon(
                    painter = rememberVectorPainter(image = Icons.Rounded.Send),
                    contentDescription = "Send Message",
                    tint = IconImageColor,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable {
                            onSendClicked(message)
                            message = ""
                        }
                        .background(ButtonColor)
//                        .border(BorderStroke(1.dp, TextColor))
                        .padding(5.dp)
                )
            }

        }

    }

}
//
//@ExperimentalAnimationApi
//@Preview
//@Composable
//fun ShowMessageInput() {
//    MessageInput(onSendClicked = {})
//}