package me.wkai.prac_android_compose.ui.screen.home

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import me.wkai.prac_android_compose.broadcast.Notice

@Composable
fun HomeScreen(navController:NavHostController) {

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
		crossAxisSpacing = 10.dp,
		mainAxisSpacing = 8.dp
	) {
		Button(
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
			content = { Text(text = "通知") }
		)

		Button(onClick = {
			Intent().also { intent ->
				intent.action = "me.wkai.NOTICE_ME_SENPAI"
				intent.putExtra("data", "Notice me senpai!")
				context.sendBroadcast(intent)
			}
		}) {
			Text(text = "發送廣播")
		}
	}
}

@Composable
private fun HashTags() {
	FlowRow(
		modifier = Modifier.padding(8.dp),
		mainAxisAlignment = MainAxisAlignment.Center,
		mainAxisSize = SizeMode.Expand,
		crossAxisSpacing = 10.dp,
		mainAxisSpacing = 8.dp
	) {
		mapOf(
			"Cheetos" to Color(0xFFFFCDD2),
			"Beer" to Color(0xFFB2DFDB),
			"Milk" to Color(0xFFF8BBD0),
			"CocaCola" to Color(0xFFC8E6C9),
			"DairyProductts" to Color(0xFFE1BEE7),
			"Soda" to Color(0xFFDCEDC8),
			"Cake" to Color(0xFFD1C4E9),
			"Cheese" to Color(0xFFF0F4C3),
			"JohnnyWalkerBlack" to Color(0xFFC5CAE9),
			"Cigarettes" to Color(0xFFFFF9C4),
			"Cigars" to Color(0xFFBBDEFB),
			"Vodka" to Color(0xFFFFECB3),
			"Doritos" to Color(0xFFB3E5FC),
			"Nutella" to Color(0xFFFFE0B2),
		).forEach { (tag, color) ->
			Text(
				text = "#$tag",
				modifier = Modifier
					.background(
						color = color,
						shape = RoundedCornerShape(4.dp)
					)
					.padding(8.dp),
				overflow = TextOverflow.Ellipsis,
				maxLines = 1
			)
		}
	}
}