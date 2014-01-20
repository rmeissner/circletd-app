package de.thegerman.circletd.gems;

import android.graphics.Color;
import de.thegerman.circletd.GameProperties;

public class GreenGem extends Gem {

	public GreenGem(float xPos, float yPos) {
		super(xPos, yPos, 50, Color.GREEN);
	}

	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		angle += ROTATION_SPEED * timespan/1000f;
		angle %= 360;
		return false;
	}

	@Override
	int getValue() {
		return 50;
	}

}