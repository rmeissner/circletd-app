package de.thegerman.circletd.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.gems.Gem;

public class GemsUIElement extends UIElement {
	
	private Paint paint;
	public GemsUIElement(float xPos, float yPos, GameProperties gameProperties) {
		super(xPos, yPos, gameProperties);
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.CYAN);
	}

	@Override
	public void draw(Canvas canvas) {
		float radius = INTERFACE_ELEMENT_HEIGHT / 2;
		Gem.drawGem(canvas, Color.WHITE, 0, xPos + radius, yPos + radius, radius);
		canvas.drawText(String.valueOf(gameProperties.getGemCount()), xPos + INTERFACE_ELEMENT_WIDTH, yPos + INTERFACE_ELEMENT_TEXT_Y, textPaint);
	}

}
