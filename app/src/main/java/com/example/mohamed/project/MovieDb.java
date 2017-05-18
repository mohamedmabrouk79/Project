package com.example.mohamed.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mohamed.project.MovieSchema.FavouriteTable;
import com.example.mohamed.project.MovieSchema.MovieTable;

/**
 * Created by mohamed on 5/18/2017.
 */

public class MovieDb extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "movie.db";

    public MovieDb(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ MovieTable.NAME+ "(" +
                " _id integer primary key autoincrement, " +
                MovieTable.COLS.ID + " UNIQUE , " +
                MovieTable.COLS.NAME + ", " +
                MovieTable.COLS.IMGE+
                ")");

        db.execSQL("create table "+ FavouriteTable.NAME+ "(" +
                " _id integer primary key autoincrement, " +
                FavouriteTable.COLS.ID + " UNIQUE ,  " +
                FavouriteTable.COLS.NAME + ", " +
                FavouriteTable.COLS.IMGE+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
