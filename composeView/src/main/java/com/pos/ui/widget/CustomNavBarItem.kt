package com.pos.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.simulateHotReload
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.pos.R


@Preview
@Composable
fun CustomNavBarItem(
    label: String = "主页",
    isSelected: Boolean = true,
    @DrawableRes id:Int
) {

    Column(
        modifier = Modifier
            .height(100.dp)
            .wrapContentWidth(
                align = Alignment.CenterHorizontally,
                unbounded = false
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = if (isSelected) R.drawable.home_sel else  R.drawable.home_nor),
            contentDescription = "",
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            fontSize = 24.sp,
            color = if (isSelected) Color("#5D37FF".toColorInt()) else Color("#B0C4DE".toColorInt()),
            modifier = Modifier.fillMaxWidth(1.0f),
            textAlign = TextAlign.Center,
            text = label
        )
    }

}