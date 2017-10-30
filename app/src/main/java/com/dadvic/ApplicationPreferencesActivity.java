
package com.dadvic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.dadvic.service.RefreshService;

public class ApplicationPreferencesActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (MainTabActivity.isLightTheme(this)) {
			setTheme(R.style.Theme_Light);
		}
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.preferences);
		
		Preference preference = (Preference) findPreference(Strings.SETTINGS_REFRESHENABLED);

		preference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if (Boolean.TRUE.equals(newValue)) {
					new Thread() {
						public void run() {
							startService(new Intent(ApplicationPreferencesActivity.this, RefreshService.class));
						}
					}.start();
				} else {
					getPreferences(MODE_PRIVATE).edit().putLong(Strings.PREFERENCE_LASTSCHEDULEDREFRESH, 0).commit();
					stopService(new Intent(ApplicationPreferencesActivity.this, RefreshService.class));
				}
				return true;
			}
		});
		
		preference = (Preference) findPreference(Strings.SETTINGS_SHOWTABS);
		preference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if (MainTabActivity.INSTANCE != null ) {
					MainTabActivity.INSTANCE.setTabWidgetVisible(Boolean.TRUE.equals(newValue));
				}
				return true;
			}
		});	
		
		preference = (Preference) findPreference(Strings.SETTINGS_LIGHTTHEME);
		preference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationPreferencesActivity.this).edit();
				
				editor.putBoolean(Strings.SETTINGS_LIGHTTHEME, Boolean.TRUE.equals(newValue));
				editor.commit();
				android.os.Process.killProcess(android.os.Process.myPid());
				
				// this return statement will never be reached
				return true;
			}
		});
		
		preference = (Preference) findPreference(Strings.SETTINGS_EFFICIENTFEEDPARSING);
		preference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(final Preference preference, Object newValue) {
				if (newValue.equals(Boolean.FALSE)) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ApplicationPreferencesActivity.this);
					
					builder.setIcon(android.R.drawable.ic_dialog_alert);
					builder.setTitle(android.R.string.dialog_alert_title);
					builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Editor editor = PreferenceManager.getDefaultSharedPreferences(ApplicationPreferencesActivity.this).edit();
							
							editor.putBoolean(Strings.SETTINGS_EFFICIENTFEEDPARSING, Boolean.FALSE);
							editor.commit();
							((CheckBoxPreference) preference).setChecked(false);
							dialog.dismiss();
						}
					});
					builder.setNegativeButton(android.R.string.cancel, new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					builder.setMessage(R.string.warning_moretraffic);
					builder.show();
					return false;
				} else {
					return true;
				}
			}
		});
	}
	
}
