package com.sojebsikder.snote.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sojebsikder.snote.Models.Note;
import com.sojebsikder.snote.NoteClickListener;
import com.sojebsikder.snote.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteListAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    Context context;
    List<Note> list;
    NoteClickListener listener;

    public NoteListAdapter(Context context, List<Note> list, NoteClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_content.setText(list.get(position).getContent());

        holder.textView_updated_at.setText(list.get(position).getUpdated_at());
        holder.textView_updated_at.setSelected(true);

        if(list.get(position).isPinned()){
            holder.imageView_pin.setVisibility(View.VISIBLE);
        }else{
            holder.imageView_pin.setVisibility(View.GONE);
        }

        int color_code = getRandomColor();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.note_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));
        }else{
            holder.note_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code));
        }

        holder.note_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.note_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.note_container);
                return true;
            }
        });
    }

    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);
        colorCode.add(R.color.color6);
        colorCode.add(R.color.color7);
        colorCode.add(R.color.color8);
        colorCode.add(R.color.color9);
        colorCode.add(R.color.color10);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());

        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Note> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}

class NoteViewHolder extends RecyclerView.ViewHolder{

    CardView note_container;
    TextView textView_title, textView_content, textView_updated_at;
    ImageView imageView_pin;
    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        note_container = itemView.findViewById(R.id.note_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_content = itemView.findViewById(R.id.textView_content);
        textView_updated_at = itemView.findViewById(R.id.textView_updated_at);
        imageView_pin = itemView.findViewById(R.id.imageView_pin);
    }
}