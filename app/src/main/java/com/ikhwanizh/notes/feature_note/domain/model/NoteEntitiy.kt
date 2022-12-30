package com.ikhwanizh.notes.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ikhwanizh.notes.ui.theme.*

@Entity
data class NoteEntitiy(
    @PrimaryKey
    val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
){
    companion object {
        val colors = listOf(RedOrange, LightBlue, LightGreen, Violet, RedPink)
    }
}
