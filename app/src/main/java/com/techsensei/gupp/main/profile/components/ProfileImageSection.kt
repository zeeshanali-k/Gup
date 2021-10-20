package com.techsensei.gupp.main.profile.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.techsensei.gupp.R
import com.techsensei.gupp.main.profile.ProfileState
import com.techsensei.gupp.ui.theme.SecondaryDark
import com.techsensei.gupp.utils.PrefsProvider
import com.techsensei.gupp.utils.constants.PrefConstants

@Composable
fun ProfileImageSection(
    profileImage : State<String?>,
    modifier: Modifier = Modifier,
    imgPickerLauncher: ManagedActivityResultLauncher<String, Uri>,
    profileState: State<ProfileState>
) {

    Box(
        modifier = modifier
    ) {
        if (profileState.value.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Image(
            painter = rememberImagePainter(
                data = profileImage.value ?: R.drawable.man_avatar
            ),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(50))
                .align(Alignment.Center),contentScale = ContentScale.Fit
        )

        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                Modifier
                    .weight(2f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                    contentDescription = "Edit Profile",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable {
                            imgPickerLauncher.launch("image/*")
                        }
                        .background(SecondaryDark)
                        .padding(10.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }

    }

}