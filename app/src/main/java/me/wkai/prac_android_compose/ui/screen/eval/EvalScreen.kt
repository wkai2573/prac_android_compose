package me.wkai.prac_android_compose.ui.screen.eval

import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat

@Composable
fun EvalScreen() {

	var code by remember {
		mutableStateOf(
			"""
				val a = 1 + 2
				val b = a * 10 + 5
				val c = b / 5
				val d = a + b - c
				val e=a*b+c/d
			""".trimIndent()
		)
	}

	var ans by remember { mutableStateOf<Map<String, String>>(mapOf()) }

	Column(
		modifier = Modifier.fillMaxSize()
	) {
		TextField(
			modifier = Modifier.weight(1f).fillMaxWidth(),
			value = code,
			onValueChange = {
				code = it
			},
		)

		Button(
			modifier = Modifier.fillMaxWidth(),
			onClick = {
				ans = calc(code = code)
			}) {
			Text(text = "計算", style = MaterialTheme.typography.h5)
		}

		Text(
			modifier = Modifier.weight(1f),
			text = "結果:\n${
				ans
					.map { "${it.key} : ${it.value}" }
					.joinToString("") { it + "\n" }
			}",
			style = MaterialTheme.typography.h6
		)
	}
}

/**
 * 計算程式碼變數
 *
 * @param code 程式碼
 * @param variables 傳入其他變數數值
 *
 * @return 回傳map結果
 */
private fun calc(
	code:String,
	variables:MutableMap<String, String> = mutableMapOf(),
):Map<String, String> {
	val ans = variables.toMutableMap()

	code.split(Regex("\n")).forEach { line ->
		runCatching {
			val list = line.split("=")
			val valName = list[0].removePrefix("val").removePrefix("var").trim()
			var formula = list[1]
			ans.forEach {
				formula = formula.replace(regex = Regex("(?<=\\W|^)${it.key}(?=\\W|\$)"), replacement = it.value)
			}
			runCatching {
				ExpressionBuilder(formula).build().evaluate()
			}.onSuccess {
				ans[valName] = DecimalFormat("0.#").format(it)
			}.onFailure {
				ans[valName] = it.message ?: ""
			}
		}
	}

	return ans
}