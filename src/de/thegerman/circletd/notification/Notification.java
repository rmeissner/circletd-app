package de.thegerman.circletd.notification;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import de.thegerman.circletd.GameProperties;

public class Notification {
	private static final float NOTIFICATION_MARGIN = 20;
	private static final float NOTIFICATION_PADDING = 15;
	private static final float NOTIFICATION_HEIGHT = 50;
	private static final long LIFETIME = 2000;
	private String text;
	private float width;
	private float height;
	private float xPos;
	private float yPos;
	private long timeAlive;
	private Paint paint;
	private Paint textPaint;
	
	public Notification(String text, GameProperties gameProperties) {
		this.text = text;
		this.width = gameProperties.getWidth() - NOTIFICATION_MARGIN * 2;
		this.height = NOTIFICATION_HEIGHT + 2 * NOTIFICATION_PADDING;
		this.xPos = NOTIFICATION_MARGIN;
		this.yPos = gameProperties.getHeight() - NOTIFICATION_MARGIN - this.height;
		this.timeAlive = 0;
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.DKGRAY);
		this.paint.setAlpha(200);
		this.textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.textPaint.setColor(Color.WHITE);
		this.textPaint.setTextSize(NOTIFICATION_HEIGHT);
		this.textPaint.setTextAlign(Align.CENTER);
	}
	
	public void draw(Canvas canvas) {
		canvas.drawRect(xPos, yPos, xPos+width, yPos+height, paint);
		canvas.drawText(text, (xPos + width) / 2, yPos+height-NOTIFICATION_PADDING-5, textPaint);
	}
	
	public boolean update(long timespan) {
		timeAlive += timespan;
		return (timeAlive > LIFETIME);
	}
}
