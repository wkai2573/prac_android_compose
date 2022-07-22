package me.wkai.prac_android_compose.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun Home(navController:NavHostController) {
	Box(modifier = Modifier.fillMaxSize()) {
		Text(
			text = "Home",
			style = MaterialTheme.typography.h4,
			modifier = Modifier.align(Alignment.Center)
		)
	}
}