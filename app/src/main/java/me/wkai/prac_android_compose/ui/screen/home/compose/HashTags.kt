package me.wkai.prac_android_compose.ui.screen.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun HashTags() {
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