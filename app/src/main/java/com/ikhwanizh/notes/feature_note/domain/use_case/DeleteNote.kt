package com.ikhwanizh.notes.feature_note.domain.use_case

import com.ikhwanizh.notes.feature_note.domain.model.NoteEntitiy
import com.ikhwanizh.notes.feature_note.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteEntitiy: NoteEntitiy){
        repository.deleteNote(noteEntitiy)
    }
}