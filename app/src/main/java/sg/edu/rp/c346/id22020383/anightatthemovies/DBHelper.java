package sg.edu.rp.c346.id22020383.anightatthemovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Movies.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MOVIE = "movie";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_RATING = "rating";

    private static final String CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIE +
            "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_GENRE + " TEXT, " +
            COLUMN_YEAR + " INTEGER, " +
            COLUMN_RATING + " TEXT" +
            ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }

    public long insertMovie(String title, String genre, int year, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_RATING, rating);
        return db.insert(TABLE_MOVIE, null, values);

    }

    public ArrayList<Movie> getAllMovies() {

        ArrayList<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));
                int year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR));
                String rating = cursor.getString(cursor.getColumnIndex(COLUMN_RATING));
                movies.add(new Movie(id, title, genre, year, rating));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return movies;
    }

    public ArrayList<Movie> getMoviesByRating(String rating) {
        ArrayList<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATING
        };
        String selection = COLUMN_RATING + "=?";
        String[] selectionArgs = {rating};
        Cursor cursor = db.query(TABLE_MOVIE, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));
                int year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR));
                String movieRating = cursor.getString(cursor.getColumnIndex(COLUMN_RATING));
                movies.add(new Movie(id, title, genre, year, movieRating));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return movies;
    }

    public void updateMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_RATING, movie.getRating());
        db.update(TABLE_MOVIE, values, COLUMN_ID + "=?", new String[]{String.valueOf(movie.getId())});
    }

    public void deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIE, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
}
