package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Institute {
    //*****Database name and version*****
    private static final String DATABASE_NAME = "institute";
    private static final int DATABASE_VERSION = 1;

    //*****Table: Student and Fields*****
    private static final String TABLE_STUDENT = "student";
    private static final String STUDENT_FNAME = "first_name";
    private static final String STUDENT_LNAME = "last_name";
    private static final String STUDENT_EMAIL = "email";

    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_STUDENT +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            STUDENT_FNAME + " TEXT, " +
            STUDENT_LNAME + " TEXT, " +
            STUDENT_EMAIL + " TEXT)";

    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase ourDB;

    public DB_Institute(Context context) {
        this.context = context;
    }

    //*****Student Table: CRUD*****
    public long newStudent(String firstName, String lastName, String email) {

        ContentValues cv = new ContentValues();
        cv.put(STUDENT_FNAME, firstName);
        cv.put(STUDENT_LNAME, lastName);
        cv.put(STUDENT_EMAIL, email);

        return ourDB.insert(TABLE_STUDENT, null, cv);
    }

    public void updateRecord(int id, String firstName, String lastName, String email) {
        ourDB.execSQL("UPDATE " + TABLE_STUDENT + " SET " +
                STUDENT_FNAME + " = '" + firstName + "', " +
                STUDENT_LNAME + " = '" + lastName + "', " +
                STUDENT_EMAIL + " = '" + email + "' " +
                "where id = " + id
        );
    }

    public void deleteRecord(int id) {
        ourDB.execSQL("DELETE FROM " + TABLE_STUDENT + " " + "WHERE id = " + id);
    }

    public String getAllRecords() {
        String result = "";

        String[] columns = {"id", STUDENT_FNAME, STUDENT_LNAME, STUDENT_EMAIL};

        //Cursor goes into the table's records rows and column wise and brings all values
        Cursor c = ourDB.rawQuery("SELECT * FROM " + TABLE_STUDENT + " order by id desc", null);

        int iId = c.getColumnIndex("id");
        int iFname = c.getColumnIndex(STUDENT_FNAME);
        int iLname = c.getColumnIndex(STUDENT_LNAME);
        int iEmail = c.getColumnIndex(STUDENT_EMAIL);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result += "ID: " + c.getString(iId) + "\n" + c.getString(iFname) + " " + c.getString(iLname) + " " + c.getString(iEmail) + "\n\n";
        }
        return result;
    }

    //*****Create Database and Tables*****
    public DB_Institute open() throws SQLException {
        dbHelper = new DBHelper(context);
        ourDB = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    //*****Create Database and Tables*****
    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_STUDENT_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
            onCreate(db);
        }
    }
}
