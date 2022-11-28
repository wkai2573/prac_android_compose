package me.wkai.prac_android_compose.ui.screen.home

import android.content.*
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.wkai.prac_android_compose.ui.screen.home.compose.HashTags


@Composable
fun HomeScreen(
	scaffoldState:ScaffoldState,
	navController:NavHostController,
	homeVM:HomeViewModel = hiltViewModel(),
) {

	val context = LocalContext.current

	LaunchedEffect(key1 = true) {
		//ui事件處理
		homeVM.eventFlow.collectLatest { event ->
			when (event) {
				//小吃提示
				is HomeViewModel.UiEvent.ShowSnackbar -> {
					scaffoldState.snackbarHostState.showSnackbar(message = event.message)
				}
			}
		}
	}

	// ui
	Column {
		Text(
			text = "Home",
			style = MaterialTheme.typography.h4,
			textAlign = TextAlign.Center,
			modifier = Modifier.fillMaxWidth(),
		)

		Divider()

		Buttons(homeVM)

		Divider()

		HashTags()
	}
}

@Composable
private fun Buttons(homeVM:HomeViewModel) {
	val context = LocalContext.current
	val scope = rememberCoroutineScope()

	FlowRow(
		modifier = Modifier.padding(8.dp),
		crossAxisSpacing = 0.dp,
		mainAxisSpacing = 8.dp
	) {
		Button(
			content = { Text(text = "發出通知") },
			onClick = {
				homeVM.sendNotice(
					context = context,
					title = "通知標題",
					content = "通知內容，收合時的內容，這裡不能放超過一行",
					detailContent = """
						更多的內容，展開後會看到的內容
						(顯示detailContent時，不會顯示content)
						第三行
						第四行
					""".trimIndent()
				)
			})

		Button(
			content = { Text(text = "發送廣播(其他app接收處理)") },
			onClick = {
				homeVM.sendBroadcastToOtherApp(context)
			})

		Button(
			content = { Text(text = "取得其他App資料(失敗)") },
			onClick = {
				scope.launch(Dispatchers.IO) {
					val cursor = context.contentResolver.query(
						Uri.parse("content://me.wkai.prac_character/chara"),
						null, null, null, null
					)
					if (cursor == null) {
						Log.i("@@@", "QQ")
						return@launch
					}
					val list = mutableListOf<String>()
					while (cursor.moveToNext()) {
						list.add(cursor.getString(0))
					}
					cursor.close()

					Log.i("@@@", list.toString())
				}
			})

		Button(
			content = { Text(text = "開啟其他App(使用Sharesheet)") },
			onClick = {
				val sendIntent:Intent = Intent().apply {
					action = Intent.ACTION_SEND
					putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
					type = "text/plain"
				}
				val shareIntent = Intent.createChooser(sendIntent, null)
				context.startActivity(shareIntent)
			})
	}
}