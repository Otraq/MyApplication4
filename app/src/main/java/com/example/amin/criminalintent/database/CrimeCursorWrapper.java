package com.example.amin.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.amin.criminalintent.Crime;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Amin on 7/5/2018.
 */

public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeDBSchema.CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeDBSchema.CrimeTable.Cols.TITLE));
        Long dateLong = getLong(getColumnIndex(CrimeDBSchema.CrimeTable.Cols.DATE));
        String suspect = getString(getColumnIndex(CrimeDBSchema.CrimeTable.Cols.SUSPECT));
        boolean solved = (getInt(getColumnIndex(CrimeDBSchema.CrimeTable.Cols.SOLVED)) == 1);

        UUID uuid = null;
        if (uuidString != null)
            uuid = UUID.fromString(uuidString);

        Date date = null;
        if (dateLong != null)
            date = new Date(dateLong);

        Crime crime = new Crime(uuid);
        crime.setTitle(title);
        crime.setDate(date);
        crime.setSuspect(suspect);
        crime.setSolved(solved);

        return crime;
    }
}
