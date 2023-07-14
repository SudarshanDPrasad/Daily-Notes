package com.sudarshan.dailynotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class NewNoteData(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val categoryName: String? = null,
    val note: String? = null
)