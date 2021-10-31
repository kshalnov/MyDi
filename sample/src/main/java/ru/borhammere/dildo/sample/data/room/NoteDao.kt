package ru.borhammere.dildo.sample.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import ru.borhammere.dildo.sample.domain.entities.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getNotes(): Observable<List<NoteEntity>>

    @Query("DELETE FROM notes")
    fun clear(): Completable

    @Insert
    fun put(note: NoteEntity): Completable

    @Delete
    fun delete(note: NoteEntity): Completable
}