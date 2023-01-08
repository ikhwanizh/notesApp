package com.ikhwanizh.notes.feature_note.domain.use_case

import com.ikhwanizh.notes.feature_note.domain.model.InvalidNoteException
import com.ikhwanizh.notes.feature_note.domain.model.NoteEntitiy
import com.ikhwanizh.notes.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: NoteEntitiy) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title can't be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Content can't be empty")
        }
        repository.insertNote(note)
    }
}