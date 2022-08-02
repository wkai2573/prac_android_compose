package me.wkai.prac_android_compose.ui.screen.home

import android.content.*
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.wkai.prac_android_compose.ui.screen.home.compose.HashTags
import me.wkai.prac_android_compose.util.Notice


@Composable
fun HomeScreen(
	viewModel:HomeViewModel = hiltViewModel(),
	navController:NavHostController
) {

	Column {
		Text(
			text = "Home",
			style = MaterialTheme.typography.h4,
			textAlign = TextAlign.Center,
			modifier = Modifier.fillMaxWidth(),
		)

		Divider()

		Buttons()

		Divider()

		HashTags()
	}
}

@Composable
private fun Buttons() {
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
				Notice.notice(
					context = context,
					notice = Notice.ClickNotice,
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
				Intent().also { intent ->
					intent.action = "me.wkai.NOTICE_ME_SENPAI"
					intent.putExtra("data", "Notice me senpai!")
					context.sendBroadcast(intent)
				}
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
				val sendIntent: Intent = Intent().apply {
					action = Intent.ACTION_SEND
					putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
					type = "text/plain"
				}
				val shareIntent = Intent.createChooser(sendIntent, null)
				context.startActivity(shareIntent)
			})
	}
}