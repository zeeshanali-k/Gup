package com.techsensei.gupp.main.notifications

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techsensei.domain.model.Resource
import com.techsensei.domain.use_case.notifications.GetNotifications
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotifications: GetNotifications
) : ViewModel() {

    private val _notificationsState: MutableState<NotificationsState> =
        mutableStateOf(NotificationsState(isLoading = true))
    val notificationsState: State<NotificationsState> = _notificationsState

    init {
        if (_notificationsState.value.notifications == null) {
            viewModelScope.launch (Dispatchers.IO){
                getNotifications().collectLatest {
                    when(it){
                        is Resource.Success->{
                            _notificationsState.value = NotificationsState(isLoading = false,notifications = it.data)
                        }

                        is Resource.Error->{
                            _notificationsState.value = NotificationsState(isLoading = false,error = it.message)
                        }

                    }
                }
            }
        }
    }

}