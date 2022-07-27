package me.wkai.prac_android_compose.util

import android.app.Service
import android.content.*
import android.os.*
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint

const val MSG_REGISTER_CLIENT = 1
const val MSG_UNREGISTER_CLIENT = 2

//服務
@AndroidEntryPoint
class AppService : Service() {

	//服務處理(伺服端): 當訊息傳至伺服端時
	private val messengerServer by lazy {
		Messenger(ServiceHandler(Looper.getMainLooper()))
	}

	private inner class ServiceHandler(looper:Looper) : Handler(looper) {
		override fun handleMessage(msgFromClient:Message) {
			when (msgFromClient.what) {
				MSG_REGISTER_CLIENT -> {
					// 接收到客戶端訊息後
					Log.i("@@@", "服務_伺服端_handle: ${msgFromClient.data.getString("param")}")
					Toast.makeText(applicationContext, "MSG_REGISTER_CLIENT", Toast.LENGTH_SHORT).show()
					// 回傳資料給客戶端
					val msgToClient:Message = Message.obtain(msgFromClient)
					msgToClient.data = Bundle().apply{
						putString("param", "伺服端msg參數")
					}
					msgFromClient.replyTo.send(msgToClient);
				}
				MSG_UNREGISTER_CLIENT -> {
					Toast.makeText(applicationContext, "MSG_UNREGISTER_CLIENT", Toast.LENGTH_SHORT).show()
				}
				else -> super.handleMessage(msgFromClient)
			}

			// stopSelf(msg.arg1) //停止服務
		}
	}

	//1當服務建立
	// private var serviceLooper:Looper? = null
	// private var serviceHandler:ServiceHandler? = null
	// override fun onCreate() {
	// 	Log.i("@@@", "服務_伺服端_onCreate")
	// 	HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
	// 		start()
	// 		serviceLooper = looper
	// 		serviceHandler = ServiceHandler(looper)
	// 	}
	// }

	//2當服務啟動
	override fun onStartCommand(intent:Intent, flags:Int, startId:Int):Int {
		Log.i("@@@", "服務_伺服端_onStartCommand")
		return START_STICKY // 如果程序被殺，恢復時，重新啟動
	}

	//3當服務綁定: 由其他app呼叫
	override fun onBind(intent:Intent):IBinder? {
		Log.i("@@@", "服務_伺服端_onBind: ${intent.getStringExtra("param")}")
		return messengerServer.binder
	}

	//4當服務結束
	// override fun onDestroy() {
	// 	Log.i("@@@", "服務_伺服端_onDestroy")
	// }

	companion object {
		//初始化服務
		fun init(context:Context) {
			val intent = Intent(context, AppService::class.java)
			context.startService(intent)
		}
	}
}