package com.example.mynotes.presentaion.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

class NoteEditViewmodel:KoinComponent,ViewModel() {
    private val _title = MutableStateFlow("")
    val title=_title.asStateFlow()

    private val _note = MutableStateFlow("")
    val note=_note.asStateFlow()

    private val _color = MutableStateFlow(Color.Transparent.toArgb())
    val color=_color.asStateFlow()



    private val _id = MutableStateFlow(0)
    val id=_id.asStateFlow()


    fun setTitle(value:String){
        _title.value=value
    }
    fun setNote(value:String){
        _note.value=value
    }
    fun setBgColor(value:Int){
        _color.value=value
    }
    fun setId(value:Int){
        _id.value=value
    }



    fun dispose() {
        setTitle("")
        setNote("")
        setBgColor(Color.Transparent.toArgb())
    }


}