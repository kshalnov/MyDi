package ru.gb.course1.myapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.gb.course1.myapplication.domain.entities.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDb : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}