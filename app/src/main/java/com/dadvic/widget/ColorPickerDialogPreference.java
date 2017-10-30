
package com.dadvic.widget;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.dadvic.R;

public class ColorPickerDialogPreference extends DialogPreference {
	private SeekBar redSeekBar;
	
	private SeekBar greenSeekBar;
	
	private SeekBar blueSeekBar;
	
	private SeekBar transparencySeekBar;
	
	int color;
	
	public ColorPickerDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		color = com.dadvic.widget.SparseRSSAppWidgetProvider.STANDARD_BACKGROUND;
	}
	
	@Override
	protected View onCreateDialogView() {
		final View view = super.onCreateDialogView();
		
		view.setBackgroundColor(color);
		
		redSeekBar = (SeekBar) view.findViewById(R.id.seekbar_red);
		greenSeekBar = (SeekBar) view.findViewById(R.id.seekbar_green);
		blueSeekBar = (SeekBar) view.findViewById(R.id.seekbar_blue);
		transparencySeekBar = (SeekBar) view.findViewById(R.id.seekbar_transparency);
		
		int _color = color;
		
		transparencySeekBar.setProgress(((_color / 0x01000000)*100)/255);
		_color %= 0x01000000;
		redSeekBar.setProgress(((_color / 0x00010000)*100)/255);
		_color %= 0x00010000;
		greenSeekBar.setProgress(((_color / 0x00000100)*100)/255);
		_color %= 0x00000100;
		blueSeekBar.setProgress((_color*100)/255);
		
		OnSeekBarChangeListener onSeekBarChangeListener = new OnSeekBarChangeListener() {

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int red = (redSeekBar.getProgress()*255) / 100;
				
				int green = (greenSeekBar.getProgress()*255) / 100;
				
				int blue = (blueSeekBar.getProgress()*255) / 100;
				
				int transparency = (transparencySeekBar.getProgress()*255) / 100;
				
				color = transparency*0x01000000 + red*0x00010000 + green*0x00000100 + blue;
				view.setBackgroundColor(color);
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
		};
		
		redSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
		greenSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
		blueSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
		transparencySeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
		return view;
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		if (positiveResult) {
			persistInt(color);
		}
		super.onDialogClosed(positiveResult);
	}

}
