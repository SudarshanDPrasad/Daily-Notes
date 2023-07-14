package com.sudarshan.dailynotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class CategoryData(
    val categoryName: String? = null,
    val noOfNotes: Int = 0,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
