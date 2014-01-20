package de.thegerman.circletd.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.thegerman.circletd.GameProperties;

public class EnergyUIElement extends UIElement {
	
	private Paint paint;
	public EnergyUIElement(float xPos, float yPos, GameProperties gameProperties) {
		super(xPos, yPos, gameProperties);
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.RED);
	}

	@Override
	public void draw(Canvas canvas) {
		float radius = INTERFACE_ELEMENT_HEIGHT / 2;
		canvas.drawCircle(xPos + radius, yPos + radius, radius, paint);
		canvas.drawText(String.valueOf(gameProperties.getEnergy()), xPos + INTERFACE_ELEMENT_WIDTH, yPos + INTERFACE_ELEMENT_TEXT_Y, textPaint);
	}

}
