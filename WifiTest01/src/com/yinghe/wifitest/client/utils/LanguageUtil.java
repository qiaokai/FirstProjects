package com.yinghe.wifitest.client.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

public class LanguageUtil {
	@SuppressLint("DefaultLocale")
	public static String getPinyin(Context context, String input) {
		ContentValues values = new ContentValues();
		Uri rawContactUri = context.getContentResolver().insert(RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(rawContactUri);
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		values.put(StructuredName.GIVEN_NAME, input);
		context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

		String Where = ContactsContract.RawContacts.CONTACT_ID + " =" + rawContactId;
		String[] projection = { "sort_key" };
		Cursor cur = context.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, projection, Where,
				null, null);
		int pinyin1 = cur.getColumnIndex("sort_key");
		cur.moveToFirst();
		String pinyin = cur.getString(pinyin1);

		String result = "";
		for (int i = 0; i < pinyin.length(); i++) {
			String temp = pinyin.substring(i, i + 1);
			if (temp.matches("[a-zA-Z]")) {
				result = result + temp;
			}
		}

		context.getContentResolver().delete(ContentUris.withAppendedId(RawContacts.CONTENT_URI, rawContactId), null,
				null);
		return result.toLowerCase();
	}

}
