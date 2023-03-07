package com.dave_devs.videochatappwithagora.agora_chat

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dave_devs.videochatappwithagora.agora_chat.Constant.APP_ID
import io.agora.agorauikit_android.AgoraConnectionData
import io.agora.agorauikit_android.AgoraVideoViewer

@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun VideoScreen(
    modifier: Modifier = Modifier,
    roomName: String,
    onNavigateUP: () -> Unit = {},
    viewModel: VideoViewModel = viewModel()
) {
    var agoraView: AgoraVideoViewer? = null
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            viewModel.onPermissionResult(
                acceptedAudioPermission = perms[Manifest.permission.RECORD_AUDIO] == true,
                acceptedCameraPermission = perms[Manifest.permission.CAMERA] == true
            )
        }
    )

    LaunchedEffect(key1 = true) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            )
        )
    }
    //Back handler for when we leave the channel/room
    BackHandler {
        agoraView?.leaveChannel()
        onNavigateUP()
    }
    //We migrate Xml view to Compose view with AndroidView helper function.
    if(
        viewModel.hasAudioPermsState.value &&
        viewModel.hasCameraPermsState.value
    ) {
        AndroidView(
            factory = { it ->
                AgoraVideoViewer(
                    it, connectionData = AgoraConnectionData(
                        appId = APP_ID,
                    )
                ).also{
                    it.join(roomName)
                    agoraView = it
                }
            },
            modifier = modifier
                .fillMaxSize()
                .padding(all = 8.dp)
        )
    }
}