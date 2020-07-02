package com.zahirherz.fibtest;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    private LiveData<Double> totalTime;

    // Activity only has reference to viewmodel not repository
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        totalTime = repository.getTotalTimeToCalculate();
    }

    //Create wrapper methods for db operations
    public void insert(Note note) {
        repository.insert(note);
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<Double> getTotalTime() {
        return totalTime;
    }
}
