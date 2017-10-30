package com.dadvic;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class Animations {
	/** Slide in from right */
	public static final TranslateAnimation SLIDE_IN_RIGHT = generateAnimation(1, 0);
	
	/** Slide in from left */
	public static final TranslateAnimation SLIDE_IN_LEFT = generateAnimation(-1, 0);
	
	/** Slide out to right */
	public static final TranslateAnimation SLIDE_OUT_RIGHT = generateAnimation(0, 1);
	
	/** Slide out to left */
	public static final TranslateAnimation SLIDE_OUT_LEFT = generateAnimation(0, -1);

	/** Duration of one animation */
	private static final long DURATION = 180;
	
	private static TranslateAnimation generateAnimation(float fromX, float toX) {
		TranslateAnimation transformAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, fromX, Animation.RELATIVE_TO_SELF, toX, 0, 0, 0, 0);
	
		transformAnimation.setDuration(DURATION);
		return transformAnimation;
	}
	
}
