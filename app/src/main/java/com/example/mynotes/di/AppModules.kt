package com.example.mynotes.di

import androidx.room.Room
import com.example.mynotes.data.local.database.AppDatabase
import com.example.mynotes.data.repository.NoteRepository
import com.example.mynotes.data.repository.NoteRepositoryImpl
import com.example.mynotes.presentaion.viewmodel.NoteEditViewmodel
import com.example.mynotes.presentaion.viewmodel.SharedViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


val appModules = module {
    viewModel {
        NoteEditViewmodel()
    }
    single{
        SharedViewmodel()
    }

    //Database
    single {   Room.databaseBuilder(
        get(),
        AppDatabase::class.java,
        "notes.db"
    ).build() }
    //Dao
    single {
        get<AppDatabase>().dao
    }
    //Repository
    single { NoteRepositoryImpl(noteDao = get()) } bind NoteRepository::class


}


