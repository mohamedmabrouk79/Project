package com.example.mohamed.project;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.mohamed.project.MovieSchema.MovieTable;

/**
 * Created by mohamed on 5/18/2017.
 */

public class MovieWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MovieWrapper(Cursor cursor) {
        super(cursor);
    }

    public Movie getMovie(){
        Movie movie =new Movie(getString(getColumnIndex(MovieTable.COLS.ID)),
                getString(getColumnIndex(MovieTable.COLS.IMGE)),
                        getString(getColumnIndex(MovieTable.COLS.NAME)
                        ));

        return movie;
    }
}
