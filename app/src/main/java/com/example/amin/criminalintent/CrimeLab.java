package com.example.amin.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.amin.criminalintent.database.CrimeBaseHelper;
import com.example.amin.criminalintent.database.CrimeCursorWrapper;
import com.example.amin.criminalintent.database.CrimeDBSchema;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Amin on 6/27/2018.
 */

public class CrimeLab {
    private static CrimeLab crimeLab;
    private SQLiteDatabase database;
    private Context context;

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper crimeCursorWrapper = queryCrimes(null, null);

        if (crimeCursorWrapper.getCount() > 0) {

            try {
                crimeCursorWrapper.moveToFirst();

                while (!crimeCursorWrapper.isAfterLast()) {
                    crimes.add(crimeCursorWrapper.getCrime());
                    crimeCursorWrapper.moveToNext();
                }

            } finally {
                crimeCursorWrapper.close();
            }

        }

        return crimes;
    }

    private CrimeLab(Context context){
        this.context = context.getApplicationContext();
        database = new CrimeBaseHelper(this.context).getWritableDatabase();
    }

    public static CrimeLab getInstance(Context context) {
        if (crimeLab == null) {
            crimeLab = new CrimeLab(context);
        }

        return crimeLab;
    }

    public Crime getCrime(UUID crimeId) {
        CrimeCursorWrapper crimeCursorWrapper = queryCrimes("UUID = ?", new String[]{crimeId.toString()});

        if (crimeCursorWrapper.getCount() == 0)
            return null;

        try {
            crimeCursorWrapper.moveToFirst();
            return crimeCursorWrapper.getCrime();
        } finally {
            crimeCursorWrapper.close();
        }
    }

    public int getIndex(UUID crimeId) {
        List<Crime> crimes = getCrimes();

        for (int i = 0; i < crimes.size(); i++) {
            if (crimes.get(i).getId().equals(crimeId))
                return i;
        }

        return -1;
    }

    public void addCrime(Crime crime) {
        ContentValues contentValues = getContentValues(crime);
        database.insert(CrimeDBSchema.CrimeTable.NAME, null, contentValues);
    }

    public void updateCrime(Crime crime) {
        ContentValues contentValues = getContentValues(crime);
        String whereClause = "UUID = ?";
        String[] whereArgs = new String[]{crime.getId().toString()};
        database.update(CrimeDBSchema.CrimeTable.NAME, contentValues, whereClause, whereArgs);
    }

    public ContentValues getContentValues(Crime crime) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CrimeDBSchema.CrimeTable.Cols.UUID, crime.getId().toString());
        contentValues.put(CrimeDBSchema.CrimeTable.Cols.TITLE, crime.getTitle());
        contentValues.put(CrimeDBSchema.CrimeTable.Cols.DATE, crime.getDate().getTime());
        contentValues.put(CrimeDBSchema.CrimeTable.Cols.SUSPECT, crime.getSuspect());
        contentValues.put(CrimeDBSchema.CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return contentValues;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = database.query(CrimeDBSchema.CrimeTable.NAME, null, whereClause, whereArgs, null, null, null);
        CrimeCursorWrapper crimeCursorWrapper = new CrimeCursorWrapper(cursor);
        return crimeCursorWrapper;
    }

    public File getPhotoFile(Crime crime) {
        File externalFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFile == null)
            return null;

        File photoFile = new File(externalFile, crime.getPhotoFileName());
        return photoFile;
    }
}
