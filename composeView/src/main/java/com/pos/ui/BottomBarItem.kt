package com.pos.ui

import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

enum class BottomBarItem(
    val route:String,
    val label: String,
    val icon: @Composable () -> Unit,
) {
    HOME("HOME","首页", { Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Home, contentDescription = "") }),
    ASSET("ASSET","资产", { Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Face, contentDescription = "") }),
    DISCOVER("DISCOVER","发现", { Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Person, contentDescription = "") }),
    ME("PROFILE","我的", { Icon(imageVector = androidx.compose.material.icons.Icons.Filled.Lock, contentDescription = "") }),
}