package com.pos.ui.pages

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.pos.ui.widget.InfiniteCarousel
import com.pos.ui.widget.PullRefresh
import com.pos.ui.widget.Resident
import com.pos.ui.widget.Token
import com.pos.ui.widget.TokenList
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home() {
    val tokens = listOf(
        Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
        Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
        Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
        Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
        Token("BTC", "ICON", "CONTRACT", "DEXCONTRACT", null, null),
    )

    LazyColumn(modifier = Modifier.fillMaxSize()){

        item{
            InfiniteCarousel()
        }
        item{
            Resident()
        }

        stickyHeader {
            Box(modifier = Modifier.height(50.dp).fillMaxWidth().background(Color.Green)){
                Text(text = "这是吸顶的title")
            }
        }

        allTokens(tokens)

    }

//
//    PullRefresh(
//        onRefresh = {
//            delay(2000)
//        },
//        loadingIndicator = {
//            Box(modifier = Modifier.padding(10.dp)) {
//                CircularProgressIndicator(Modifier.size(200.dp))
//            }
//        }
//    ) {
//
//        Column(modifier = Modifier.pointerInput(Unit) {
//
//        }) {
//
//            InfiniteCarousel()
//
//            Resident()
//
//            TokenList(tokens = tokens)
//        }
//
//    }

}

fun LazyListScope.allTokens(tokens:List<Token>){

    items(tokens){
        Box(modifier = Modifier.height(200.dp)){
            Text(text = it.symbol+"/USDT")
        }
    }
}