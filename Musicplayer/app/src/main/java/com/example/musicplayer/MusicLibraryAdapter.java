package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicLibraryAdapter extends RecyclerView.Adapter<MusicLibraryAdapter.MusicLibraryViewHolder> {

    public ArrayList<MusicLibraryItem> musicLibraryItems;
    public  Context context;

    public MusicLibraryAdapter(ArrayList<MusicLibraryItem> musicLibraryItems, Context context){
        this.musicLibraryItems = musicLibraryItems;
        this.context = context;
    }

    class MusicLibraryViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public ImageView imageView;
        public TextView title;
        public TextView author;

        public MusicLibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            imageView = itemView.findViewById(R.id.musicIV);
            title = itemView.findViewById(R.id.titleMusic);
            author = itemView.findViewById(R.id.authorMusic);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, MusicActivity.class);
            int pos = getAdapterPosition();
            MusicLibraryItem item = musicLibraryItems.get(pos);

            intent.putExtra("nameMusic", item.getTitle().replace(" ", "_"));
            intent.putExtra("nameAuthor", item.getAuthor());
            context.startActivity(intent);
        }
    }


    @NonNull
    @Override
    public MusicLibraryAdapter.MusicLibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list,
                parent, false);

        MusicLibraryViewHolder recyclerViewViewHolder = new MusicLibraryViewHolder(view);
        return recyclerViewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicLibraryViewHolder holder, int position) {
        MusicLibraryItem musicLibraryItem = musicLibraryItems.get(position);

        holder.imageView.setImageResource(musicLibraryItem.getImageResource());
        holder.title.setText(musicLibraryItem.getTitle());
        holder.author.setText(musicLibraryItem.getAuthor());
    }

    @Override
    public int getItemCount() {
        return musicLibraryItems.size();
    }
}