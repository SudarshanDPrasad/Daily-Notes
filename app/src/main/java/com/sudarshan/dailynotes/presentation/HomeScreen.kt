package com.sudarshan.dailynotes.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sudarshan.dailynotes.R
import com.sudarshan.dailynotes.data.model.CategoryData
import com.sudarshan.dailynotes.presentation.Components.CategoryCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    categoryClick: () -> Unit
) {
    var addCategory by remember { mutableStateOf(false) }
    var deleteCategory by remember { mutableStateOf("") }
    var deleteNote by remember { mutableStateOf("") }
    var deleteCategoryClick by remember { mutableStateOf(false) }
    var deleteNoteClick by remember { mutableStateOf(false) }
    var filterCategory by remember { mutableStateOf(false) }
    viewModel.getCategory()
    viewModel.getAllNotes()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CATEGORIES",
                color = Color.Black
            )
            Icon(
                modifier = Modifier.clickable {
                    addCategory = true
                },
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        LazyRow() {
            items(viewModel.listOfCategories) { response ->
                CategoryCard(
                    modifier = Modifier
                        .combinedClickable(
                            onLongClick = {
                                deleteCategoryClick = true
                                deleteCategory = response.categoryName.toString()
                            }
                        ) {
                            response.categoryName?.let { name ->
                                viewModel.categoryName = name
                                categoryClick()
                            }
                        }
                        .padding(10.dp),
                    notesCount = response.noOfNotes,
                    category = response.categoryName
                )
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Divider(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
        )
        if (viewModel.listOfFilter.isEmpty()) {
            Icon(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(10.dp)
                    .clickable {
                        filterCategory = !filterCategory
                    },
                painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                contentDescription = ""
            )
        } else {
            Icon(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(10.dp)
                    .clickable {
                        viewModel.listOfFilter.clear()
                    },
                painter = painterResource(id = R.drawable.baseline_close_24),
                contentDescription = ""
            )
        }
        LazyColumn() {
            if (viewModel.listOfFilter.isEmpty()) {
                items(viewModel.listOfAllNotes) { response ->
                    NoteCard(
                        modifier = Modifier
                            .combinedClickable(
                                onLongClick = {
                                    deleteNoteClick = true
                                    deleteCategory = response.categoryName.toString()
                                    deleteNote = response.note.toString()
                                }
                            ) { },
                        categoryName = response.categoryName.toString(),
                        note = response.note.toString()
                    )
                }
            } else {
                items(viewModel.listOfFilter) { response ->
                    NoteCard(
                        modifier = Modifier
                            .combinedClickable(
                                onLongClick = {
                                    deleteNoteClick = true
                                    deleteCategory = response.categoryName.toString()
                                    deleteNote = response.note.toString()
                                }
                            ) { },
                        categoryName = response.categoryName.toString(),
                        note = response.note.toString()
                    )
                }
            }
        }
    }

    if (addCategory) {
        NewCategoryAdd(
            onDismiss = {
                addCategory = !addCategory
            },
            onAdd = { categoryName ->
                val newCategory = CategoryData(categoryName = categoryName)
                viewModel.addCategory(categoryData = newCategory)
                addCategory = !addCategory
            }
        )
    }

    if (deleteCategoryClick) {
        DeleteCategory(
            category = deleteCategory,
            onDismiss = {
                deleteCategoryClick = !deleteCategoryClick
            },
            onDelete = {
                viewModel.deleteCategory(deleteCategory)
                deleteCategoryClick = !deleteCategoryClick
            },
            onCancel = {
                deleteCategoryClick = !deleteCategoryClick
                deleteCategory = ""
            }
        )
    }

    if (deleteNoteClick) {
        DeleteCategory(
            category = deleteNote,
            onDismiss = {
                deleteNoteClick = !deleteNoteClick
            },
            onDelete = {
                viewModel.deleteNotes(deleteNote, categoryName = deleteCategory)
                deleteNoteClick = !deleteNoteClick
            },
            onCancel = {
                deleteNoteClick = !deleteNoteClick
                deleteNote = ""
                deleteCategory = ""
            }
        )
    }

    if (filterCategory) {
        FilterCategory(
            onDismiss = {
                filterCategory = !filterCategory
            },
            viewModel = viewModel,
            onFilter = {
                filterCategory = !filterCategory
            }
        )
    }
}

@Composable
fun FilterCategory(
    onDismiss: () -> Unit,
    viewModel: HomeViewModel,
    onFilter: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "Filter Category",
                color = Color.Black.copy(alpha = 0.40f)
            )
            Spacer(modifier = Modifier.size(10.dp))
            LazyColumn() {
                items(viewModel.listOfCategories) {
                    Text(
                        modifier = Modifier.clickable {
                            viewModel.filterSearch(it.categoryName.toString())
                            onFilter()
                        },
                        text = it.categoryName.toString()
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCategoryAdd(
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit
) {
    var newCategory by remember { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "New Category",
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = newCategory,
                onValueChange = {
                    newCategory = it
                },
                label = {
                    Text(text = "Add New Category")
                }
            )
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                onClick = { onAdd(newCategory) })
            {
                Text(text = "Add")
            }
        }
    }
}


@Composable
fun DeleteCategory(
    category: String,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onCancel: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "Are You sure wanna Delete $category",
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onDelete() }
                ) {
                    Text(text = "Delete", color = Color.White)
                }
                Button(
                    onClick = { onCancel() })
                {
                    Text(text = "Cancel", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    categoryName: String,
    note: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = categoryName, color = Color.Black.copy(alpha = 0.40f))
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = note, color = Color.Black)
        }
    }
}
