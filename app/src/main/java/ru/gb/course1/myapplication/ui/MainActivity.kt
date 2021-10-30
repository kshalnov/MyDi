package ru.gb.course1.myapplication.ui

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.gb.course1.myapplication.R
import ru.gb.course1.myapplication.databinding.ActivityMainBinding
import ru.gb.course1.myapplication.di.DI
import ru.gb.course1.myapplication.domain.NoteRepo
import ru.gb.course1.myapplication.domain.entities.NoteEntity

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val compositeDisposable = CompositeDisposable()

    private val noteRepo: NoteRepo = DI.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.deleteAllButton.setOnClickListener {
            noteRepo.clear().subscribe().autoDispose(compositeDisposable)
        }

        binding.newNoteButton.setOnClickListener {
            noteRepo.put(NoteEntity.new()).subscribe().autoDispose(compositeDisposable)
        }

        noteRepo.notes
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onDataLoaded(it)
            }.autoDispose(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    @MainThread
    private fun onDataLoaded(notes: List<NoteEntity>) {
        val sb = StringBuilder()
        notes.forEach {
            sb.append("${it.title}\n${it.body}\n\n")
        }
        binding.resultTextView.text = sb.toString()
    }

}


fun Disposable.autoDispose(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}