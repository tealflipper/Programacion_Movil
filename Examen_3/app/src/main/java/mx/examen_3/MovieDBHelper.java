package mx.examen_3;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MovieDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABSE_NAME = "Movie.db";
    MovieContract.MovieEntry MovieEntry = new MovieContract.MovieEntry();
    private final String CREATE_MOVIE =
            "CREATE TABLE " + MovieEntry.TABLE_NAME + " ( "+
            MovieEntry.ID + " NUMERIC(12,0) PRIMARY KEY, " +
            MovieEntry.TITLE + " TEXT, " +
            MovieEntry.GENRE + " TEXT, "+
            MovieEntry.LENGTH + " NUMERIC(3,0), " +
            MovieEntry.DIRECTOR + " TEXT, " +
            MovieEntry.YEAR + " INTEGER, " +
            MovieEntry.PRICE + " NUMERIC (4,2)," +
            MovieEntry.IMAGE + " TEXT )";

    private final String DELETE_MOVIE =
            "DROP TABLE IF EXIST " + MovieEntry.TABLE_NAME;


    public MovieDBHelper(Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_MOVIE);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}

