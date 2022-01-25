package net.ariflaksito.tmdbapp_new.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.ariflaksito.tmdbapp_new.models.Movie;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "movie";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MOVIES = "movies";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";

    private static final String CREATE_TABLE_MOVIES = "CREATE TABLE " + TABLE_MOVIES +
            " ( " + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE + " TEXT, " +
            KEY_OVERVIEW + " TEXT, " + KEY_RELEASE_DATE + " TEXT, " + KEY_POSTER_PATH + " TEXT, " +
            KEY_VOTE_AVERAGE + " INTEGER, " + KEY_BACKDROP_PATH + " TEXT);";

    private static final String DROP_TABLE_MOVIES = "DROP TABLES " + TABLE_MOVIES;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_MOVIES + "'");
        onCreate(db);
    }

    public long addMovieDetail(String id, String title, String overview, String release_date, String poster_path, String vote_average, String backdrop_path){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_TITLE, title);
        values.put(KEY_OVERVIEW, overview);
        values.put(KEY_RELEASE_DATE, release_date);
        values.put(KEY_POSTER_PATH, poster_path);
        values.put(KEY_VOTE_AVERAGE, vote_average);
        values.put(KEY_BACKDROP_PATH, backdrop_path);

        long insert = db.insert(TABLE_MOVIES, null, values);

        return insert;
    }

    public void deleteMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public boolean checkMovie(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String findQuery = "SELECT * FROM " + TABLE_MOVIES + " WHERE id=" + id;
        Cursor c = db.rawQuery(findQuery, null);

        if (c.getCount() <= 0){
            c.close();
            return false;
        }
        c.close();
        return true;
    }

    public ArrayList<Movie> getAllMovies(){
        ArrayList<Movie> movieModelArrayList = new ArrayList<Movie>();

        String selectQuery = "SELECT * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do {
                Movie mv = new Movie();

                mv.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                mv.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                mv.setOverview(c.getString(c.getColumnIndex(KEY_OVERVIEW)));
                mv.setRelease_date(c.getString(c.getColumnIndex(KEY_RELEASE_DATE)));
                mv.setPoster_path(c.getString(c.getColumnIndex(KEY_POSTER_PATH)));
                mv.setVote_average(c.getInt(c.getColumnIndex(KEY_VOTE_AVERAGE)));
                mv.setBackdrop_path(c.getString(c.getColumnIndex(KEY_BACKDROP_PATH)));

                movieModelArrayList.add(mv);
            } while (c.moveToNext());
        }

        return movieModelArrayList;
    }
}
