package com.wardziniak.reminder.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by wardziniak on 11/12/14.
 */
public class AlaramProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher;

    private static final int ALARM = 1;
    private static final int ALARM_ID = 2;
    private static final int ACTIVE_ALARM = 3;

    private ReminderDBHelper reminderDBHelper;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(DatabaseEntries.AUTHORITY, DatabaseEntries.ALARM_PATH + "#", ALARM_ID);
        sUriMatcher.addURI(DatabaseEntries.AUTHORITY, DatabaseEntries.ALARM_PATH, ALARM);
        sUriMatcher.addURI(DatabaseEntries.AUTHORITY, DatabaseEntries.ACTIVE_ALARM_PATH, ACTIVE_ALARM);
//        sUriMatcher.addURI(DatabaseEntries.AUTHORITY, DatabaseEntries.ACTIVE_ALARM_PATH + "#", ACTIVE_ALARM);
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case ALARM_ID:
                return DatabaseEntries.ALARM_CONTENT_ITEM_TYPE;
            case ALARM:
            case ACTIVE_ALARM:
                return DatabaseEntries.ALARM_CONTENT_DIR_TYPE;
            default:
                throw new IllegalArgumentException("getType() Unknown URI:" + uri);
        }
    }

    @Override
    public boolean onCreate() {
        reminderDBHelper = new ReminderDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = reminderDBHelper.getReadableDatabase();
        Log.d("Reminder", "query()" + uri);
        switch (sUriMatcher.match(uri)) {
            case ALARM:
                qb.setTables(DatabaseEntries.AlarmEntry.TABLE_NAME);
                cursor =  qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ACTIVE_ALARM:
                qb.setTables(DatabaseEntries.AlarmEntry.TABLE_NAME);
                selection = DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_STATUS + " = " + DatabaseEntries.ACTIVE_ALARM_STATUS;
                cursor =  qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ALARM_ID:
                qb.setTables(DatabaseEntries.AlarmEntry.TABLE_NAME);
                selection = DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_ID + " = ?";
                selectionArgs = new String[] {""+ ContentUris.parseId(uri)};
                cursor = qb.query(db,projection,selection,selectionArgs, null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("query() Unknown URI:" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return  cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d("Reminder", "insert()" + uri);
        long id;
        SQLiteDatabase db = reminderDBHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case ALARM:
                contentValues.put(DatabaseEntries.AlarmEntry.COLUMN_NAME_ALA_STATUS, DatabaseEntries.ACTIVE_ALARM_STATUS);
                id = db.insert(DatabaseEntries.AlarmEntry.TABLE_NAME, null, contentValues);
                getContext().getContentResolver().notifyChange(Uri.parse(DatabaseEntries.SCHEME + DatabaseEntries.AUTHORITY + DatabaseEntries.ACTIVE_ALARM_PATH), null);
                break;
            default:
                throw new IllegalArgumentException("insert() Unknown URI:" + uri);
        }
        return Uri.parse(DatabaseEntries.AUTHORITY + DatabaseEntries.ALARM_PATH + id);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        Log.d("Reminder", "update()" + uri);
        SQLiteDatabase db = reminderDBHelper.getWritableDatabase();
        int numberOfUpdated;
        switch (sUriMatcher.match(uri)) {
            case ACTIVE_ALARM:
                numberOfUpdated = db.update(DatabaseEntries.AlarmEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return numberOfUpdated;
            default:
                throw new IllegalArgumentException("update() Unknown URI:" + uri);
        }
    }
}
