package com.example.composeview

import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

enum class BottomBarItem(
    val label: String,
    val icon: @Composable () -> Unit,
) {
    HOME("首页", { Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Home, contentDescription = "") }),
    ASSET("资产", { Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Face, contentDescription = "") }),
    DISCOVER("发现", { Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Person, contentDescription = "") }),
    ME("我的", { Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Lock, contentDescription = "") }),
}