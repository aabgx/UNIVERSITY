package com.example.myfirstapp.todo.ui.items

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.res.stringResource
import com.example.myfirstapp.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfirstapp.core.Result
import com.example.myfirstapp.todo.data.Song
import com.example.myfirstapp.todo.utils.MyNetworkStatusViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScreen(onItemClick: (id: String?) -> Unit, onAddItem: () -> Unit, onLogout: () -> Unit){
    Log.d("ItemsScreen", "Recompose")
    val itemsViewModel = viewModel<ItemsViewModel>(factory = ItemsViewModel.Factory)
    val itemsUiState by itemsViewModel.uiState.collectAsStateWithLifecycle(
        initialValue = listOf()
    )

//    val myNewtworkStatusViewModel = viewModel<MyNetworkStatusViewModel>(
//        factory = MyNetworkStatusViewModel.Factory(
//            LocalContext.current.applicationContext as Application
//        )
//    )
//
//    LaunchedEffect(myNewtworkStatusViewModel.uiState){
//        if(myNewtworkStatusViewModel.uiState){
//            myNewtworkStatusViewModel.startJob()
//        }
//    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.items)) },
                actions = {
                    Button(onClick = onLogout) { Text("Logout") }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Log.d("ItemsScreen", "add")
                    onAddItem()
                },
            ) { Icon(Icons.Rounded.Add, "Add") }
        }
    ){
        ItemList(songList = itemsUiState, onItemClick = onItemClick, modifier = Modifier.padding(it))
    }
}

@Preview
@Composable
fun PreviewItemsScreen() {
    ItemsScreen(onItemClick = {}, onAddItem = {}, onLogout = {})
}