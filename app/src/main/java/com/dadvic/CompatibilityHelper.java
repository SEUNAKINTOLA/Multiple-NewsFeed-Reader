package com.dadvic;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.webkit.WebView;

public class CompatibilityHelper {
	private static final String METHOD_GETACTIONBAR = "getActionBar";
	
	private static final String METHOD_SETICON = "setIcon";
	
	private static final String METHOD_ONRESUME = "onResume";
	
	private static final String METHOD_ONPAUSE = "onPause";
	
	public static void setActionBarDrawable(Activity activity, Drawable drawable) {
		try {
			Object actionBar = Activity.class.getMethod(METHOD_GETACTIONBAR).invoke(activity);
			
			actionBar.getClass().getMethod(METHOD_SETICON, Drawable.class).invoke(actionBar, drawable);
		} catch (Exception e) {
			
		}

	}
	
	public static void onResume(WebView webView) {
		try {
			WebView.class.getMethod(METHOD_ONRESUME).invoke(webView);
		} catch (Exception e) {
			
		}
	}
	
	public static void onPause(WebView webView) {
		try {
			WebView.class.getMethod(METHOD_ONPAUSE).invoke(webView);
		} catch (Exception e) {
			
		}
	}
}
