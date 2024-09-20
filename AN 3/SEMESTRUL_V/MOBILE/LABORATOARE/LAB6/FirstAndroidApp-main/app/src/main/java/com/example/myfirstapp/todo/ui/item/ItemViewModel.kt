package com.example.myfirstapp.todo.ui.item

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.myfirstapp.MyFirstApplication
import com.example.myfirstapp.core.Result
import com.example.myfirstapp.core.TAG
import com.example.myfirstapp.todo.data.Song
import com.example.myfirstapp.todo.data.ItemRepository
import com.example.myfirstapp.todo.utils.MyNetworkStatusViewModel
import com.example.myfirstapp.todo.utils.MyWorker
import kotlinx.coroutines.launch
import java.util.UUID

data class ItemUiState(
    val itemId: String? = null,
    val song: Song = Song(),
    var loadResult: Result<Song>? = null,
    var submitResult: Result<Song>? = null,
)

class ItemViewModel(private val itemId: String?, private val itemRepository: ItemRepository) :
    ViewModel() {

    var uiState: ItemUiState by mutableStateOf(ItemUiState(loadResult = Result.Loading))
        private set

    init {
        Log.d(TAG, "init with id: ${itemId}")
        if (itemId != null) {
            loadItem()
        } else {
            uiState = uiState.copy(loadResult = Result.Success(Song()))
        }
    }

    fun loadItem() {
        viewModelScope.launch {
            itemRepository.songStream.collect { items ->
                if (!(uiState.loadResult is Result.Loading)) {
                    return@collect
                }
                Log.d(TAG, "searching for ${itemId}")
                val song = items.find { it._id == itemId } ?: Song()
                Log.d(TAG, "found ${song}")
                uiState = uiState.copy(song = song, loadResult = Result.Success(song))
            }
        }
    }

    fun saveItem(title: String, artist: String, duration: Double, dateOfRelease: String, hasFeaturedArtists: Boolean){
        viewModelScope.launch {
            Log.d(TAG, "save new song!!!");
            try{
                uiState = uiState.copy(submitResult = Result.Loading)
                val item = uiState.song.copy(title=title, duration = duration, artist = artist, dateOfRelease = dateOfRelease, hasFeaturedArtists = hasFeaturedArtists, _id = "")
                val savedSong: Song;
                savedSong = itemRepository.save(item)
                Log.d(TAG, "save song succeeeded!!!!");
                uiState = uiState.copy(submitResult = Result.Success(savedSong))
            }catch (e: Exception){
                Log.d(TAG, "saveOrUpdateItem failed");
                val nrUnsaved = itemRepository.getNrUnsaved()
                val currentId = nrUnsaved + 1
                uiState = uiState.copy(submitResult = Result.Error(e))
                val item = uiState.song.copy(title=title, duration = duration, artist = artist, dateOfRelease = dateOfRelease, hasFeaturedArtists = hasFeaturedArtists, isSaved = false, _id = currentId.toString())
                itemRepository.addLocally(item);
                Log.d(TAG, "added item ${item} locally");
            }
        }
    }

    fun UpdateItem(title: String, duration: Double, lat: Double, lon: Double) {
        viewModelScope.launch {
            Log.d(TAG, "update Song!!!");
            try {
                uiState = uiState.copy(submitResult = Result.Loading)
                val item = uiState.song.copy(title=title, duration = duration, isSaved = true, lat = lat, lon = lon)
                val savedSong: Song;
                savedSong = itemRepository.update(item)
                Log.d(TAG, "UpdateItem succeeeded");
                uiState = uiState.copy(submitResult = Result.Success(savedSong))
            } catch (e: Exception) {
                Log.d(TAG, "saveOrUpdateItem failed");
                uiState = uiState.copy(submitResult = Result.Error(e))
                val item = uiState.song.copy(title=title, duration = duration, isSaved = false, lat=lat, lon=lon)
                itemRepository.updateLocally(item)
            }
        }
    }

    companion object {
        fun Factory(itemId: String?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyFirstApplication)
                ItemViewModel(itemId, app.container.itemRepository)
            }
        }
    }
}
