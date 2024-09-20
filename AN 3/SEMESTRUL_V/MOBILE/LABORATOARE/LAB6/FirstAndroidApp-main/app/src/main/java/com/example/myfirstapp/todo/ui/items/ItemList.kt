package com.example.myfirstapp.todo.ui.items

import android.app.Application
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirstapp.todo.data.Song
import com.example.myfirstapp.todo.utils.MyNetworkStatusViewModel

typealias OnItemFn = (id: String?) -> Unit

@Composable
fun ItemList(songList: List<Song>, onItemClick: OnItemFn, modifier: Modifier) {
    Log.d("ItemList", "recompose")
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        items(songList) { item ->
            ItemDetail(item, onItemClick)
        }
    }
}

@Composable
fun ItemDetail(song: Song, onItemClick: OnItemFn) {
    Log.d("ItemDetail", "recompose id = ${song._id}, title: ${song.title}")
    var isExpanded by remember { mutableStateOf(false) }

    Row {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onItemClick(song._id) }
        ) {
            Text(
                text = song.title,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = song.artist,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                )
            )
            Text(
                text = "Duration: ${song.duration}",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            )
            Text(
                text = "DateOfRelease: ${song.dateOfRelease}",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            )
            Text(
                text = "IsSaved: ${song.isSaved}",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            )
        }
    }
    Surface(modifier = Modifier
        .fillMaxWidth(),
        onClick = { isExpanded = !isExpanded }) {
        Column {
            Row {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Show latitude & longitude",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Latitude: ${song.lat}, Longitude: ${song.lon}",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
