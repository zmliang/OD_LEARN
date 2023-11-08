package com.pos.ui.page

sealed class MainScreenItem {
    object MainScreenAddButtonItem : MainScreenItem()
    data class MainScreenTodoItem(
        val isChecked: Boolean,
        val text: String,
    ) : MainScreenItem()
}