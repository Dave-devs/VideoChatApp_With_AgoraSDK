package com.dave_devs.videochatappwithagora.agora_chat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dave_devs.videochatappwithagora.agora_chat.Constant.VIDEO_SCREEN
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RoomScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    viewModel: RoomViewModel = viewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.onJoinEvent.collectLatest { name->
            onNavigate(VIDEO_SCREEN+"/${name}")
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.roomName.value.text,
            onValueChange = viewModel::onRoomEnter,
            placeholder = {Text("Enter room name")},
            isError = viewModel.roomName.value.error != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        viewModel.roomName.value.error?.let { error ->
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = viewModel::onJoinRoom) {
            Text("Join Room")
        }

    }
}