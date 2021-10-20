package com.techsensei.gupp.main.profile

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techsensei.domain.model.Resource
import com.techsensei.domain.use_case.profile.UpdateProfileImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateProfileImageUseCase: UpdateProfileImage
) : ViewModel() {
    private val TAG = "ProfileViewModel"
    private val _profileState: MutableState<ProfileState> = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    fun updateProfileImage(fileUri: String, userId: Int) {
        Log.d(TAG, "updateProfileImage: ")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "updateProfileImage: started")
            updateProfileImageUseCase(fileUri, userId).collect {
                Log.d(TAG, "updateProfileImage: in")
                when (it) {
                    is Resource.Success -> {
                        Log.d(TAG, "updateProfileImage: s")
                        _profileState.value = ProfileState(profileImageUrl = it.data!!.url)
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "updateProfileImage: ${it.message}")
                        _profileState.value = ProfileState(error = it.message)
                    }

                    else -> {
                        Log.d(TAG, "updateProfileImage: l")
                        _profileState.value = ProfileState(isLoading = true)
                    }

                }
            }
        }
    }

}