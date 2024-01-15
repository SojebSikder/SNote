package com.sojebsikder.snote.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sojebsikder.snote.Models.Note;

import java.util.List;

@Dao
public interface MainDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getAll();

    @Query("SELECT * FROM notes WHERE id = :id")
    Note getOne(int id);

    @Query("UPDATE notes SET title = :title, content = :content, updated_at = :updated_at WHERE id = :id")
    void update(int id, String title, String content, String updated_at);

    @Delete()
    void delete(Note note);

    @Query("DELETE FROM notes")
    void deleteAll();
}
