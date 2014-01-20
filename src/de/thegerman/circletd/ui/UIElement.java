package de.thegerman.circletd.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.GraphicalObject;

public abstract class UIElement implements GraphicalObject {

	protected float xPos;
	protected float yPos;
	protected GameProperties gameProperties;
	protected Paint textPaint;
	public static float INTERFACE_ELEMENT_WIDTH = 200;
	public static float INTERFACE_ELEMENT_HEIGHT = 70;
	public static float INTERFACE_ELEMENT_TEXT_SIZE = 55;
	public static float INTERFACE_ELEMENT_TEXT_Y = INTERFACE_ELEMENT_TEXT_SIZE;

	public UIElement(float xPos, float yPos, GameProperties gameProperties) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.gameProperties = gameProperties;
		this.textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.textPaint.setColor(Color.WHITE);
		this.textPaint.setTextSize(INTERFACE_ELEMENT_TEXT_SIZE);
		this.textPaint.setTextAlign(Align.RIGHT);
	}

	@Override
	public boolean contains(float x, float y) {
		return false;
	}

	@Override
	public void setPosition(float newX, float newY) {
		xPos = newX;
		yPos = newY;
	}

}