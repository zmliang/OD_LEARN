package com.pos.ui

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.pos.R

enum class BottomBarItem(
    val route: String,
    val label: String,
    @DrawableRes val iconSrc: Int,
) {
    HOME("HOME", "首页", R.drawable.home_nor),
    ASSET("ASSET", "资产",  R.drawable.assets_nor),
    DISCOVER("DISCOVER", "发现", R.drawable.discover_nor),
    ME("PROFILE", "我的",  R.drawable.me_nor),
}