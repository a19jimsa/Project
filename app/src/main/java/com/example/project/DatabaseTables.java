package com.example.project;

class DatabaseTables {

    static class Quiz {

        static final String TABLE_NAME = "quiz";
        static final String COLUMN_NAME_ID = "id";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_CATEGORY = "category";
        static final String COLUMN_NAME_LOCATION = "location";
    }

    static final String SQL_CREATE_TABLE_QUIZ =
            // "CREATE TABLE quiz (id INTEGER PRIMARY KEY, name TEXT, height INT)"
            "CREATE TABLE " + Quiz.TABLE_NAME + " (" +
                    Quiz.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    Quiz.COLUMN_NAME_NAME + " TEXT," +
                    Quiz.COLUMN_NAME_CATEGORY + " TEXT," +
                    Quiz.COLUMN_NAME_LOCATION + " TEXT)";

    static final String SQL_DELETE_TABLE_QUIZ =
            // "DROP TABLE IF EXISTS quiz"
            "DROP TABLE IF EXISTS " + Quiz.TABLE_NAME;

}
