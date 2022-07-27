package me.wkai.prac_android_compose.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint

//廣播_低電量提示toast & 通知
@AndroidEntryPoint
class AppBroadcastReceiver : BroadcastReceiver() {

	//當接收廣播
	override fun onReceive(context:Context, intent:Intent) {
		val msg = """
			data:   ${intent.getStringExtra("data")}
			Action: ${intent.action}
			URI:    ${intent.toUri(Intent.URI_INTENT_SCHEME)}
		""".trimIndent()
		Toast.makeText(context, "低電量吐司\n$msg", Toast.LENGTH_LONG).show()
		Notice.notice(
			context = context,
			notice = Notice.ClickNotice,
			title = "低電電",
			content = "你的電很低，快充電R",
		)
	}

	companion object {
		//初始化，在activity onCreate呼叫
		fun init(context: Context):AppBroadcastReceiver {
			val br = AppBroadcastReceiver()
			val filter = IntentFilter().apply {
				//可接收的廣播
				addAction(Intent.ACTION_BATTERY_LOW)
			}
			context.registerReceiver(br, filter)
			return br
		}
	}
}
