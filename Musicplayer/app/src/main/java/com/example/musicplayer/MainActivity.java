package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<MusicLibraryItem> musicLibraryItems = new ArrayList<>();


        musicLibraryItems.add(new MusicLibraryItem(R.drawable.cat,
                Utils.CAT_TRUMPET_WALTZ_OF_CHIHIRO.replace("_", " "),
                Utils.CAT_TRUMPET));

        musicLibraryItems.add(new MusicLibraryItem(R.drawable.erik,
                Utils.ERIK_SATIE_YMNOPDIE_NO_1.replace("_", " "),
                Utils.ERIK_SATIE));

        musicLibraryItems.add(new MusicLibraryItem(R.drawable.langlang,
                Utils.LANG_LANG_SATUE_GNOSSIENNES.replace("_", " "),
                Utils.LANG_LANG));


        musicLibraryItems.add(new MusicLibraryItem(R.drawable.luke,
                Utils.LUKE_FAULKNER_DAYDREAMING.replace("_", " "),
                Utils.LUKE_FAULKNER));


        musicLibraryItems.add(new MusicLibraryItem(R.drawable.steve,
                Utils.STEVE_GIBBS_LOW_LIGHT.replace("_", " "),
                Utils.STEVE_GIBBS));


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new MusicLibraryAdapter(musicLibraryItems, this);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
