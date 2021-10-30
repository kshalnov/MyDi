package ru.gb.course1.myapplication.di

import androidx.room.Room
import ru.gb.course1.myapplication.data.NoteRepoCombinedImpl
import ru.gb.course1.myapplication.data.NoteRepoWebImpl
import ru.gb.course1.myapplication.data.room.NoteDao
import ru.gb.course1.myapplication.data.room.NoteDb
import ru.gb.course1.myapplication.data.room.NoteRepoRoomImpl
import ru.gb.course1.myapplication.domain.NoteRepo

val dependencies = arrayOf<DI.Dependency>(
    DI.singleton<NoteRepo> {
        NoteRepoCombinedImpl(
            DI.inject("room"),
            DI.inject("web")
        )
    },
    DI.singleton<NoteRepo>("web") {
        NoteRepoWebImpl()
    },
    DI.singleton<NoteRepo>("room") {
        NoteRepoRoomImpl(DI.inject())
    },
    DI.singleton<NoteDb> {
        Room.databaseBuilder(
            DI.inject(),
            NoteDb::class.java,
            "notes.db"
        ).build()
    },
    DI.singleton<NoteDao> {
        DI.inject<NoteDb>().noteDao()
    }
)
