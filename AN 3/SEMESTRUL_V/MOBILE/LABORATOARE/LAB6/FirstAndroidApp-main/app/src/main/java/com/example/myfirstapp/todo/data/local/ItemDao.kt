package com.example.myfirstapp.todo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myfirstapp.todo.data.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM Songs")
    fun getAll(): Flow<List<Song>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Song)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<Song>)

    @Update
    suspend fun update(item: Song): Int

    @Query("DELETE FROM Songs WHERE _id = :id")
    suspend fun deleteById(id: String): Int

    @Query("DELETE FROM Songs")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM Songs where title= :title and isSaved = :isSaved")
    suspend fun deleteSongNotSaved(title: String, isSaved: Boolean)

    @Query("SELECT * FROM Songs where isSaved = :isSaved")
    suspend fun getLocalSongs(isSaved: Boolean): List<Song>

    @Query("DELETE FROM Songs where isSaved = :isSaved")
    suspend fun deleteNotSaved(isSaved: Boolean): Int

    @Query("SELECT COUNT(*) FROM Songs where isSaved = :isSaved")
    suspend fun getNotSaved(isSaved: Boolean): Int
}
