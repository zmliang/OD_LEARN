package com.pos.ui.widget

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.math.MathUtils.lerp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


//@OptIn(ExperimentalFoundationApi::class)
@OptIn(ExperimentalFoundationApi::class)

@Composable
fun InfiniteCarousel(){

    val pageCount = Int.MAX_VALUE

    val max_number_of_rounds = Int.MAX_VALUE / 5

    val first_page_index = ( max_number_of_rounds / 2 ) * 5

    val pagerState = rememberPagerState(initialPage = first_page_index)
    {
        pageCount
    }

    with(pagerState) {

        if (pageCount > 0) {
            var currentPageKey by remember { mutableIntStateOf(0) }
            LaunchedEffect(key1 = currentPageKey) {
                launch {
                    delay(timeMillis = 3000)
                    val nextPage = (currentPage + 1).mod(pageCount)
                    animateScrollToPage(
                        page = nextPage,
                    )
                    currentPageKey = nextPage
                }
            }
        }
    }

    HorizontalPager(
        state = pagerState
    ) {
        page ->
        Card(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(1f)
                .padding(horizontal = 20.dp)
        ) {
            Box(modifier = Modifier
                .fillMaxSize(1f)
                .background(color = Color.Blue)){
                Text(text =(page%5).toString() , color = Color.White
                    , fontSize = 50.sp)
            }
        }
    }
    
}