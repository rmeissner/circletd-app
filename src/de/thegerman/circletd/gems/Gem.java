package de.thegerman.circletd.gems;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import de.thegerman.circletd.objects.CircleObject;

public abstract class Gem extends CircleObject {
	
	public static float ROTATION_SPEED = 30; 

	private int gemColor;
	protected float angle;
	
	public Gem(float xPos, float yPos, float radius, int gemColor) {
		super(xPos, yPos, radius);
		this.gemColor = gemColor;
		this.angle = 0;
	}
	
	abstract int getValue();
	
	@Override
	public void draw(Canvas canvas) {
		Gem.drawGem(canvas, gemColor, angle, getX(), getY(), angle);
	}

	public static void drawGem(Canvas canvas, int gemColor, float angle, float xPos, float yPos, float radius) {
		Paint color = new Paint(Paint.ANTI_ALIAS_FLAG);
		color.setStyle(Paint.Style.FILL);
		color.setColor(gemColor);
		color.setAntiAlias(true);
		
		Path gemPath = new Path();

		double cos = Math.cos(angle * Math.PI / 180.0);
		double sin = Math.sin(angle * Math.PI / 180.0);
		float x1 = (float) (-1 * sin);
		float y1 = (float) (cos);
		float x2 = (float) (-0.951 * cos - 0.309 * sin);
		float y2 = (float) (-0.951 * sin + 0.309 * cos);
		float x3 = (float) (-0.588 * cos + 0.809 * sin);
		float y3 = (float) (-0.588 * sin - 0.809 * cos);
		float x4 = (float) (0.588 * cos + 0.809 * sin);
		float y4 = (float) (0.588 * sin - 0.809 * cos);
		float x5 = (float) (0.951 * cos - 0.309 * sin);
		float y5 = (float) (0.951 * sin + 0.309 * cos);
		float glow1 = 0.8f;
		float glow2 = 0.6f;
		gemPath.reset();
		gemPath.moveTo((xPos + x1 * radius), (yPos + y1 * radius));
		gemPath.lineTo((xPos + x2 * radius), (yPos + y2 * radius));
		gemPath.lineTo((xPos + x3 * radius), (yPos + y3 * radius));
		gemPath.lineTo((xPos + x4 * radius), (yPos + y4 * radius));
		gemPath.lineTo((xPos + x5 * radius), (yPos + y5 * radius));
		gemPath.lineTo((xPos + x1 * radius), (yPos + y1 * radius));
		gemPath.close();
		color.setAlpha(64);
		canvas.drawPath(gemPath, color);

		gemPath.reset();
		gemPath.moveTo((xPos + x1 * radius * glow1), (yPos + y1 * radius * glow1));
		gemPath.lineTo((xPos + x2 * radius * glow1), (yPos + y2 * radius * glow1));
		gemPath.lineTo((xPos + x3 * radius * glow1), (yPos + y3 * radius * glow1));
		gemPath.lineTo((xPos + x4 * radius * glow1), (yPos + y4 * radius * glow1));
		gemPath.lineTo((xPos + x5 * radius * glow1), (yPos + y5 * radius * glow1));
		gemPath.lineTo((xPos + x1 * radius * glow1), (yPos + y1 * radius * glow1));
		gemPath.close();
		canvas.drawPath(gemPath, color);

		gemPath.reset();
		gemPath.moveTo((xPos + x1 * radius * glow2), (yPos + y1 * radius * glow2));
		gemPath.lineTo((xPos + x2 * radius * glow2), (yPos + y2 * radius * glow2));
		gemPath.lineTo((xPos + x3 * radius * glow2), (yPos + y3 * radius * glow2));
		gemPath.lineTo((xPos + x4 * radius * glow2), (yPos + y4 * radius * glow2));
		gemPath.lineTo((xPos + x5 * radius * glow2), (yPos + y5 * radius * glow2));
		gemPath.lineTo((xPos + x1 * radius * glow2), (yPos + y1 * radius * glow2));
		gemPath.close();
		color.setAlpha(255);
		canvas.drawPath(gemPath, color);
	}

}
