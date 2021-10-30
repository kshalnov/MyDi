package ru.gb.course1.myapplication.di

import androidx.room.Room
import ru.borhammere.dildo.Dildo
import ru.borhammere.dildo.Dildo.inject
import ru.borhammere.dildo.Dildo.singleton
import ru.gb.course1.myapplication.data.NoteRepoCombinedImpl
import ru.gb.course1.myapplication.data.NoteRepoWebImpl
import ru.gb.course1.myapplication.data.room.NoteDao
import ru.gb.course1.myapplication.data.room.NoteDb
import ru.gb.course1.myapplication.data.room.NoteRepoRoomImpl
import ru.gb.course1.myapplication.domain.NoteRepo

val dependencies = arrayOf<Dildo.Dependency>(
    singleton<NoteRepo> {
        NoteRepoCombinedImpl(
            inject("room"),
            inject("web")
        )
    },
    singleton<NoteRepo>("web") {
        NoteRepoWebImpl()
    },
    singleton<NoteRepo>("room") {
        NoteRepoRoomImpl(inject())
    },
    singleton<NoteDb> {
        Room.databaseBuilder(
            inject(),
            NoteDb::class.java,
            "notes.db"
        ).build()
    },
    singleton<NoteDao> {
        inject<NoteDb>().noteDao()
    }
)
