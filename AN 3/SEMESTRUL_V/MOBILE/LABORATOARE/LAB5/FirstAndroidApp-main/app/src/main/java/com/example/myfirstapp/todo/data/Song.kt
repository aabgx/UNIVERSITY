package com.example.myfirstapp.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(@PrimaryKey var _id: String = "",
                val title: String = "",
                val artist: String = "",
                val duration: Double=0.0,
                val dateOfRelease: String="",
                val hasFeaturedArtists: Boolean= false,
                var isSaved: Boolean=true,
                var lat: Double=0.0,
                var lon: Double=0.0)
