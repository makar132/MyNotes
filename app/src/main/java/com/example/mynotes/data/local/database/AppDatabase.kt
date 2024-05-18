package com.example.mynotes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynotes.data.local.dao.NoteDao
import com.example.mynotes.data.local.entities.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao : NoteDao

}