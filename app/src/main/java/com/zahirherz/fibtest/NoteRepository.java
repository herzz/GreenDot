package com.zahirherz.fibtest;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<Double> totalTimeToCalculate;

    // Application subclass of context which ViewModel will access
    public NoteRepository(Application application) {
        NoteDataBase dataBase = NoteDataBase.getInstance(application);
        noteDao = dataBase.noteDao();
        allNotes = noteDao.getAllNotes();
        totalTimeToCalculate = noteDao.getTotalTimeToCalculate();
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    //Room returns livedata on background thread
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<Double> getTotalTimeToCalculate() {
        return totalTimeToCalculate;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
}
