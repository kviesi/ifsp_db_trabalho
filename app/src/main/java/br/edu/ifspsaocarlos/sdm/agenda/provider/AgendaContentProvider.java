package br.edu.ifspsaocarlos.sdm.agenda.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by viesi on 11/12/15.
 */
public class AgendaContentProvider extends ContentProvider {

    private SQLiteHelper dbHelper;

    private UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        dbHelper = new SQLiteHelper(this.getContext());
        uriMatcher = AgendaProviderContract.getMatcher();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch (uriMatcher.match(uri)) {

            case AgendaProviderContract.Agenda.OPERATION_LIST:

               return dbHelper.getReadableDatabase()
                              .query(SQLiteHelper.DATABASE_TABLE, projection, selection, selectionArgs, null, null, sortOrder);

            case AgendaProviderContract.Agenda.OPERATION_GET_BY_ID:

                selection = AgendaProviderContract.Agenda.KEY_ID + " = ? ";
                selectionArgs = new String[] { uri.getLastPathSegment() };

                return dbHelper.getReadableDatabase()
                               .query(SQLiteHelper.DATABASE_TABLE, projection, selection, selectionArgs, null, null, sortOrder);

            case AgendaProviderContract.Agenda.OPERATION_GET_BY_NAME_EMAIL:

                selection = AgendaProviderContract.Agenda.KEY_EMAIL + " LIKE ? OR " +
                            AgendaProviderContract.Agenda.KEY_NAME + " LIKE ? ";

                selectionArgs = new String[] { "%" + uri.getLastPathSegment() + "%" };


                return dbHelper.getReadableDatabase()
                               .query(SQLiteHelper.DATABASE_TABLE, projection, selection, selectionArgs, null, null, sortOrder);

            default:
                throw new IllegalArgumentException("URI invalid to query");

        }

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int operation = uriMatcher.match(uri);
        if(operation == AgendaProviderContract.Agenda.OPERATION_LIST || operation == AgendaProviderContract.Agenda.OPERATION_GET_BY_NAME_EMAIL) {
            return AgendaProviderContract.Agenda.MEDIA_TYPE_ALL;
        }  else {
            return AgendaProviderContract.Agenda.MEDIA_TYPE_ITEM;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int operation = uriMatcher.match(uri);
        if(operation != AgendaProviderContract.Agenda.OPERATION_LIST) {
            throw new IllegalArgumentException("Uri invalid to insert.");
        }

        long pk = dbHelper.getWritableDatabase().insert(SQLiteHelper.DATABASE_TABLE, null, values);

        return uri.withAppendedPath(AgendaProviderContract.Agenda.CONTENT_URI, String.valueOf(pk));
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int operation = uriMatcher.match(uri);
        if(operation != AgendaProviderContract.Agenda.OPERATION_GET_BY_ID) {
            throw new IllegalArgumentException("Uri invalid to delete");
        }

        selection = AgendaProviderContract.Agenda.KEY_ID + " = ? ";
        selectionArgs = new String[] { uri.getLastPathSegment() };

        return dbHelper.getWritableDatabase().delete(SQLiteHelper.DATABASE_TABLE, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int operation = uriMatcher.match(uri);
        if(operation != AgendaProviderContract.Agenda.OPERATION_GET_BY_ID) {
            throw new IllegalArgumentException("Uri invalid to update");
        }

        selection = AgendaProviderContract.Agenda.KEY_ID + " = ?";
        selectionArgs = new String[] {  uri.getLastPathSegment() };

        return dbHelper.getWritableDatabase().update(SQLiteHelper.DATABASE_TABLE, values, selection, selectionArgs);

    }
}
