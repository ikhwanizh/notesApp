package com.ikhwanizh.notes.feature_note.data.data_source

import androidx.room.*
import com.ikhwanizh.notes.feature_note.domain.model.NoteEntitiy
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteEntitiy")
    fun getNotes(): Flow<List<NoteEntitiy>>

    @Query("SELECT * FROM NoteEntitiy WHERE id = id")
    suspend fun getNotesById(id: Int): NoteEntitiy?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntitiy: NoteEntitiy)

    @Delete
    suspend fun deleteNote(noteEntitiy: NoteEntitiy)
}