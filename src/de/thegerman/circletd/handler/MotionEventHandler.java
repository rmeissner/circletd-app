package de.thegerman.circletd.handler;

import de.thegerman.circletd.GameProperties;

public interface MotionEventHandler {

	public abstract boolean handleTouchEvent(int action, float currentX, float currentY, GameProperties gameProperties);

}