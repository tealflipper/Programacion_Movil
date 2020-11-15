package mx.examen3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MovieDBAdapter {
    private MovieDBHelper movieDBHelper;
    MovieContract.MovieEntry MovieEntry = new MovieContract.MovieEntry();

    public MovieDBAdapter(Context context) {
        movieDBHelper = new MovieDBHelper(context);
    }

    public long insertMovie(Movie movie) throws SQLException {

        SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.ID, movie.getId()); //12 digit long
        contentValues.put(MovieContract.MovieEntry.TITLE, movie.getTitle().toString()); //String
        contentValues.put(MovieContract.MovieEntry.GENRE, movie.getGenres().toString()); //String
        contentValues.put(MovieContract.MovieEntry.LENGTH, movie.getLength()); //3 digit number
        contentValues.put(MovieContract.MovieEntry.DIRECTOR, movie.getDirector()); //String
        contentValues.put(MovieContract.MovieEntry.YEAR, movie.getYear());//int
        contentValues.put(MovieContract.MovieEntry.PRICE, movie.getPrice());// 4 digits 2 decimals
        contentValues.put(MovieContract.MovieEntry.IMAGE, movie.getImageFile()); //String

        long id= sqLiteDatabase.insertOrThrow(MovieContract.MovieEntry.TABLE_NAME, null,
                contentValues);
        return id;
    }

    public ArrayList<Movie> getMovies(){
        SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();
        String[] columns = {
                MovieEntry.ID,
                MovieEntry.TITLE,
                MovieEntry.GENRE,
                MovieEntry.LENGTH,
                MovieEntry.DIRECTOR,
                MovieEntry.YEAR,
                MovieEntry.PRICE,
                MovieEntry.IMAGE };
        Cursor cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, columns,null,
                null,null,null,null);
        ArrayList <Movie> movies = new ArrayList<>();

        while (cursor.moveToNext()){
            Movie movie = new Movie();
            movie.setId(cursor.getLong(cursor.getColumnIndex(MovieEntry.ID)));
            movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieEntry.TITLE)));
            movie.setGenre(cursor.getString(cursor.getColumnIndex(MovieEntry.GENRE)));
            movie.setLength(cursor.getDouble(cursor.getColumnIndex(MovieEntry.LENGTH)));
            movie.setDirector(cursor.getString(cursor.getColumnIndex(MovieEntry.DIRECTOR)));
            movie.setYear(cursor.getInt(cursor.getColumnIndex(MovieEntry.YEAR)));
            movie.setPrice(cursor.getDouble(cursor.getColumnIndex(MovieEntry.PRICE)));
            movie.setImageFile(cursor.getString(cursor.getColumnIndex(MovieEntry.IMAGE)));
            movies.add(movie);
        }
        sqLiteDatabase.close();
        return movies;
    }

    public ArrayList<String> getMovieImages(){
        SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();
        String[] columns = {
                MovieEntry.IMAGE };
        Cursor cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, columns,null,
                null,null,null,null);
        ArrayList <String> movieImages = new ArrayList<>();

        while (cursor.moveToNext()){
            String movieImage =cursor.getString(cursor.getColumnIndex(MovieEntry.IMAGE));
            movieImages.add(movieImage);
        }
        sqLiteDatabase.close();
        return movieImages;
    }

}
