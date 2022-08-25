package com.techsensei.gupp.main.profile

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techsensei.gupp.R
import com.techsensei.gupp.main.profile.components.ProfileImageSection
import com.techsensei.gupp.ui.theme.AppBg
import com.techsensei.gupp.ui.theme.Typography
import com.techsensei.gupp.utils.PrefsProvider
import com.techsensei.gupp.utils.Screen
import com.techsensei.gupp.utils.constants.PrefConstants
import com.techsensei.gupp.utils.navigator

private const val TAG = "ProfileTab"
@Composable
fun ProfileTab(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    Log.d(TAG, "ProfileTab: init ")
    val profileState = profileViewModel.profileState

    profileState.value.profileImageUrl?.let {
        profileViewModel.profileImg = it
    }

    val imgPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {

            it?.let { uri ->
                profileViewModel.updateProfileImage(
                    uri.toString()
                )
            }
        }

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = profileState) {
        profileState.value.error?.let {
            scaffoldState.snackbarHostState.showSnackbar(
                it,
                actionLabel = "Dismiss"
            )
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImageSection(
            profileImage = profileViewModel.profileImg,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            imgPickerLauncher = imgPickerLauncher,
            profileState = profileState
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        Text(
            text = profileViewModel.userName,
            style = Typography.h2
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )
        Text(
            text = profileViewModel.userEmail,
            style = Typography.h4
        )
        Box(
            Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            OutlinedButton(onClick = {
                profileViewModel.logout()
                navController.navigator(
                    Screen.SplashScreen.route,
                    Screen.HomeScreen.route,
                    true
                )
            }) {
                Text(
                    text = stringResource(id = R.string.logout),
                    style = Typography.h2
                )
            }
        }
    }
}

@Preview
@Composable
fun ShowProfileTab() {
    ProfileTab(navController = rememberNavController())
}
