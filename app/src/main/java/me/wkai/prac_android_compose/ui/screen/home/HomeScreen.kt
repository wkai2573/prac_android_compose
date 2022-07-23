package me.wkai.prac_android_compose.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.evgenii.jsevaluator.JsEvaluator
import com.evgenii.jsevaluator.interfaces.JsCallback
import net.objecthunter.exp4j.ExpressionBuilder

@Composable
fun HomeScreen(navController:NavHostController) {
	Box(modifier = Modifier.fillMaxSize()) {
		Text(
			text = "Home",
			style = MaterialTheme.typography.h4,
			modifier = Modifier.align(Alignment.Center)
		)
	}
}