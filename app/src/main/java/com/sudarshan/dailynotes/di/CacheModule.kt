package com.sudarshan.dailynotes.di

import android.content.Context
import androidx.room.Room
import com.sudarshan.dailynotes.data.model.LocalDataBase
import com.sudarshan.dailynotes.data.dao.NotesDao
import com.sudarshan.dailynotes.presentation.CategoryRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NotesDao {
        return Room.databaseBuilder(appContext, LocalDataBase::class.java, "Notes")
            .build().notesDao
    }

    @Singleton
    @Provides
    fun getRepo(notesDao: NotesDao): CategoryRepo {
        return CategoryRepo(notesDao)
    }
}