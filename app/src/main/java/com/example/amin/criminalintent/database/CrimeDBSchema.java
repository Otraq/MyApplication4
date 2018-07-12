package com.example.amin.criminalintent.database;

/**
 * Created by Amin on 7/5/2018.
 */

public class CrimeDBSchema {
    public static final String NAME = "CrimeDB";
    public static final int VERSION = 1;

    public static final class CrimeTable {
        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SUSPECT = "suspect";
            public static final String SOLVED = "solved";
        }
    }
}
