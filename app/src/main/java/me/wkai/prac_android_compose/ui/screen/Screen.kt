package me.wkai.prac_android_compose.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 各頁面
 * 圖標參考: https://fonts.google.com/icons?selected=Material+Icons
 */
sealed class Screen(
	val route:String,
	val text:String,
	val icon:ImageVector
) {
	object HomeScreen : Screen("home", "首頁", Icons.Default.Home)
	object CardMemoryGameScreen : Screen("card_memory_game", "卡片記憶遊戲", Icons.Default.Games)
	object EvalScreen : Screen("eval", "字串計算器", Icons.Default.Calculate)

	companion object {
		val Screens = listOf(
			HomeScreen,
			CardMemoryGameScreen,
			EvalScreen,
		)
	}
}