package ru.borhammere.dildo.sample.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.borhammere.dildo.sample.domain.entities.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDb : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}