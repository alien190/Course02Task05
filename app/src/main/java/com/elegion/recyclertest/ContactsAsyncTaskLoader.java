package com.elegion.recyclertest;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by IvanovNV on 19.04.2018.
 */

public class ContactsAsyncTaskLoader extends AsyncTaskLoader <String>{

    private String mId;

    public ContactsAsyncTaskLoader(Context context, String id) {
        super(context);
        mId = id;
    }

    @Override
    public String loadInBackground() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        String number = null;

        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
                        + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                new String[]{mId, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                null);

        if (cursor != null && cursor.moveToFirst()) {
            number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();
        }
        return number;
    }

    @Override
    public void onCanceled(String data) {
        Toast.makeText(getContext(), R.string.call_canceled, Toast.LENGTH_SHORT).show();
    }
}
