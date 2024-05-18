package com.example.mynotes.presentaion.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.navigation.NavHostController
import com.example.mynotes.NotesListScreen
import com.example.mynotes.domain.NoteItem
import com.example.mynotes.presentaion.viewmodel.NoteEditViewmodel
import com.example.mynotes.presentaion.viewmodel.SharedViewmodel
import io.mhssn.colorpicker.ColorPickerDialog
import io.mhssn.colorpicker.ColorPickerType
import org.koin.compose.koinInject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalStdlibApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteEdit(navController: NavHostController) {
    val viewmodel: NoteEditViewmodel = koinInject()
    val sharedViewmodel: SharedViewmodel = koinInject()

    val id = viewmodel.id.collectAsState()
    val title = viewmodel.title.collectAsState()
    val noteBody = viewmodel.note.collectAsState()
    val color = viewmodel.color.collectAsState()
    val (dialogOpen, setDialogOpen) = remember {
        mutableStateOf(false)
    }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    var saveClicked by remember { mutableStateOf(false) }

    val args = navController.previousBackStackEntry?.savedStateHandle
    val receivedNote = args?.get<NoteItem>("Note")
    if (receivedNote != null) {
        with(viewmodel) {
            setId(receivedNote.id)
            setTitle(receivedNote.title)
            setNote(receivedNote.content)
            setBgColor(receivedNote.color)

        }
        args.remove<NoteItem>("Note")
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(10.dp))
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack(NotesListScreen, false)
                        }) {
                            Icon(Icons.AutoMirrored.Sharp.ArrowBack, contentDescription = "Back")
                        }
                    },
                    title = {
                        TransparentTextFieldTopAppBar(title = title.value) {
                            viewmodel.setTitle(it)
                        }
                    },
                    actions = {

                        TextButton(onClick =
                        {
                            if (!saveClicked) {
                                saveClicked = true
                                sharedViewmodel.upsertNote(
                                    NoteItem(
                                        id = id.value,
                                        title = title.value,
                                        content = noteBody.value,
                                        date = getDate(),
                                        color = color.value
                                    )
                                ).also {
                                    navController.popBackStack(NotesListScreen, false)

                                }
                            }

                        }) {
                            Icon(Icons.Filled.Save, contentDescription = "save")
                        }
                    }, scrollBehavior = scrollBehavior
                )
            }

        },
        floatingActionButton = {
            FloatingActionButton(contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { setDialogOpen(true) }) {
                Icon(
                    imageVector = Icons.Rounded.Palette,
                    contentDescription = "Background color picker"
                )
            }
        }
    ) { paddingValues ->

        ColorPickerDialog(
            show = dialogOpen,
            type = ColorPickerType.Ring(
                ringWidth = 10.dp,
                previewRadius = 80.dp,
                showLightnessBar = true,
                showDarknessBar = true,
                showAlphaBar = false,
                showColorPreview = true
            ),
            properties = DialogProperties(),
            onDismissRequest = {
                setDialogOpen(false)
            },
            onPickedColor = {
                viewmodel.setBgColor(it.toArgb())
                setDialogOpen(false)
            },
        )
        val decodedColor = Color(color.value)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = paddingValues.calculateTopPadding())
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            NoteBodyTextField(
                value = noteBody.value,
                onValueChange = { viewmodel.setNote(it) },
                modifier = Modifier
                    .fillMaxSize(),
                backgroundColor = decodedColor

            )


        }


    }
    DisposableEffect(Unit) {
        onDispose {
            // Dispose of the ViewModel when the composable is destroyed
            viewmodel.dispose()
        }
    }

}


@Composable
fun TransparentTextFieldTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onTitleChange: (String) -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        color = Color.Transparent,
        shape = RoundedCornerShape(4.dp),
    ) {
        TextField(
            value = title,
            placeholder = { Text(text = "Title") },
            onValueChange = onTitleChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),

            singleLine = true
        )
    }
}


@Composable
fun NoteBodyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        textStyle = LocalTextStyle.current.copy(
            color = chooseContrastingTextColor(backgroundColor),
            fontWeight = FontWeight.SemiBold

        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )
    )

}

fun calculateLuminance(color: Color): Double {
    val components = color.toArgb()
    return components.red * 0.2126 + components.green * 0.7152 + components.blue * 0.0722
}

fun chooseContrastingTextColor(backgroundColor: Color): Color {
    val luminance = calculateLuminance(backgroundColor)
    return if (luminance > 0.5) Color.Black else Color.White
}

fun getDate(): String {
    return SimpleDateFormat(
        "yyyy/MM/dd hh:mm:ss",
        Locale.getDefault()
    ).format(Date(System.currentTimeMillis()))
}

