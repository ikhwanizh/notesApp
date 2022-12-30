package com.ikhwanizh.notes.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ikhwanizh.notes.feature_note.domain.model.NoteEntitiy

@Database(
    entities = [NoteEntitiy::class],
    version = 1
)
abstract class NoteDB: RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE = "notes_db"
    }
}