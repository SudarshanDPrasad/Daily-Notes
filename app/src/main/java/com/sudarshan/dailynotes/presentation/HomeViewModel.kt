package com.sudarshan.dailynotes.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudarshan.dailynotes.data.model.CategoryData
import com.sudarshan.dailynotes.data.model.NewNoteData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repo: CategoryRepo
) : ViewModel() {

    var categoryName by mutableStateOf("")
    val listOfCategories = mutableStateListOf<CategoryData>()
    val listOfAllNotes = mutableStateListOf<NewNoteData>()
    val listOfFilter = mutableStateListOf<NewNoteData>()

    fun addCategory(categoryData: CategoryData) {
        viewModelScope.launch {
            repo.addCategory(categoryData)
            getCategory()
        }
    }

    fun filterSearch(filterCategory: String) {
        listOfFilter.clear()
        listOfAllNotes.forEach {
            if (it.categoryName == filterCategory){
                listOfFilter.add(it)
            }
        }
    }

    fun addNewNote(newNoteData: NewNoteData, categoryName: String) {
        viewModelScope.launch {
            repo.addNote(newNoteData, categoryName)
            getCategory()
        }
    }

    fun getCategory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.getStudents().collect {
                    listOfCategories.clear()
                    listOfCategories.addAll(it)
                }
            }
        }
    }

    fun getAllNotes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.getAllNotes().collect {
                    listOfAllNotes.clear()
                    listOfAllNotes.addAll(it)
                }
            }
        }
    }

    fun deleteCategory(categoryName: String) {
        viewModelScope.launch {
            repo.deleteCategory(categoryName = categoryName)
            repo.deleteCategoryNotes(categoryName = categoryName)
            getCategory()
            getAllNotes()
        }
    }

    fun deleteNotes(note: String, categoryName: String) {
        viewModelScope.launch {
            repo.deleteNotes(note = note, categoryName = categoryName)
            getAllNotes()
            getCategory()
        }
    }
}