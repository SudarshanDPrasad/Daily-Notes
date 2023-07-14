package com.sudarshan.dailynotes.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sudarshan.dailynotes.data.dao.NotesDao


@Database(entities = [CategoryData::class, NewNoteData::class], version = 1)
abstract class LocalDataBase : RoomDatabase() {
    abstract val notesDao: NotesDao
}