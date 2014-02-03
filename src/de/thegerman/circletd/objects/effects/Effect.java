package de.thegerman.circletd.objects.effects;

import de.thegerman.circletd.objects.AnimatedObject;

public abstract class Effect implements AnimatedObject{
	
	float xPos;
	float yPos;
	
	public Effect(float xPos, float yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
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
