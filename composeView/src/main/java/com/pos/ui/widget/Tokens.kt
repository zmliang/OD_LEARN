package com.pos.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.math.BigDecimal

data class Token(
    val symbol:String,
    val icon:String,
    val contract:String?,
    val dexContract:String?,
    var price:BigDecimal?,
    var percentage:Float?
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TokenList(tokens : List<Token>){

    LazyColumn(){
        stickyHeader {
            Text(text = "这是吸顶的title")
        }

        items(tokens){
            Text(text = it.symbol+"/USDT")
        }


    }

}