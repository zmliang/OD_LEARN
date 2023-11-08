package com.pos.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pos.R
import com.pos.base.TimeCapsule
import com.pos.repository.entity.Todo
import com.pos.util.debugInputPointer

@Composable
fun mainScreen(
    viewModel: MainViewModel
){
    val state by viewModel.state.collectAsState()

    Column {
        //Render toolbar
        Toolbar(viewModel.timeMachine)
        //Render screen content
        when {
            state.isLoading -> ContentWithProgress()
            state.data.isNotEmpty() -> MainScreenContent(
                state.data,
                state.isShowAddDialog,
                onItemCheckedChanged = { index, isChecked -> viewModel.onItemCheckedChanged(index, isChecked) },
                onAddButtonClick = { viewModel.changeAddDialogState(true) },
                onDialogDismissClick = { viewModel.changeAddDialogState(false) },
                onDialogOkClick = { text -> viewModel.addNewItem(text) },
            )
        }
    }
}

@Composable
private fun Toolbar(timeMachine: TimeCapsule<MainScreenState>) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .background(color = Color.Blue)
            .fillMaxWidth()
            .debugInputPointer(LocalContext.current, timeMachine),//自定义扩展方法
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .padding(start = 16.dp),
            text = stringResource(id = R.string.main_screen_title),
            color = Color.White,
            fontSize = 18.sp,
            style = TextStyle(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
private fun MainScreenContent(
    todos: List<MainScreenItem>,
    isShowAddDialog: Boolean,
    onItemCheckedChanged: (Int, Boolean) -> Unit,
    onAddButtonClick: () -> Unit,
    onDialogDismissClick: () -> Unit,
    onDialogOkClick: (String) -> Unit,
) {
    Box {
        LazyColumn(content = {
            itemsIndexed(todos) { index, item ->
                when (item) {
                    is MainScreenItem.MainScreenTodoItem -> {
                        TodoListItem(item = item, onItemCheckedChanged, index)
                    }
                    is MainScreenItem.MainScreenAddButtonItem -> {
                        AddButton(onAddButtonClick)
                    }
                }
            }
        })

        if (isShowAddDialog) {
            AddNewItemDialog(onDialogDismissClick, onDialogOkClick)
        }
    }
}

@Composable
private fun TodoListItem(
    item: MainScreenItem.MainScreenTodoItem,
    onItemCheckedChanged: (Int, Boolean) -> Unit,
    index: Int,
) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            colors = CheckboxDefaults.colors(Color.Blue),
            checked = item.isChecked,
            onCheckedChange = {
                onItemCheckedChanged(index, !item.isChecked)
            }
        )
        Text(
            text = item.text,
            modifier = Modifier.padding(start = 16.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = 14.sp
            )
        )
    }
}

@Composable
private fun AddButton(
    onAddButtonClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.icon_plus_blue),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .align(Alignment.Center)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onAddButtonClick
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddNewItemDialog(
    onDialogDismissClick: () -> Unit,
    onDialogOkClick: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = { },
        text = {
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Blue,
                    disabledIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Blue,
                    containerColor = Color.LightGray,
                )
            )
        },
        confirmButton = {
            Button(
                onClick = { onDialogOkClick(text) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(text = "Ok", style = TextStyle(color = Color.White, fontSize = 12.sp))
            }
        }, dismissButton = {
            Button(
                onClick = onDialogDismissClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(text = "Cancel", style = TextStyle(color = Color.White, fontSize = 12.sp))
            }
        }
    )
}

@Composable
private fun ContentWithProgress() {
    Surface(color = Color.LightGray) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

data class MyState(
    val isShowProgressBar: Boolean,
    val error: Throwable? = null,
    val data: List<Todo>
)