package com.sojebsikder.snote;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.sojebsikder.snote.Adapters.NoteListAdapter;
import com.sojebsikder.snote.Database.RoomDB;
import com.sojebsikder.snote.Models.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NoteListAdapter noteListAdapter;
    List<Note> noteList = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_home);
        fab_add = findViewById(R.id.fab_add);

        database = RoomDB.getInstance(this);
        noteList = database.mainDAO().getAll();

        updateRecyclerView(noteList);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, 100); // 100 is for add
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Note note = (Note) data.getSerializableExtra("note");
                database.mainDAO().insert(note);

//                noteList.add(note);
//                updateRecyclerView(noteList);
                noteList.clear();
                noteList.addAll(database.mainDAO().getAll());
                noteListAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Note note = (Note) data.getSerializableExtra("note");
                database.mainDAO().update(note.getId(), note.getTitle(), note.getContent(), note.getUpdated_at());

                noteList.clear();
                noteList.addAll(database.mainDAO().getAll());
                noteListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecyclerView(List<Note> noteList) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        noteListAdapter = new NoteListAdapter(MainActivity.this, noteList, noteClickListener);
        recyclerView.setAdapter(noteListAdapter);
    }

    private final NoteClickListener noteClickListener = new NoteClickListener() {
        @Override
        public void onClick(Note note) {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            intent.putExtra("note", note);
            startActivityForResult(intent, 101); // 101 is for update
        }

        @Override
        public void onLongClick(Note note, CardView cardView) {

        }
    };

}