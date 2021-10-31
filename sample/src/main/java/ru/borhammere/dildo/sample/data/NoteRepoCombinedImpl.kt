package ru.borhammere.dildo.sample.data

import io.reactivex.Completable
import io.reactivex.Observable
import ru.borhammere.dildo.sample.domain.NoteRepo
import ru.borhammere.dildo.sample.domain.entities.NoteEntity

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
