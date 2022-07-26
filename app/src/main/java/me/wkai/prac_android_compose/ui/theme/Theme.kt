package me.wkai.prac_android_compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
	primary = Primary,
	primaryVariant = DarkPrimary,
	secondary = OnPrimary
)

private val LightColorPalette = lightColors(
	primary = Primary,
	primaryVariant = DarkPrimary,
	onPrimary = OnPrimary,

	secondary = Secondary,
	onSecondary = OnSecondary,

	surface = Surface,
	onSurface = OnSurface,

	background = White,
	onBackground = Black,
)

@Composable
fun AppTheme(darkTheme:Boolean = isSystemInDarkTheme(), content:@Composable () -> Unit) {
//	val colors = if (darkTheme) DarkColorPalette else LightColorPalette
	val colors = LightColorPalette

	MaterialTheme(
		colors = colors,
		typography = typography,
		shapes = Shapes,
		content = content
	)
}