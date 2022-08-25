package com.techsensei.gupp.authentication.login_register

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.techsensei.domain.model.User
import com.techsensei.gupp.R
import com.techsensei.gupp.authentication.login_register.components.LoginRegisterTopBar
import com.techsensei.gupp.authentication.login_register.components.LoginSignUpForm
import com.techsensei.gupp.ui.theme.*
import com.techsensei.gupp.utils.Screen
import com.techsensei.gupp.utils.helpers.InputError
import com.techsensei.gupp.utils.navigator

@Composable
fun LoginRegisterScreen(
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = loginRegisterViewModel.authState.value) {
        if (loginRegisterViewModel.isLoggedIn) {
            navController.navigator(
                Screen.HomeScreen.route,
                Screen.LoginRegisterScreen.route,
                true
            )
            return@LaunchedEffect
        }
        val authState = loginRegisterViewModel.authState.value
        authState.error?.let {
            scaffoldState.snackbarHostState.showSnackbar(
                it, "Dismiss",
                SnackbarDuration.Short
            )
        }
        authState.user?.let {
            if ((authState.isRegistered || it.exists) && authState.isLoading.not()) {
                Log.d(TAG, "LoginRegisterScreen: ")
                navController.navigator(
                    Screen.HomeScreen.route,
                    Screen.LoginRegisterScreen.route,
                    true
                )
            }
        }
    }
    val context = LocalContext.current

    Scaffold(modifier = Modifier
        .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            LoginRegisterTopBar(stringResource(id = R.string.login_register))
        }) {
        LoginRegBody(it, loginRegisterViewModel, context)
    }

}

fun checkLogin(
    loginRegisterViewModel: LoginRegisterViewModel,
    navController: NavController,
    isLoggedIn: MutableState<Boolean>
): Boolean {
    Log.d(TAG, "checkLogin: ${isLoggedIn.value}")
    if (isLoggedIn.value)
        return true
    val authState = loginRegisterViewModel.authState.value
    authState.user?.let {
        if ((authState.isRegistered || it.exists) && authState.isLoading.not()) {
//            Navigate to Home screen
            isLoggedIn.value = true
            navController.navigator(
                Screen.HomeScreen.route,
                Screen.LoginRegisterScreen.route,
                true
            )
            return true
        }
        return false
    }
    return false
}

@Composable
fun LoginRegBody(
    paddingValues: PaddingValues,
    loginRegisterViewModel: LoginRegisterViewModel,
    context: Context
) {
    val authState = loginRegisterViewModel.authState
    val isNewUser = authState.value.user?.let { !it.exists } ?: false
    val user = remember {
        mutableStateOf(User())
    }
    val error = remember {
        mutableStateOf(InputError())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryDark)
            .padding(10.dp)
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier
                .size(100.dp, 100.dp)
                .align(CenterHorizontally)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(end = 100.dp), horizontalAlignment = Alignment.Start
        ) {

            LoginSignUpForm(authState.value, isNewUser, error, user.value,
                { emailVal ->
                    user.value = user.value.copy(email = emailVal)
                    error.value = error.value.copy(emailError = null)
                },
                { passwordVal ->
                    user.value = user.value.copy(password = passwordVal)
                    error.value = error.value.copy(passwordError = null)
                },
                { nameVal ->
                    user.value = user.value.copy(name = nameVal)
                    error.value = error.value.copy(nameError = null)
                }) {
//                Handling login click
                if (checkInputs(user.value, error, context, isNewUser)) {
                    if (isNewUser)
                        loginRegisterViewModel.registerUser(user.value)
                    else loginRegisterViewModel.verifyUser(user.value)
                }

            }
        }
    }
}

fun checkInputs(
    value: User,
    error: MutableState<InputError>,
    context: Context,
    isNewUser: Boolean
): Boolean {
    if (value.email!!.isEmpty()) {
        error.value = InputError(emailError = context.getString(R.string.email_error))
        return false
    } else if (value.password!!.isEmpty()) {
        error.value = InputError(passwordError = context.getString(R.string.invalid_password_error))
        return false
    } else if (value.password!!.length < 6) {
        error.value = InputError(passwordError = context.getString(R.string.password_length_error))
        return false
    } else if (isNewUser && value.name!!.isEmpty()) {
        error.value = InputError(nameError = context.getString(R.string.invalid_name_error))
        return false
    }

    return true
}

private const val TAG = "LoginRegisterTest"

//
//@Preview
//@Composable
//fun TestLoginResgister() {
//    LoginRegisterScreen(navController = rememberNavController())
//}