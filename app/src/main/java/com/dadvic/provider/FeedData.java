package com.dadvic.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.dadvic.handler.PictureFilenameFilter;

import java.io.File;

public class FeedData {
	public static final String CONTENT = "content://";
	
	public static final String AUTHORITY = "com.dadvic.provider.FeedData";
	
	private static final String TYPE_PRIMARY_KEY = "INTEGER PRIMARY KEY AUTOINCREMENT";
	
	protected static final String TYPE_TEXT = "TEXT";
	
	protected static final String TYPE_DATETIME = "DATETIME";
	
	protected static final String TYPE_INT = "INT";

	protected static final String TYPE_BOOLEAN = "INTEGER(1)";
	
	public static final String FEED_DEFAULTSORTORDER = FeedColumns.PRIORITY;
	
	public static class FeedColumns implements BaseColumns {
		public static final Uri CONTENT_URI = Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append("/feeds").toString());
		
		public static final String URL = "url";
		
		public static final String NAME = "name";
		
		public static final String OTHER_ALERT_RINGTONE = "other_alertringtone";
		
		public static final String ALERT_RINGTONE = "alertringtone";
		
		public static final String SKIP_ALERT = "skipalert";
		
		public static final String LASTUPDATE = "lastupdate";
		
		public static final String ICON = "icon";
		
		public static final String ERROR = "error";
		
		public static final String PRIORITY = "priority";
		
		public static final String FETCHMODE = "fetchmode";
		
		public static final String REALLASTUPDATE = "reallastupdate";
		
		public static final String WIFIONLY = "wifionly";
		
		public static final String[] COLUMNS = new String[] {_ID, URL, NAME, LASTUPDATE, ICON, ERROR, PRIORITY, FETCHMODE, REALLASTUPDATE, ALERT_RINGTONE, OTHER_ALERT_RINGTONE, SKIP_ALERT, WIFIONLY};
		
		public static final String[] TYPES = new String[] {TYPE_PRIMARY_KEY, "TEXT UNIQUE", TYPE_TEXT, TYPE_DATETIME, "BLOB", TYPE_TEXT, TYPE_INT, TYPE_INT, TYPE_DATETIME, TYPE_TEXT, TYPE_INT, TYPE_INT, TYPE_BOOLEAN};
		
		public static final Uri CONTENT_URI(String feedId) {
			return Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append("/feeds/").append(feedId).toString());
		}
		
		public static final Uri CONTENT_URI(long feedId) {
			return Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append("/feeds/").append(feedId).toString());
		}
	}
	
	public static class EntryColumns implements BaseColumns {
		public static final String FEED_ID = "feedid";
		
		public static final String TITLE = "title";
		
		public static final String ABSTRACT = "abstract";
		
		public static final String DATE = "date";
		
		public static final String READDATE = "readdate";
		
		public static final String LINK = "link";
		
		public static final String FAVORITE = "favorite";
		
		public static final String ENCLOSURE = "enclosure";
		
		public static final String GUID = "guid";
		
		public static final String AUTHOR = "author";
		
		public static final String[] COLUMNS = new String[] {_ID, FEED_ID, TITLE, ABSTRACT, DATE, READDATE, LINK, FAVORITE, ENCLOSURE, GUID, AUTHOR};
		
		public static final String[] TYPES = new String[] {TYPE_PRIMARY_KEY, "INTEGER(7)", TYPE_TEXT, TYPE_TEXT, TYPE_DATETIME, TYPE_DATETIME, TYPE_TEXT, TYPE_BOOLEAN, TYPE_TEXT, TYPE_TEXT, TYPE_TEXT};

		public static Uri CONTENT_URI = Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append("/entries").toString());
		
		public static Uri FAVORITES_CONTENT_URI = Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append("/favorites").toString());

		public static Uri CONTENT_URI(String feedId) {
			return Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append("/feeds/").append(feedId).append("/entries").toString());
		}

		public static Uri ENTRY_CONTENT_URI(String entryId) {
			return Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append("/entries/").append(entryId).toString());
		}
		
		public static Uri PARENT_URI(String path) {
			return Uri.parse(new StringBuilder(CONTENT).append(AUTHORITY).append(path.substring(0, path.lastIndexOf('/'))).toString());
		}
		
	}
		
	private static String[] IDPROJECTION = new String[] {FeedData.EntryColumns._ID};
	
	public static void deletePicturesOfFeedAsync(final Context context, final Uri entriesUri, final String selection) {
		if (com.dadvic.provider.FeedDataContentProvider.IMAGEFOLDER_FILE.exists()) {
			new Thread() {
				public void run() {
					deletePicturesOfFeed(context, entriesUri, selection);
				}
			}.start();
		}
	}
	
	public static synchronized void deletePicturesOfFeed(Context context, Uri entriesUri, String selection) {
		if (com.dadvic.provider.FeedDataContentProvider.IMAGEFOLDER_FILE.exists()) {
			PictureFilenameFilter filenameFilter = new PictureFilenameFilter();
			
			Cursor cursor = context.getContentResolver().query(entriesUri, IDPROJECTION, selection, null, null);
			
			while (cursor.moveToNext()) {
				filenameFilter.setEntryId(cursor.getString(0));
				
				File[] files = com.dadvic.provider.FeedDataContentProvider.IMAGEFOLDER_FILE.listFiles(filenameFilter);
				
				for (int n = 0, i = files != null ? files.length : 0; n < i; n++) {
					files[n].delete();
				}
			}
			cursor.close();
		}
	}
	
	public static synchronized void deletePicturesOfEntry(String entryId) {
		if (com.dadvic.provider.FeedDataContentProvider.IMAGEFOLDER_FILE.exists()) {
			PictureFilenameFilter filenameFilter = new PictureFilenameFilter(entryId);
			
			File[] files = com.dadvic.provider.FeedDataContentProvider.IMAGEFOLDER_FILE.listFiles(filenameFilter);
			
			for (int n = 0, i = files != null ? files.length : 0; n < i; n++) {
				files[n].delete();
			}
		}
	}
	

}
