package de.thegerman.circletd.objects.gems;

import android.graphics.Color;

public class BlueGem extends Gem {

	public BlueGem(float xPos, float yPos) {
		super(xPos, yPos, Color.BLUE);
	}

	@Override
	public int getValue() {
		return 100;
	}

}
