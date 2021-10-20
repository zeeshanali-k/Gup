package com.techsensei.gupp.authentication.login_register.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techsensei.domain.model.User
import com.techsensei.gupp.R
import com.techsensei.gupp.authentication.AuthState
import com.techsensei.gupp.ui.theme.*
import com.techsensei.gupp.utils.helpers.InputError


@ExperimentalAnimationApi
@Composable
fun LoginSignUpForm(
    authState: AuthState,
    isNewUser: Boolean,
    inputError: State<InputError>,
    user: User,
    onEmailChanged: (email: String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onProceedClicked: () -> Unit
) {


    LoginTextField(
        user.email?:"",
        onEmailChanged,
        KeyboardType.Email,
        stringResource(id = R.string.email),
        inputError.value.emailError,
        Icons.Rounded.Email,
        stringResource(id = R.string.email)
    )

    AnimatedVisibility(visible = isNewUser) {

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            LoginTextField(
                value = user.name?:"",
                onTextChanged = onNameChanged,
                keyboardType = KeyboardType.Text,
                label = stringResource(id = R.string.full_name),
                inputError = inputError.value.nameError,
                Icons.Rounded.Person,
                stringResource(id = R.string.full_name)
            )

        }

    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

    PasswordTextField(user, onPasswordChanged, inputError.value.passwordError)

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)

    )
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        AnimatedVisibility(visible = !authState.isLoading) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(
                    onClick = { onProceedClicked() },
                    modifier = Modifier
                        .background(ButtonColor, shape = RoundedCornerShape(size = 12.dp))
                        .width(120.dp)
                        .height(50.dp)
                ) {

                    Text(
                        text = "Proceed",
                        fontFamily = Typography.button.fontFamily,
                        color = Typography.button.color,
                        fontSize = Typography.button.fontSize
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(10.dp)
                    )

                    Icon(
                        painter = rememberVectorPainter(image = Icons.Rounded.ArrowForward),
                        contentDescription = "Proceed", tint = ButtonTextColor
                    )
                }
            }
        }
        AnimatedVisibility(visible = authState.isLoading) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                CircularProgressIndicator(color = CircularProgressBarColor)
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun LoginTextField(
    value: String, onTextChanged: (String) -> Unit,
    keyboardType: KeyboardType,
    label: String, inputError: String?,
    leadingIcon: ImageVector,
    contentDescription: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onTextChanged,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            autoCorrect = true
        ),
        label = {
            Text(text = label, color = DarkTextColor)
        },
        textStyle = TextStyle(color = TextColor, fontSize = 18.sp),
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = rememberVectorPainter(image = leadingIcon),
                contentDescription = contentDescription,
                tint = TextColor
            )
        },
        modifier = Modifier.fillMaxWidth(),
        isError = inputError != null && inputError.isNotEmpty()
    )
    AnimatedVisibility(visible = (inputError != null && inputError.isNotEmpty())) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        inputError?.let {
            Text(
                text = it,
                fontFamily = Typography.body1.fontFamily,
                fontSize = 12.sp,
                color = Color.Red
            )
        }

    }

}

@ExperimentalAnimationApi
@Composable
fun PasswordTextField(user: User, onPasswordChanged: (String) -> Unit, passwordError: String?) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val icon = if (isPasswordVisible) {
        painterResource(id = R.drawable.ic_baseline_visibility_off_24)
    } else {
        painterResource(R.drawable.ic_baseline_visibility_24)
    }

    OutlinedTextField(
        value = user.password?:"",
        onValueChange = onPasswordChanged,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            autoCorrect = true
        ),
        textStyle = TextStyle(color = TextColor, fontSize = 18.sp),
        singleLine = true,
        label = {
            Text(
                text = stringResource(id = R.string.password),
                color = DarkTextColor
            )
        }, trailingIcon = {
            Icon(painter = icon, contentDescription = "Password Toggle", Modifier.clickable {
                isPasswordVisible = !isPasswordVisible
            }, tint = TextColor)
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        isError = passwordError != null && passwordError.isNotEmpty()
    )

    AnimatedVisibility(visible = (passwordError != null && passwordError.isNotEmpty())) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        passwordError?.let {
            Text(
                text = it,
                fontFamily = Typography.body1.fontFamily,
                fontSize = 12.sp,
                color = Color.Red
            )
        }
    }
}
