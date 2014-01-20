package de.thegerman.circletd;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import de.thegerman.circletd.levels.Level;
import de.thegerman.circletd.levels.TestLevel;

public class GameThread extends Thread {
	private static final int MAXFPS = 60;
	private long lastRunTime;
	private boolean running = true;
	private SurfaceHolder mSurfaceHolder;
	private GameProperties gameProperties;
	private Level level;

	public GameThread(SurfaceHolder holder) {
		this.mSurfaceHolder = holder;
		this.gameProperties = new GameProperties();
		this.level = new TestLevel(this.gameProperties);
	}

	@Override
	public void run() {
		lastRunTime = System.currentTimeMillis();
		while (running) {
			if (!level.isInitialized() || level.getMainBase().getLives() <= 0) {
				level.initialize();
			}
			if (gameProperties.getWidth() > 0 && gameProperties.getHeight() > 0) {
				Canvas canvas = null;
				try {
					if(!gameProperties.isGamePaused()) {
						updateLevel();
					}
					synchronized (this.mSurfaceHolder) {
						canvas = mSurfaceHolder.lockCanvas();
						if(canvas == null) continue; 
						float ratio = gameProperties.getRatio();
						canvas.scale(ratio, ratio);
						level.draw(canvas);
					}
				} finally {
					if (canvas != null) {
						mSurfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
			try {
				GameThread.sleep((long) (1000 / MAXFPS));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateLevel() {
		long timespan = Math.min(System.currentTimeMillis() - lastRunTime, GameView.MAX_TIMESPAN);
		level.update(timespan);
		lastRunTime = System.currentTimeMillis();
	}

	public boolean handleTouchEvent(MotionEvent event) {
		return level.handleTouchEvent(event);
	}

	public void setSurfaceSize(int newWidth, int newHeight) {
		synchronized (mSurfaceHolder) {
			if (gameProperties.getWidth() != newWidth || gameProperties.getHeight() != newHeight) {
				float ratio = ((float) newWidth) / GameView.WIDTH;
				gameProperties.setRatio(ratio);
				gameProperties.setWidth((int) (newWidth / ratio));
				gameProperties.setHeight((int) (newHeight / ratio));
			}
		}
	}

	public void end() {
		running = false;
	}
}
