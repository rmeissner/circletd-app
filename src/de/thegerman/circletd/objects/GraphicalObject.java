package de.thegerman.circletd.objects;

import android.graphics.Canvas;

public interface GraphicalObject {
	public void draw(Canvas canvas);
	public boolean contains(float x, float y);
	public void setPosition(float newX, float newY);
}
