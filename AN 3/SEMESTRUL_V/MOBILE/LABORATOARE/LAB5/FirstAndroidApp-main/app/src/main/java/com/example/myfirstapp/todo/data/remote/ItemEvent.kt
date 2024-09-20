package com.example.myfirstapp.todo.data.remote

import com.example.myfirstapp.todo.data.Song

data class Payload(val updatedSong: Song)

data class ItemEvent(val event: String, val payload: Payload)
