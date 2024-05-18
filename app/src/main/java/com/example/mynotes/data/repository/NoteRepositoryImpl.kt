package com.example.mynotes.data.repository

import com.example.mynotes.data.local.dao.NoteDao
import com.example.mynotes.data.local.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    override suspend fun upsertNote(note: NoteEntity) {
        noteDao.upsertNote(note)
    }


    override suspend fun deleteNote(note: NoteEntity) {
        noteDao.deleteNote(note)
    }

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }

    override fun getNoteById(id: Int): Flow<NoteEntity> {
        return noteDao.getNoteById(id)
    }
}