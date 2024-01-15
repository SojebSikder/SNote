package com.sojebsikder.snote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sojebsikder.snote.Models.Note;

import java.text.SimpleDateFormat;

public class AddNoteActivity extends AppCompatActivity {

    EditText editText_title, editText_content;
    ImageView imageView_save;
    Note note;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        imageView_save = findViewById(R.id.imageView_save);
        editText_title = findViewById(R.id.editText_title);
        editText_content = findViewById(R.id.editText_content);

        note = new Note();
        try {
            note = (Note) getIntent().getSerializableExtra("note");
            editText_title.setText(note.getTitle());
            editText_content.setText(note.getContent());
            isEdit = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                String content = editText_content.getText().toString();

                String dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(System.currentTimeMillis());

                if (!isEdit) {
                    note = new Note();
                }
                note.setTitle(title);
                note.setContent(content);
                note.setCreated_at(dateTime);
                note.setUpdated_at(dateTime);

                Intent intent = new Intent();
                intent.putExtra("note", note);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}