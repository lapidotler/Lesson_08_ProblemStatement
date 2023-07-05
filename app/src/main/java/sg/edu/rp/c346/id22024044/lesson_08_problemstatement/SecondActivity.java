package sg.edu.rp.c346.id22024044.lesson_08_problemstatement;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends MainActivity{
    ListView lvSong;
    Button btnSortByTitle;
    Button btnSortBySinger;
    Button btnReturn;
    boolean ascendingOrder = true; // Flag to keep track of the sorting order

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lvSong = findViewById(R.id.lvSongs);
        btnSortByTitle = findViewById(R.id.btnSortByTitle);
        btnSortBySinger = findViewById(R.id.btnSortBySingers);
        btnReturn = findViewById(R.id.btnBack);

        ArrayList<String> songList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
        lvSong.setAdapter(adapter);

        // Retrieve the data from the Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String singer = intent.getStringExtra("singer");
        int year = intent.getIntExtra("year", 0);
        int stars = intent.getIntExtra("stars", 0);

        // Display the data in the ListView
        String songInfo = title + "\n" + singer + "\n" + year + "\n" + stars;
        songList.add(songInfo);
        adapter.notifyDataSetChanged();

        btnSortByTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(SecondActivity.this);

                // Insert a task (For Title)
                // Get the task data with the specified sorting order
                ArrayList<Song> songTitle = db.getTitle(ascendingOrder);
                db.close();

                songList.clear();
                for (Song task : songTitle) {
                    songList.add(songInfo);
                }
                adapter.notifyDataSetChanged();

                // Toggle the sorting order for the next button click
                ascendingOrder = !ascendingOrder;
            }
        });

        btnSortBySinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(SecondActivity.this);

                // Insert a task (For Singer)
                // Get the task data with the specified sorting order
                ArrayList<Song> songTitle = db.getSinger(ascendingOrder);
                db.close();

                songList.clear();
                for (Song task : songTitle) {
                    songList.add(songInfo);
                }
                adapter.notifyDataSetChanged();

                // Toggle the sorting order for the next button click
                ascendingOrder = !ascendingOrder;
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
