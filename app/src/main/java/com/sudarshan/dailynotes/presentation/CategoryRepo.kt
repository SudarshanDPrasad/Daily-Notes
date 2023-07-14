package com.sudarshan.dailynotes.presentation

import com.sudarshan.dailynotes.data.dao.NotesDao
import com.sudarshan.dailynotes.data.model.CategoryData
import com.sudarshan.dailynotes.data.model.NewNoteData
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepo @Inject constructor(
    val notesDao: NotesDao
) {
    suspend fun addCategory(categoryData: CategoryData) {
        notesDao.insertCategory(categoryData = categoryData)
    }

    suspend fun addNote(newNoteData: NewNoteData,categoryName: String) {
        notesDao.insertNote(newNoteData = newNoteData)
        notesDao.incrementCategory(categoryName)
    }


    suspend fun getStudents() = flow {
        emit(notesDao.getCategory())
    }

    suspend fun getAllNotes() = flow {
        emit(notesDao.getAllNotes())
    }


    suspend fun deleteCategory(categoryName: String) {
        notesDao.deleteCategory(categoryName = categoryName)
        notesDao.deleteNotes(categoryName)
    }

    suspend fun deleteNotes(note: String,categoryName: String) {
        notesDao.deleteNotes(note)
        notesDao.decrementCategory(categoryName)
    }

    suspend fun deleteCategoryNotes(categoryName: String) {
        notesDao.deleteCategoryNotes(categoryName)
    }
}