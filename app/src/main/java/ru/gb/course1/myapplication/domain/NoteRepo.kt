package ru.gb.course1.myapplication.domain

import io.reactivex.Completable
import io.reactivex.Observable
import ru.gb.course1.myapplication.domain.entities.NoteEntity

interface NoteRepo {
    val notes: Observable<List<NoteEntity>>
    fun put(note: NoteEntity): Completable
    fun clear(): Completable
}