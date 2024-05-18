package com.example.mynotes.presentaion.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mynotes.NoteEditScreen
import com.example.mynotes.data.mappers.toNoteItem
import com.example.mynotes.presentaion.ui.components.NoteListCard
import com.example.mynotes.presentaion.viewmodel.SharedViewmodel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesList(
    navController: NavHostController
) {
    val sharedViewmodel: SharedViewmodel = koinInject()

    val scrollState = rememberLazyStaggeredGridState()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val notes = sharedViewmodel.getAllNotes().collectAsState(emptyList())

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // TopAppBar
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ){
                MediumTopAppBar(
                    title = { Text(text = "My Notes") }, scrollBehavior = scrollBehavior
                )
            }

        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Add Note") },
                icon = {
                    Icon(
                        imageVector = Icons.TwoTone.AddCircle, contentDescription = "add new note"
                    )
                },
                onClick = {
                    navController.navigate(NoteEditScreen)
                },
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.primary,
                expanded = scrollState.isScrollingUp()
            )

        }) {

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            state = scrollState
        ) {
            items(notes.value) { item ->
                NoteListCard(item = item.toNoteItem(), onNoteClicked = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "Note",
                        item.toNoteItem()
                    )
                    navController.navigate(NoteEditScreen)
                }, onDelete = {
                    sharedViewmodel.deleteNote(item.toNoteItem())
                })
            }
        }
    }

}


@Composable
private fun LazyStaggeredGridState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
