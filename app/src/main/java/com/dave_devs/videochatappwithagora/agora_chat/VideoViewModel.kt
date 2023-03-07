package com.dave_devs.videochatappwithagora.agora_chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VideoViewModel: ViewModel() {

    private val _hasAudioPermsState = mutableStateOf(false)
    val hasAudioPermsState: State<Boolean> = _hasAudioPermsState

    private val _hasCameraPermsState = mutableStateOf(false)
    val hasCameraPermsState: State<Boolean> = _hasCameraPermsState

    fun onPermissionResult(
        acceptedAudioPermission:Boolean,
        acceptedCameraPermission:Boolean,
    ) {
        _hasAudioPermsState.value = acceptedAudioPermission
        _hasCameraPermsState.value = acceptedCameraPermission
    }
}