package com.example.myfirstapp.todo.data

import android.util.Log
import com.example.myfirstapp.core.Result
import com.example.myfirstapp.core.TAG
import com.example.myfirstapp.core.data.remote.Api
import com.example.myfirstapp.todo.data.local.ItemDao
import com.example.myfirstapp.todo.data.remote.ItemEvent
import com.example.myfirstapp.todo.data.remote.ItemService
import com.example.myfirstapp.todo.data.remote.ItemWsClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

class ItemRepository(private val itemService: ItemService,
                     private val itemWsClient: ItemWsClient,
                     private val itemDao: ItemDao) {

    val songStream by lazy { itemDao.getAll() }

    init {
        Log.d(TAG, "init")
    }

    suspend fun refresh() {
        Log.d(TAG, "refresh started")
        try {
            val songs = itemService.find(authorization = getBearerToken())
            itemDao.deleteAll()
            songs.forEach{ itemDao.insert(it) }
            Log.d(TAG, "refresh succeeded")
        } catch (e: Exception) {
            Log.w(TAG, "refresh failed", e)
        }
    }

    suspend fun openWsClient() {
        Log.d(TAG, "openWsClient")
        withContext(Dispatchers.IO) {
            getItemEvents().collect {
                Log.d(TAG, "Item event collected $it")
                if (it is Result.Success) {
                    val itemEvent = it.data;
                    when (itemEvent.event) {
                        "created" -> handleItemCreated(itemEvent.payload.updatedSong)
                        "updated" -> handleItemUpdated(itemEvent.payload.updatedSong)
                        "deleted" -> handleItemDeleted(itemEvent.payload.updatedSong)
                    }
                }
            }
        }
    }

    suspend fun closeWsClient() {
        Log.d(TAG, "closeWsClient")
        withContext(Dispatchers.IO) {
            itemWsClient.closeSocket()
        }
    }

    suspend fun getItemEvents(): Flow<Result<ItemEvent>> = callbackFlow {
        Log.d(TAG, "getItemEvents started")
        itemWsClient.openSocket(
            onEvent = {
                Log.d(TAG, "onEvent $it")
                if (it != null) {
                    Log.d(TAG, "onEvent trySend $it")
                    trySend(Result.Success(it))
                }
            },
            onClosed = { close() },
            onFailure = { close() });
        awaitClose { itemWsClient.closeSocket() }
    }

    suspend fun update(song: Song): Song {
        Log.d(TAG, "update $song...")
        song.isSaved = true
        val updatedItem = itemService.update(authorization = getBearerToken(), song._id, song)
        Log.d(TAG, "update $song succeeded")
        handleItemUpdated(updatedItem)
        return updatedItem
    }

    suspend fun save(song: Song): Song {
        Log.d(TAG, "save $song...")
        song.isSaved = true
        val createdItem = itemService.create(authorization = getBearerToken(), song)
        Log.d(TAG, "save $song succeeded")
        Log.d(TAG, "handle created $createdItem")
        handleItemCreated(createdItem)
        deleteSong(createdItem.title, false)
        return createdItem
    }

    suspend fun addLocally(song: Song){
        itemDao.insert(song);
    }

    suspend fun deleteLocally(){
        itemDao.getLocalSongs(isSaved = false)
    }

    suspend fun getLocallySaved(): List<Song>{
        return itemDao.getLocalSongs(isSaved = false)
    }

    suspend fun updateLocally(song: Song){
        Log.d(TAG, "Updating song locally: ${song}")
        itemDao.update(song)
    }

    private suspend fun handleItemDeleted(song: Song) {
        Log.d(TAG, "handleItemDeleted - todo $song")
    }

    private suspend fun handleItemUpdated(song: Song) {
        Log.d(TAG, "handleItemUpdated...: $song")
        itemDao.update(song)
    }

    private suspend fun handleItemCreated(song: Song) {
        Log.d(TAG, "handleItemCreated...: $song")
        itemDao.insert(song)
    }

    suspend fun deleteAll(){
        itemDao.deleteAll()
    }

    suspend fun deleteSong(title: String, isSaved: Boolean) {
        Log.d(TAG, "deleting not saved: ${title}, ${isSaved}")
        itemDao.deleteSongNotSaved(title, isSaved)
    }

    suspend fun getNrUnsaved(): Int{
        return itemDao.getNotSaved(false)
    }

    fun setToken(token: String) {
        itemWsClient.authorize(token)
    }

    private fun getBearerToken() = "Bearer ${Api.tokenInterceptor.token}"
}