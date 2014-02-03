package de.thegerman.circletd.objects.gems;

import android.graphics.Color;
import de.thegerman.circletd.GameProperties;

public class MagentaGem extends Gem {

	public MagentaGem(float xPos, float yPos) {
		super(xPos, yPos, Color.MAGENTA);
	}

	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		angle += ROTATION_SPEED * timespan/1000f;
		angle %= 360;
		return false;
	}

	@Override
	public int getValue() {
		return 300;
	}

}
