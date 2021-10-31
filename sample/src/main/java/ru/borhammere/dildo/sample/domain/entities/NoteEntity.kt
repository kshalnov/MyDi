package ru.borhammere.dildo.sample.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String
) {
    companion object {
        private var counter = 1
        fun new(): NoteEntity =
            NoteEntity(UUID.randomUUID().toString(), "Я заметка ${counter++}", "Lorem Ipsum")
    }
}