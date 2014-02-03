package de.thegerman.circletd.objects.effects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.thegerman.circletd.GameProperties;

public class SplashDamageEffect extends Effect {

	private static final long LIVE_TIME = 50;
	private float radius;
	private Paint paint ;
	private long timeAlive;

	public SplashDamageEffect(float xPos, float yPos, float radius) {
		super(xPos, yPos);
		this.radius = radius;
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.argb(100, 255, 69, 00));
	}

	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		timeAlive += timespan;
		return timeAlive > LIVE_TIME;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(xPos, yPos, radius, paint);
	}

}
