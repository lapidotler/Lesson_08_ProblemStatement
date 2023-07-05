package sg.edu.rp.c346.id22024044.lesson_08_problemstatement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    // Start version with 1
    // increment by 1 whenever db schema changes.
    private static final int DATABASE_VER = 1;

    // Filename of the database
    private static final String DATABASE_NAME = "song.db";

    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGER = "singer";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_SONG +  "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_SINGER + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_STARS + " INTEGER )";
        db.execSQL(createTableSql);
        Log.i("info" ,"created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);

        // Create table(s) again
        onCreate(db);
    }

    public void insertTask(String title, String singers, int year, int stars){
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        // We use ContentValues object to store the values for the db operation
        ContentValues values = new ContentValues();

        // Store the column name as key and NEEDED FIELDS as value
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGER, singers);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STARS, stars);

        // Insert the row into the TABLE_SONG
        db.insert(TABLE_SONG, null, values);

        // Close the database connection
        db.close();
    }

    public ArrayList<Song> getTitle(boolean ascending) {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder = ascending ? "ASC" : "DESC";
        String query = "SELECT * FROM " + TABLE_SONG + " ORDER BY " + COLUMN_TITLE + " " + sortOrder;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(title, singers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Song> getSinger(boolean ascending) {
        ArrayList<Song> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sortOrder = ascending ? "ASC" : "DESC";
        String query = "SELECT * FROM " + TABLE_SONG + " ORDER BY " + COLUMN_SINGER + " " + sortOrder;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(title, singers, year, stars);
                tasks.add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tasks;
    }
}