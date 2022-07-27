package me.wkai.prac_android_compose.ui.screen.home

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import me.wkai.prac_android_compose.R
import me.wkai.prac_android_compose.ui.screen.home.compose.HashTags
import me.wkai.prac_android_compose.util.AppService
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
			},
		)

		Button(
			content = { Text(text = "發送廣播(其他app接收處理)") },
			onClick = {
				Intent().also { intent ->
					intent.action = "me.wkai.NOTICE_ME_SENPAI"
					intent.putExtra("data", "Notice me senpai!")
					context.sendBroadcast(intent)
				}
			})
	}
}