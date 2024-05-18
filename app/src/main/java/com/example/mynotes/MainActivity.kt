package com.example.mynotes

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mynotes.presentaion.theme.MyNotesTheme
import com.example.mynotes.presentaion.ui.screens.NoteEdit
import com.example.mynotes.presentaion.ui.screens.NotesList
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    MyAppNavHost()
                }
            }
        }
    }
}
@Serializable
object NotesListScreen

@Serializable
object NoteEditScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Any = NotesListScreen
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<NotesListScreen> {
            NotesList(navController)
        }

        composable<NoteEditScreen> {
            NoteEdit(navController)
        }
    }
}


