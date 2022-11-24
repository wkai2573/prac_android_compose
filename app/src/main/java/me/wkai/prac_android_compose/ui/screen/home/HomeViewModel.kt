package me.wkai.prac_android_compose.ui.screen.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import me.wkai.prac_android_compose.util.Notice
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

	// ==參數==
	// ...

	// ==ui事件流==
	private val _eventFlow = MutableSharedFlow<UiEvent>()
	val eventFlow = _eventFlow.asSharedFlow()

	// ==定義ui事件==
	sealed class UiEvent {
		data class ShowSnackbar(val message:String) : UiEvent()
	}

	// ==邏輯事件(方法)==

	// 傳送通知
	fun sendNotice(context:Context, title:String, content:String, detailContent:String) = viewModelScope.launch {
		Notice.notice(
			context = context,
			notice = Notice.ClickNotice,
			title = title,
			content = content,
			detailContent = detailContent
		)
		_eventFlow.emit(UiEvent.ShowSnackbar("已發出通知: $title"))
	}

	// 發送廣播(其他app接收處理)
	fun sendBroadcastToOtherApp(context:Context) {
		Intent().also { intent ->
			intent.action = "me.wkai.NOTICE_ME_SENPAI"
			intent.putExtra("data", "Notice me senpai!")
			context.sendBroadcast(intent)
		}
	}

	// 註冊偵測網路改變
	fun initDetectionNetwork(context:Context) {
		val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
			override fun onAvailable(network:Network) {
				viewModelScope.launch {
					_eventFlow.emit(HomeViewModel.UiEvent.ShowSnackbar("有網路啦 $network"))
				}
			}

			override fun onLost(network:Network) {
				viewModelScope.launch {
					_eventFlow.emit(UiEvent.ShowSnackbar("網路斷線啦 $network"))
				}
			}
		})
	}

	// ==初始化==
	init {
	}
}