package com.zahirherz.fibtest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT SUM(timeToCalculate) FROM note_table")
    LiveData<Double> getTotalTimeToCalculate();
}
