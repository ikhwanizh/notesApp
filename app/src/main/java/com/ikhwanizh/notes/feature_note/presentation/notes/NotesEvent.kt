package com.ikhwanizh.notes.feature_note.presentation.notes

import com.ikhwanizh.notes.feature_note.domain.model.NoteEntitiy
import com.ikhwanizh.notes.feature_note.domain.util.NoteOrder

sealed class NotesEvent{
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: NoteEntitiy): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSections: NotesEvent()
}
