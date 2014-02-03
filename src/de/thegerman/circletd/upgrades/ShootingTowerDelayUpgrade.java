package de.thegerman.circletd.upgrades;

import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.R;

public class ShootingTowerDelayUpgrade extends Upgrade<Long> {
	
	private static final float VALUE_BASE = 0.3f;
	private static final float VALUE_STEP = 0.3f;
	private static final int MAX_LEVEL = 10;

	public ShootingTowerDelayUpgrade() {
		super(GameApplication.getAppContext().getString(R.string.speed_upgrade));
		
		for (int i = 0; i <= MAX_LEVEL; i ++) {
			addUpgradeLevel((long) (1000 / (VALUE_BASE + VALUE_STEP * i)));
		}
	}

	@Override
	public String getDescription() {
		return GameApplication.getAppContext().getString(R.string.hits_per_second_with_number, (1000f / getNextValue()));
	}
}
