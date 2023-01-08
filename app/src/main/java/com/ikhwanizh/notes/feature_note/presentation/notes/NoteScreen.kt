package com.ikhwanizh.notes.feature_note.presentation.notes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: NoteViewModel = hiltViewModel()
) {
}