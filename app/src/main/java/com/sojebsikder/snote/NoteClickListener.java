package com.sojebsikder.snote;

import androidx.cardview.widget.CardView;

import com.sojebsikder.snote.Models.Note;

public interface NoteClickListener {
    void onClick(Note note);
    void onLongClick(Note note, CardView cardView);
}
