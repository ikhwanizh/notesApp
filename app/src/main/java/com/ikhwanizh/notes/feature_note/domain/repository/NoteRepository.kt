package com.ikhwanizh.notes.feature_note.domain.repository

import com.ikhwanizh.notes.feature_note.domain.model.NoteEntitiy
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<NoteEntitiy>>

    suspend fun getNoteById(id: Int) : NoteEntitiy?

    suspend fun insertNote(noteEntitiy: NoteEntitiy)

    suspend fun deleteNote(noteEntitiy: NoteEntitiy)
}