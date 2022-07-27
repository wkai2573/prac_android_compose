package me.wkai.prac_android_compose.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import me.wkai.prac_android_compose.R

sealed class Notice(
	val id:Int,
	val name:String,
	val description:String
) {
	object ClickNotice : Notice(1, "點擊通知", "點擊時通知")
	object RegularNotice : Notice(2, "例行通知", "時間到時通知")

	companion object {
		//建立&註冊頻道
		private fun createNotificationChannel(context:Context, notice:Notice) {
			val channel = NotificationChannel(
				notice.name,
				notice.name,
				NotificationManager.IMPORTANCE_DEFAULT
			).apply {
				description = notice.description
			}
			val notificationManager:NotificationManager =
				context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.createNotificationChannel(channel)
		}

		//通知
		fun notice(
			context:Context,
			notice:Notice,
			title:String = "",
			content:String = "",
			detailContent:String? = null,
		) {
			createNotificationChannel(context, notice)

			val builder = NotificationCompat.Builder(context, notice.name)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)     //通知優先級
				.setSmallIcon(R.drawable.ic_baseline_location_on_24)
				.setContentTitle(title)
				.setContentText(content)
				.also { builder ->
					detailContent?.let { it ->
						builder.setStyle(NotificationCompat.BigTextStyle().bigText(it))
					}
				}

			val manager = NotificationManagerCompat.from(context)
			manager.notify(notice.id, builder.build())
		}
	}
}