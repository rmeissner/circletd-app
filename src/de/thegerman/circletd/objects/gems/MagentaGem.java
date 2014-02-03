package de.thegerman.circletd.objects.gems;

import android.graphics.Color;

public class MagentaGem extends Gem {

	public MagentaGem(float xPos, float yPos) {
		super(xPos, yPos, Color.MAGENTA);
	}

	@Override
	public int getValue() {
		return 150;
	}

}
