package com.example.myfirstapp.todo.data.remote

import com.example.myfirstapp.todo.data.Song
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ItemService {
    @GET("/api/song")
    suspend fun find(@Header("Authorization") authorization: String): List<Song>

    @GET("/api/song/{id}")
    suspend fun read( @Header("Authorization") authorization: String, @Path("id") itemId: String?): Song;

    @Headers("Content-Type: application/json")
    @POST("/api/song")
    suspend fun create(@Header("Authorization") authorization: String, @Body song: Song): Song

    @Headers("Content-Type: application/json")
    @PUT("/api/song/{id}")
    suspend fun update( @Header("Authorization") authorization: String, @Path("id") itemId: String?, @Body song: Song): Song
}
