package com.ikhwanizh.notes.feature_note.data.repository

import com.ikhwanizh.notes.feature_note.data.data_source.NoteDao
import com.ikhwanizh.notes.feature_note.domain.model.NoteEntitiy
import com.ikhwanizh.notes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImplement(private val dao: NoteDao): NoteRepository {
    override fun getNotes(): Flow<List<NoteEntitiy>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): NoteEntitiy? {
        return dao.getNotesById(id)
    }

    override suspend fun insertNote(noteEntitiy: NoteEntitiy) {
        dao.insertNote(noteEntitiy)
    }

    override suspend fun deleteNote(noteEntitiy: NoteEntitiy) {
        dao.deleteNote(noteEntitiy)
    }
}