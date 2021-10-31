package ru.borhammere.dildo.sample.data.room

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.borhammere.dildo.sample.domain.NoteRepo
import ru.borhammere.dildo.sample.domain.entities.NoteEntity

class NoteRepoRoomImpl(private val noteDao: NoteDao) : NoteRepo {
    override val notes: Observable<List<NoteEntity>> = noteDao.getNotes()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun put(note: NoteEntity) = noteDao.put(note).subscribeOn(Schedulers.io())

    override fun clear() = noteDao.clear().subscribeOn(Schedulers.io())
}