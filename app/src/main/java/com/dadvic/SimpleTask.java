package com.dadvic;


public abstract class SimpleTask implements Runnable {
	private boolean canceled = false;
	
	private int postCount = 0;
	
	public abstract void runControlled();
	
	public void cancel() {
		canceled = true;
	}
	
	public boolean isCanceled() {
		return canceled;
	}
	
	public void post() {
		post(1);
	}
	
	public synchronized void post(int count) {
		postCount += count;
		canceled = false;
	}
	
	public boolean isPosted() {
		return postCount > 0;
	}
	
	public int getPostCount() {
		return postCount;
	}

	public final synchronized void run() {
		if (!canceled) {
			runControlled();
		}
		postRun();
		postCount--;
	}
	
	/**
	 * Override to use
	 */
	public void postRun() {
		
	}

	public void enable() {
		canceled = false;
	}

}
