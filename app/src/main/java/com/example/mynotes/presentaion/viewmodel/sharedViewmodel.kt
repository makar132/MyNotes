package com.example.mynotes.presentaion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.local.entities.NoteEntity
import com.example.mynotes.data.mappers.toNoteEntity
import com.example.mynotes.data.repository.NoteRepository
import com.example.mynotes.domain.NoteItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SharedViewmodel : ViewModel(), KoinComponent {

    private val noteRepository: NoteRepository by inject()

    // Insert or update a note
    fun upsertNote(note: NoteItem) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                noteRepository.upsertNote(note.toNoteEntity())
            }
        }
    }


    // Delete a note
    fun deleteNote(note: NoteItem) {
        // Delete the note from the database
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                noteRepository.deleteNote(note.toNoteEntity())
            }
        }
    }

    // Get all notes
    fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteRepository.getAllNotes()
    }

    // Get a note by ID
    fun getNoteById(id: Int): Flow<NoteEntity> {
        return noteRepository.getNoteById(id)
    }

}