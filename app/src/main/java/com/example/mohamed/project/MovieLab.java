package com.example.mohamed.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.mohamed.project.MovieSchema.FavouriteTable;
import com.example.mohamed.project.MovieSchema.MovieTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 5/18/2017.
 */
public class MovieLab {
    private static MovieLab movieLab ;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MovieLab getInstance(Context context) {
        if (movieLab==null){
            movieLab=new MovieLab(context);
        }
        return movieLab;
    }

    private MovieLab(Context context) {
        mContext=context.getApplicationContext();
        mDatabase=new MovieDb(mContext).getWritableDatabase();

    }

    public String  insertFavourite(Movie movie){
        ContentValues contentValues=getFavouritValues(movie);
      Long aLong=  mDatabase.insert(FavouriteTable.NAME,null,contentValues);
        return aLong>0?"insert done ":"this movie is added before";
    }

    public String  insertMovies(Movie movie){
        ContentValues contentValues=getMoviesValues(movie);
        Long aLong=  mDatabase.insert(MovieTable.NAME,null,contentValues);
        return aLong>0?"insert done ":"";
    }

    public Movie getMovie(String id){
        MovieWrapper cursor=queryMovies(MovieTable.NAME,MovieTable.COLS.ID+" =?",new String[]{id.toString()});
       try {
           if (cursor.getCount()==0){
               return null;
           }
           cursor.moveToFirst();
           return cursor.getMovie();
       }finally {
           cursor.close();
       }

    }
    public void deleteMovie(String uuid){
        mDatabase.delete(MovieTable.NAME,MovieTable.COLS.ID+" = ?",new String[]{uuid.toString()});
    }
    public void update(Movie  movie){
        String uuid=movie.getId().toString();
        ContentValues values=getFavouritValues(movie);
        mDatabase.update(FavouriteTable.NAME, values,
                FavouriteTable.COLS.ID + " = ?",
                new String[]{uuid});

    }
    public Movie getFavourite(String id){
        MovieWrapper cursor=queryMovies(FavouriteTable.NAME,MovieTable.COLS.ID+" =?",new String[]{id.toString()});
        try {
            if (cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();

            return cursor.getMovie();
        }finally {
            cursor.close();
        }

    }
    private static ContentValues getMoviesValues(Movie movie){
        ContentValues values=new ContentValues();
        values.put(MovieTable.COLS.ID,movie.getId());
        values.put(MovieTable.COLS.NAME, movie.getName());
        values.put(MovieTable.COLS.IMGE, movie.getImage());
        return values;
    }

    private static ContentValues getFavouritValues(Movie movie){
        ContentValues values=new ContentValues();
        values.put(FavouriteTable.COLS.ID,movie.getId());
        values.put(FavouriteTable.COLS.NAME, movie.getName());
        values.put(FavouriteTable.COLS.IMGE, movie.getImage());
        return values;
    }

    public List<Movie> getFavourites(){
        List<Movie> movies=new ArrayList<>();
        MovieWrapper cursor=queryMovies(FavouriteTable.NAME, null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                movies.add(cursor.getMovie());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return movies;

    }

    public List<Movie> getMovies(){
      List<Movie> movies=new ArrayList<>();
        MovieWrapper cursor=queryMovies(MovieTable.NAME, null,null);
   try {

       cursor.moveToFirst();
       while (!cursor.isAfterLast()) {
           movies.add(cursor.getMovie());
           cursor.moveToNext();
       }
   }
   finally {
       cursor.close();
   }
        return movies;

    }

    private MovieWrapper queryMovies(String NAME, String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new MovieWrapper(cursor);
    }


}