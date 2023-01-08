package com.ikhwanizh.notes.feature_note.presentation.notes

import com.ikhwanizh.notes.feature_note.domain.model.NoteEntitiy
import com.ikhwanizh.notes.feature_note.domain.util.NoteOrder
import com.ikhwanizh.notes.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<NoteEntitiy> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isSectionOrderVisible: Boolean = false
)
