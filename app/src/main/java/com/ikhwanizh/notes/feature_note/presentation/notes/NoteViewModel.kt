package com.ikhwanizh.notes.feature_note.presentation.notes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhwanizh.notes.feature_note.domain.model.NoteEntitiy
import com.ikhwanizh.notes.feature_note.domain.use_case.NoteUseCases
import com.ikhwanizh.notes.feature_note.domain.util.NoteOrder
import com.ikhwanizh.notes.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state : State<NotesState> = _state

    private var recentlyDeletedNote: NoteEntitiy? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent){
            when(event){
                is NotesEvent.Order -> {
                    if(state.value.noteOrder == event.noteOrder &&
                            state.value.noteOrder.orderType == event.noteOrder.orderType) {
                        return
                    }
                    getNotes(event.noteOrder)
                }
                is NotesEvent.DeleteNote -> {
                    viewModelScope.launch {
                        noteUseCases.deleteNote(event.note)
                        recentlyDeletedNote = event.note
                    }
                }
                is NotesEvent.RestoreNote -> {
                    viewModelScope.launch {
                        noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                        recentlyDeletedNote = null
                    }
                }
                is NotesEvent.ToggleOrderSections -> {
                    _state.value = _state.value.copy(
                        isSectionOrderVisible = !state.value.isSectionOrderVisible
                    )
                }
            }
        }
    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder).
                onEach { notes ->
                    _state.value = state.value.copy(
                        notes = notes,
                        noteOrder = noteOrder
                    )
                }
            .launchIn(viewModelScope)
    }
}