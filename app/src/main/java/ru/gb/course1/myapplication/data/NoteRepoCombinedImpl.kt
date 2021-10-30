package ru.gb.course1.myapplication.data

import io.reactivex.Completable
import io.reactivex.Observable
import ru.gb.course1.myapplication.domain.NoteRepo
import ru.gb.course1.myapplication.domain.entities.NoteEntity

class NoteRepoCombinedImpl(
    private val localRepo: NoteRepo,
    private val webRepo: NoteRepo
) : NoteRepo {

    override val notes: Observable<List<NoteEntity>>
        get() = localRepo.notes

    override fun put(note: NoteEntity): Completable =
        localRepo.put(note).mergeWith(webRepo.put(note))

    override fun clear(): Completable = localRepo.clear().mergeWith(webRepo.clear())

}
