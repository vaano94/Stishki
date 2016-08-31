package com.stishki.iva.Database;

import android.provider.BaseColumns;

/**
 * Created by Ivan on 12/1/2015.
 */
public class PoemContract {

    public PoemContract() {}

    public static abstract class NewPoemEntry implements BaseColumns {
        public static final String TABLE_NAME = "NewPoems";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_GENRE = "genre";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_LIKES = "likes";
        public static final String COLUMN_DISLIKES = "dislikes";
    }

    public static abstract class PopularPoemEntry implements BaseColumns {
        public static final String TABLE_NAME = "PopularPoems";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_GENRE = "genre";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_LIKES = "likes";
        public static final String COLUMN_DISLIKES = "dislikes";
    }


    public static abstract class UserPoemEntry implements BaseColumns {
        public static final String TABLE_NAME = "UserPoems";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_GENRE = "genre";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_LIKES = "likes";
        public static final String COLUMN_DISLIKES = "dislikes";

    }

}
