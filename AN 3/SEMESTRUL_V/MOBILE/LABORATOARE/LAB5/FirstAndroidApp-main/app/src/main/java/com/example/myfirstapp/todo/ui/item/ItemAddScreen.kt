package com.example.myfirstapp.todo.ui.item

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirstapp.core.Result
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myfirstapp.core.TAG
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemAddScreen(itemId: String?, onClose: () -> Unit){
    val itemViewModel = viewModel<ItemViewModel>(factory = ItemViewModel.Factory(itemId))
    val itemUiState = itemViewModel.uiState

    var title by rememberSaveable { mutableStateOf("") }
    var artist by rememberSaveable { mutableStateOf("") }
    var duration by rememberSaveable { mutableStateOf("") }
    var dateOfRelease by rememberSaveable { mutableStateOf("") }
    var hasFeaturedArtists by rememberSaveable { mutableStateOf(false) }
    // transform in double la itemViewModel.update ....


    Log.d("ItemAddScreen", "recompose, text = $title")

    LaunchedEffect(itemUiState.submitResult) {
        Log.d("ItemScreen", "Submit = ${itemUiState.submitResult}");
        if (itemUiState.submitResult is Result.Success) {
            Log.d("ItemScreen", "Closing screen");
            onClose();
        }
    }
    
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "Add Song") },
                    actions = {
                        Button(onClick = {
                            Log.d("ItemScreen", "save item text = $title");
                            itemViewModel.saveItem(title, artist, duration.toDouble(), dateOfRelease, hasFeaturedArtists)
                        }) { Text("Save") }
                    })
        }
    )
    {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ){
            if (itemUiState.loadResult is Result.Loading) {
                CircularProgressIndicator()
                return@Scaffold
            }
            if (itemUiState.submitResult is Result.Loading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { LinearProgressIndicator() }
            }
            if (itemUiState.loadResult is Result.Error) {
                Text(text = "Failed to load item - ${(itemUiState.loadResult as Result.Error).exception?.message}")
            }
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it }, label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = artist,
                    onValueChange = { artist = it }, label = { Text("Artist") },
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = duration,
                    onValueChange = { duration = it }, label = { Text("Duration") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Row{
                    val calendar = Calendar.getInstance()
                    calendar.set(2023, 10, 25)

                    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)

                    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)

                    DatePicker(state = datePickerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )

                    Log.d(TAG, "formatting date....");
                    dateOfRelease = formatter.format(datePickerState.selectedDateMillis)
                }

                Row{
                    RadioButton(
                        selected = hasFeaturedArtists == true,
                        onClick = { hasFeaturedArtists = true }
                    )
                    Text("Has Featured Artists: True")
                }

                Row{
                    RadioButton(
                        selected = hasFeaturedArtists == false,
                        onClick = { hasFeaturedArtists = false }
                    )
                    Text("Has Featured Artists: False")
                }
            }

//            Column {
//                RadioButton(
//                    selected = hasFeaturedArtists == true,
//                    onClick = { hasFeaturedArtists = true }
//                    )
//                Text("Has Featured Artists: True")
//
//                RadioButton(
//                    selected = hasFeaturedArtists == false,
//                    onClick = { hasFeaturedArtists = false }
//                    )
//                Text("Has Featured Artists: False")
//            }

            if (itemUiState.submitResult is Result.Error) {
                Text(
                    text = "Failed to submit item - ${(itemUiState.submitResult as Result.Error).exception?.message}",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}



