package com.sudarshan.dailynotes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.sudarshan.dailynotes.data.model.NewNoteData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNotesScreen(
    viewModel: HomeViewModel,
    onAddClick: () -> Unit
) {
    var note by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val listOfCategory = viewModel.listOfCategories
    var selectedCategory by remember { mutableStateOf(viewModel.categoryName) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Add note", color = Color.Black)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Category", color = Color.Black.copy(alpha = 0.40f))
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {
                        selectedCategory = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            textFieldSize = it.size.toSize()
                        },
                    label = {
                        Text(text = "Select Category")
                    },
                    trailingIcon = {
                        Icon(
                            icon,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                expanded = !expanded
                            }
                        )
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier

                        .width(with(LocalDensity.current){
                            textFieldSize.width.toDp()
                        })
                ) {
                    listOfCategory.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(text = it.categoryName.toString())
                            },
                            onClick = {
                                selectedCategory = it.categoryName.toString()
                                expanded = false
                                viewModel.categoryName = selectedCategory
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Note", color = Color.Black.copy(alpha = 0.40f))
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = note,
                    onValueChange = {
                        note = it
                    },
                    label = {
                        Text(text = "Add New Note")
                    }
                )
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            onClick = {
                val newNote = NewNoteData(
                    categoryName = viewModel.categoryName,
                    note = note
                )
                viewModel.addNewNote(newNote, viewModel.categoryName)
                onAddClick()
            })
        {
            Text(text = "Add")
        }
    }
}