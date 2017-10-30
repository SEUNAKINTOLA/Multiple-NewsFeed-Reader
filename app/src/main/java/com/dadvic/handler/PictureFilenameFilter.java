package com.dadvic.handler;

import com.dadvic.provider.FeedDataContentProvider;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class PictureFilenameFilter implements FilenameFilter {
	private static final String REGEX = "__[^\\.]*\\.[A-Za-z]*";
	
	private Pattern pattern;
	
	public PictureFilenameFilter(String entryId) {
		setEntryId(entryId);
	}

	public PictureFilenameFilter() {

	}

	public void setEntryId(String entryId) {
		pattern = Pattern.compile(entryId+REGEX);
	}

	public boolean accept(File dir, String filename) {
		if (dir != null && dir.equals(FeedDataContentProvider.IMAGEFOLDER_FILE)) { // this should be always true but lets check it anyway
			return pattern.matcher(filename).find();
		} else {
			return false;
		}
	}

}
