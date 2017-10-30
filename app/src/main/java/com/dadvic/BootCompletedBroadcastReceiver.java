package com.dadvic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;

import com.dadvic.service.RefreshService;

public class BootCompletedBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.createPackageContext(com.dadvic.Strings.PACKAGE, 0));
			
			preferences.edit().putLong(com.dadvic.Strings.PREFERENCE_LASTSCHEDULEDREFRESH, 0).commit();
			if (preferences.getBoolean(com.dadvic.Strings.SETTINGS_REFRESHENABLED, false)) {
				context.startService(new Intent(context, RefreshService.class));
			}
			context.sendBroadcast(new Intent(com.dadvic.Strings.ACTION_UPDATEWIDGET));
		} catch (NameNotFoundException e) {
		}
	}

}
