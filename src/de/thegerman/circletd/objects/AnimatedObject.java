package de.thegerman.circletd.objects;

import de.thegerman.circletd.GameProperties;


public interface AnimatedObject extends GraphicalObject {
	
	/**
	 * @param timespan the time span for which the object should be updated
	 * @param gameProperties TODO
	 * @return indicates whether the object should be removed
	 */
	public boolean update(long timespan, GameProperties gameProperties);
}
