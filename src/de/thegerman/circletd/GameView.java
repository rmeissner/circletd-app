package de.thegerman.circletd;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	public static final int WIDTH = 1000;
	public static final long MAX_TIMESPAN = 60;

	private GameThread thread;

	public GameView(Context context) {
		super(context);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if (thread != null) {
			this.thread.setSurfaceSize(width, height);
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new GameThread(holder);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.end();
		thread = null;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.thread != null) {
			return this.thread.handleTouchEvent(event);
		} else {
			return false;
		}
	}

}
