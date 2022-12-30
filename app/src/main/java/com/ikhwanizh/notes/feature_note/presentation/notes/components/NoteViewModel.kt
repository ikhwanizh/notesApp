package com.ikhwanizh.notes.feature_note.presentation.notes.components

import androidx.lifecycle.ViewModel
import com.ikhwanizh.notes.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

}