package com.allebd.dispatchsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.allebd.dispatchsystem.models.Users;

/**
 * Created by CSISPC on 08/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ambdispatch.db";

//    public interface DatabaseHandler<T> {
//        void onComplete(boolean success, T result);
//    }

//    private static abstract class DatabaseAsyncTask<T> extends AsyncTask<Void, Void, T> {
//
//        private DatabaseHandler<T> handler;
//        private RuntimeException error;
//
//        public DatabaseAsyncTask(DatabaseHandler<T> handler) {
//            this.handler = handler;
//        }
//
//        @Override
//        protected T doInBackground(Void... params) {
//            try {
//                return executeMethod();
//            } catch (RuntimeException error) {
//                this.error = error;
//                return null;
//            }
//        }
//
//        protected abstract T executeMethod();
//
//        @Override
//        protected void onPostExecute(T result) {
//            handler.onComplete(error == null, result);
//        }
//    }

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "latitude REAL," +
                "longitude REAL," +
                "username TEXT," +
                "password TEXT," +
                "telephone REAL," +
                "dob DATE," +
                "gender TEXT," +
                "bloodGroup TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users;");
        onCreate(db);
    }

    public void insertUser(Users user) {
        ContentValues values = new ContentValues();
//        values.put("latitude", user.getLatitude());
//        values.put("longitude", user.getLongitude());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("telephone", user.getTelephone());
        values.put("dob", user.getDob());
        values.put("gender", user.getGender());
        values.put("bloodGroup", user.getBloodGroup());

        db.insertOrThrow("users", null, values);
    }

//    public void insertUserAsync(final Users user, DatabaseHandler<Void> handler) {
//        new DatabaseAsyncTask<Void>(handler) {
//            @Override
//            protected Void executeMethod() {
//                insertUser(user);
//                return null;
//            }
//        }.execute();
//    }

    public Users selectUsers() {
        Users user = new Users();

        Cursor cursor = db.rawQuery("SELECT * FROM users ORDER BY id LIMIT 1", null);
        try {
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();

//                user.setId(cursor.getLong(cursor.getColumnIndex("id")));
//                user.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
//                user.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
                user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
                user.setDob(cursor.getString(cursor.getColumnIndex("dob")));
                user.setBloodGroup(cursor.getString(cursor.getColumnIndex("bloodGroup")));

            } else {
                return null;
            }
        } finally {
            cursor.close();
        }

        return user;
    }

//    public void selectPositionAsync(DatabaseHandler<Users> handler) {
//        new DatabaseAsyncTask<Users>(handler) {
//            @Override
//            protected Users executeMethod() {
//                return selectUsers();
//            }
//        }.execute();
//    }

    public void deleteUser(long id) {
        if (db.delete("users", "id = ?", new String[] { String.valueOf(id) }) != 1) {
            throw new SQLException();
        }
    }

//    public void deleteUserAsync(final long id, DatabaseHandler<Void> handler) {
//        new DatabaseAsyncTask<Void>(handler) {
//            @Override
//            protected Void executeMethod() {
//                deleteUser(id);
//                return null;
//            }
//        }.execute();
//    }

}
