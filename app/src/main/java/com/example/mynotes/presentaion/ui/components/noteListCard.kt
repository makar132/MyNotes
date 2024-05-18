package com.example.mynotes.presentaion.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mynotes.domain.NoteItem
import com.example.mynotes.presentaion.ui.screens.chooseContrastingTextColor


@Composable
fun NoteListCard(
    item: NoteItem,
    onNoteClicked: () -> Unit,
    onDelete: () -> Unit,

    ) {
    val backgroundColor = Color(item.color)
    val openDeleteConfirmationDialog = remember { mutableStateOf(false) }
    val dialogState = remember {
        DeleteConfirmationDialogState(
            title = "Delete Note",
            message = "Are you sure you want to delete this note? ",
            onConfirm = { onDelete() },
            onDismiss = {
                openDeleteConfirmationDialog.value = false
            }
        )
    }
    if (openDeleteConfirmationDialog.value) {
        AlertDialog(
            onDismissRequest = dialogState.onDismiss,
            title = { Text(text = dialogState.title) },
            text = { Text(text = dialogState.message) },
            confirmButton = {
                TextButton(
                    onClick = dialogState.onConfirm.also { dialogState.onDismiss },
                    modifier = Modifier.fillMaxWidth(0.45f),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Delete Note",
                            modifier = Modifier
                                .align(Alignment.Bottom)
                        )
                        Text(
                            text = "Delete",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )

                    }

                }


            },
            dismissButton = {
                TextButton(
                    onClick = dialogState.onDismiss,
                    modifier = Modifier.fillMaxWidth(0.45f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            Icons.Filled.Cancel,
                            contentDescription = "Cancel",
                            modifier = Modifier
                                .align(Alignment.Bottom)
                        )
                        Text(
                            text = "Cancel",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                }

            },
        )
    }


    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(enabled = true, onClick = onNoteClicked), // Rounded except top right
        shape = CutCornerShape(topEnd = 15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {

        Column(
            modifier = Modifier

                .background(
                    color = backgroundColor
                ),

            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            val contrastingColor = chooseContrastingTextColor(backgroundColor)
            Box(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.TopCenter),
                    text = item.title,
                    color = contrastingColor,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                // Overlay for folded corner
                Box(
                    modifier = Modifier
                        .height(15.dp) // Adjust height as needed
                        .width(15.dp) // Adjust width as needed
                        .background(color = MaterialTheme.colorScheme.primary) // Set fold color
                        .align(Alignment.TopEnd) // Position at top right
                    //.rotate(60f) // Rotate for fold effect
                )

            }

            Text(
                modifier = Modifier.padding(8.dp),
                text = item.content,
                color = contrastingColor,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.date,
                    color = contrastingColor,
                    fontWeight = FontWeight.Thin,
                    style = MaterialTheme.typography.bodySmall
                )
                IconButton(
                    onClick = {
                        openDeleteConfirmationDialog.value = true
                    },
                    // elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = contrastingColor,
                        containerColor = backgroundColor
                    ),
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete Note")
                }

            }

        }


    }


}

data class DeleteConfirmationDialogState(
    val title: String,
    val message: String,
    val onConfirm: () -> Unit,
    val onDismiss: () -> Unit
)




