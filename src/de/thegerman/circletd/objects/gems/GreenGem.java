package de.thegerman.circletd.objects.gems;

import android.graphics.Color;

public class GreenGem extends Gem {

	public GreenGem(float xPos, float yPos) {
		super(xPos, yPos, Color.GREEN);
	}

	@Override
	public int getValue() {
		return 50;
	}

}
