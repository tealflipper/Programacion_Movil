package mx.examen3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public final class MovieContract {
    private MovieContract() {}

    public static class MovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "movie";

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String GENRE = "genre";
        public static final String LENGTH = "length";
        public static final String DIRECTOR = "director";
        public static final String YEAR = "year";
        public static final String PRICE = "price";
        public static final String IMAGE = "image";
    }
}

