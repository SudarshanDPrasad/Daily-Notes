package com.sudarshan.dailynotes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sudarshan.dailynotes.data.model.CategoryData
import com.sudarshan.dailynotes.data.model.NewNoteData

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryData: CategoryData)

    @Query("SELECT * FROM Category")
    fun getCategory(): List<CategoryData>

    @Query("UPDATE Category SET noOfNotes = noOfNotes + 1 WHERE categoryName = :categoryName")
    suspend fun incrementCategory(categoryName: String)


    @Query("UPDATE Category SET noOfNotes = noOfNotes - 1 WHERE categoryName = :categoryName")
    suspend fun decrementCategory(categoryName: String)

    @Query("DELETE FROM Category WHERE categoryName = :categoryName")
    suspend fun deleteCategory(categoryName: String)

    @Query("DELETE FROM Notes WHERE note = :note")
    suspend fun deleteNotes(note: String)


    @Query("DELETE FROM Notes WHERE categoryName = :categoryName")
    suspend fun deleteCategoryNotes(categoryName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(newNoteData: NewNoteData)

    @Query("SELECT * FROM Notes")
    fun getAllNotes(): List<NewNoteData>
}