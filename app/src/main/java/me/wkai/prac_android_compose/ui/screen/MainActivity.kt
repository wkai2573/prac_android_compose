package me.wkai.prac_android_compose.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.wkai.prac_android_compose.R
import me.wkai.prac_android_compose.util.AppBroadcastReceiver
import me.wkai.prac_android_compose.ui.compose.Drawer
import me.wkai.prac_android_compose.ui.screen.card_memory_game.CardMemoryGameScreen
import me.wkai.prac_android_compose.ui.screen.eval.EvalScreen
import me.wkai.prac_android_compose.ui.screen.home.HomeScreen
import me.wkai.prac_android_compose.ui.theme.AppTheme
import me.wkai.prac_android_compose.util.AppService

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState:Bundle?) {
		super.onCreate(savedInstanceState)

		//註冊廣播
		AppBroadcastReceiver.init(this)

		//啟用服務
		AppService.init(this)

		setContent {
			AppTheme {
				MainContent()
			}
		}
	}
}

@Composable
private fun MainContent() {

	val scope = rememberCoroutineScope()
	val scaffoldState = rememberScaffoldState() //鷹架state
	val navController = rememberNavController() //導航state

	Scaffold(
		scaffoldState = scaffoldState,
		topBar = {
			TopAppBar(
				title = { Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.h5) },
				backgroundColor = MaterialTheme.colors.primary,
				navigationIcon = {
					IconButton(
						onClick = { scope.launch { scaffoldState.drawerState.open() } },
						content = { Icon(imageVector = Icons.Default.Menu, contentDescription = "Drawer") },
					)
				}
			)
		},
		drawerContent = { Drawer(scaffoldState.drawerState, navController = navController) },
	) { padding ->
		NavHost(
			navController = navController,
			startDestination = Screen.HomeScreen.route,
			modifier = Modifier.padding(padding),
		) {
			composable(route = Screen.HomeScreen.route) {
				HomeScreen(navController = navController)
			}
			composable(route = Screen.CardMemoryGameScreen.route) {
				CardMemoryGameScreen()
			}
			composable(route = Screen.EvalScreen.route) {
				EvalScreen()
			}
		}
	}
}
