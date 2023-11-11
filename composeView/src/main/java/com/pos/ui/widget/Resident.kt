package com.pos.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pos.R

data class ResidentItem(
    val title: String,
    val url:String,
    @DrawableRes val icon: Int,
    @DrawableRes val subIcon: Int?=null,
)


@Preview()
@Composable
fun Resident(){
    val items:List<ResidentItem> = listOf(
        ResidentItem("TT销毁", "http://www.baidu.com", R.drawable.icon_tt_burn),
        ResidentItem("WTT", "http://www.baidu.com", R.drawable.wtt),
        ResidentItem("上链挖矿", "http://www.baidu.com", R.drawable.co_chain),
        ResidentItem("福利中心", "http://www.baidu.com", R.drawable.welfare_center),
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(100.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {

        items.forEach{item ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = item.icon),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(70.dp),
                    contentDescription = item.title)
                Text(text = item.title)
            }

        }

    }

}