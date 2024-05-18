package com.example.mynotes.data.repository

import com.example.mynotes.data.local.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun upsertNote(note: NoteEntity)


    suspend fun deleteNote(note: NoteEntity)

    fun getAllNotes(): Flow<List<NoteEntity>>

    fun getNoteById(id: Int): Flow<NoteEntity>
}