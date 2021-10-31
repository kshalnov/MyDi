package ru.borhammere.dildo.sample.domain

import io.reactivex.Completable
import io.reactivex.Observable
import ru.borhammere.dildo.sample.domain.entities.NoteEntity

interface NoteRepo {
    val notes: Observable<List<NoteEntity>>
    fun put(note: NoteEntity): Completable
    fun clear(): Completable
}