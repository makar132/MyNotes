package com.example.mynotes.data.mappers

import com.example.mynotes.data.local.entities.NoteEntity
import com.example.mynotes.domain.NoteItem

fun NoteItem.toNoteEntity() = NoteEntity(id, title, content, date, color)

fun NoteEntity.toNoteItem() = NoteItem(id, title, content, date, color)