package com.example.mynotes.domain

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteItem(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val date: String,
    val color: Int = Color.Transparent.toArgb(),
):Parcelable