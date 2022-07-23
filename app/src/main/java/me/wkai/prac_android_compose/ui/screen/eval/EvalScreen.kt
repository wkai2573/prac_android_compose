package me.wkai.prac_android_compose.ui.screen.eval

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.evgenii.jsevaluator.JsEvaluator
import com.evgenii.jsevaluator.interfaces.JsCallback
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat

@Composable
fun EvalScreen(
	viewModel:EvalViewModel = hiltViewModel()
) {

//	var code by remember {
//		mutableStateOf(
//			"""
//				val a = 1 + 2
//				val b = a * 10 + 5
//				val c = b / 5
//				val d = a + b - c
//				val e=a*b+c/d
//				val f= 0/0
//				val g= abcde
//			""".trimIndent()
//		)
//	}
	var code by remember {
		mutableStateOf(
			"""
				let a = 3
				a *= 3                 // a=9
				let b = Math.sqrt(2*8) // b=4
				let c = a > 5 ? 50 : b // c=50
				
				const average = nums => 
					nums.reduce((p, c) => p + c) / nums.length
				
				let d = average([a,b,c]) // 63/3
				d
			""".trimIndent()
		)
	}

	var ansMap by remember { mutableStateOf<Map<String, String>>(mapOf()) }
	val ansStr = remember { mutableStateOf("") }

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

		val context = LocalContext.current
		val focusManager = LocalFocusManager.current
		Row {
			Button(
				modifier = Modifier.weight(1f),
				onClick = {
					focusManager.clearFocus()
					ansMap = exp4j_calc(code = code)
					ansStr.value = ansMap
						.map { "${it.key} : ${it.value}" }
						.joinToString("") { it + "\n" }
				}) {
				Text(text = "exp4j", style = MaterialTheme.typography.h5)
			}
			Button(
				modifier = Modifier.weight(1f),
				onClick = {
					focusManager.clearFocus()
					jsEvaluator_eval(
						context = context,
						jsCode = code,
						ansState = ansStr,
					)
				}) {
				Text(text = "jsEvaluator", style = MaterialTheme.typography.h5)
			}
		}

		val scroll = rememberScrollState()
		Text(
			modifier = Modifier.weight(1f).verticalScroll(scroll),
			text = ansStr.value,
			style = MaterialTheme.typography.h6
		)
	}
}

/**
 * exp4j 計算程式碼變數
 *
 * @param code 程式碼
 * @param variables 傳入其他變數數值
 *
 * @return 回傳map結果
 */
private fun exp4j_calc(
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
				ans[valName] = DecimalFormat("0.##########").format(it)
			}.onFailure {
				ans[valName] = it.message ?: "Unknown error"
			}
		}
	}

	return ans
}

/**
 * js模擬器
 */
private fun jsEvaluator_eval(
	context:Context,
	jsEvaluator:JsEvaluator = JsEvaluator(context),
	jsCode:String,
	ansState:MutableState<String>,
) {
	jsEvaluator.evaluate(jsCode, object : JsCallback {
		override fun onResult(result:String) {
			ansState.value = result
		}

		override fun onError(errorMessage:String) {
			ansState.value = errorMessage
		}
	})
}