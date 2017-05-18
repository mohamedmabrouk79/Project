package com.example.mohamed.project;

/**
 * Created by mohamed on 5/18/2017.
 */

public  class MovieSchema {

    public static  class MovieTable{
        public static String NAME="movie";
        public static class COLS{
            public static String ID="id";
            public static String NAME="name";
            public static String IMGE="image";

        }

    }

    public static class FavouriteTable{
        public static String NAME="favourit";
        public static class COLS{
            public static String ID="id";
            public static String NAME="name";
            public static String IMGE="image";

        }
    }



}
