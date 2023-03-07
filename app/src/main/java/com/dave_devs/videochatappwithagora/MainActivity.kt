package com.dave_devs.videochatappwithagora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dave_devs.videochatappwithagora.agora_chat.Constant.ROOM_SCREEN
import com.dave_devs.videochatappwithagora.agora_chat.Constant.VIDEO_SCREEN
import com.dave_devs.videochatappwithagora.agora_chat.RoomScreen
import com.dave_devs.videochatappwithagora.agora_chat.VideoScreen
import com.dave_devs.videochatappwithagora.ui.theme.VideoChatAppWithAgoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoChatAppWithAgoraTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ROOM_SCREEN
                ) {
                    composable(ROOM_SCREEN) {
                        RoomScreen(onNavigate = navController::navigate)
                    }
                    composable(
                        "$VIDEO_SCREEN/{roomName}",
                        arguments = listOf(
                            navArgument(name = "roomName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val roomName = it.arguments?.getString("roomName") ?: return@composable
                        VideoScreen(
                            roomName = roomName,
                            onNavigateUP = navController::navigateUp
                        )
                    }
                }
            }
        }
    }
}