package ru.borhammere.dildo.sample.di

import androidx.room.Room
import ru.borhammere.dildo.Dildo
import ru.borhammere.dildo.Dildo.inject
import ru.borhammere.dildo.Dildo.singleton
import ru.borhammere.dildo.sample.data.NoteRepoCombinedImpl
import ru.borhammere.dildo.sample.data.NoteRepoWebImpl
import ru.borhammere.dildo.sample.data.room.NoteDao
import ru.borhammere.dildo.sample.data.room.NoteDb
import ru.borhammere.dildo.sample.data.room.NoteRepoRoomImpl
import ru.borhammere.dildo.sample.domain.NoteRepo

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
