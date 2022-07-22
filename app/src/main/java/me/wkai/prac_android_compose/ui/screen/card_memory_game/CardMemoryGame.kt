package me.wkai.prac_android_compose.ui.screen.card_memory_game

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//編號, 數字, 已完成
data class NumberCard(val id:Int, val number:Int, var isDone:Boolean = false)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardMemoryGame() {
	val scope = rememberCoroutineScope()

	//建立數字1~5 2次
	val nums = (1..5).toMutableList()
	nums.addAll(1..5)

	//建立卡片
	val cards = nums
		.shuffled()
		.mapIndexed { i, n -> NumberCard(i, n) }
		.toMutableStateList()

	//選擇的卡片
	val selCards = listOf<NumberCard>().toMutableStateList()

	LazyVerticalGrid(cells = GridCells.Fixed(3)) {
		items(cards) { card ->
			var text = ""
			var bgc = Color(0xFFFFB241)
			var modifier = Modifier.padding(4.dp)

			if (card.isDone || selCards.contains(card)) {
				//翻開
				text = "${card.number} ${if (card.isDone) "✔" else ""}"
				bgc = Color(0xFFFFF7E5)
			} else {
				//不翻開
				if (selCards.size < 2) {
					//點擊
					modifier = modifier.clickable {
						selCards.add(card)
						if (selCards.size == 2) {
							if (selCards[0].number == selCards[1].number) {
								selCards.forEach { it.isDone = true }
								selCards.clear()
							} else {
								scope.launch {
									delay(1000L)
									selCards.clear()
								}
							}
						}
					}
				}
			}

			Card(
				modifier = modifier,
				backgroundColor = bgc
			) {
				Text(
					text = text,
					modifier = Modifier.padding(vertical = 20.dp),
					textAlign = TextAlign.Center
				)
			}
		}
	}
}