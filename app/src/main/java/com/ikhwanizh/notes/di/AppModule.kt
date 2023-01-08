package com.ikhwanizh.notes.di

import android.app.Application
import androidx.room.Room
import com.ikhwanizh.notes.feature_note.data.data_source.NoteDB
import com.ikhwanizh.notes.feature_note.data.repository.NoteRepositoryImplement
import com.ikhwanizh.notes.feature_note.domain.repository.NoteRepository
import com.ikhwanizh.notes.feature_note.domain.use_case.AddNote
import com.ikhwanizh.notes.feature_note.domain.use_case.DeleteNote
import com.ikhwanizh.notes.feature_note.domain.use_case.GetNotes
import com.ikhwanizh.notes.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideNoteDatabas(app: Application): NoteDB {
        return Room.databaseBuilder(
            app,
            NoteDB::class.java,
            NoteDB.DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDB): NoteRepository{
        return NoteRepositoryImplement(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository)
        )
    }
}