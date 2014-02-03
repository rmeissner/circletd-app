package de.thegerman.circletd.upgrades;

import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.R;

public class ShootingTowerSplashUpgrade extends Upgrade<Double> {

	private static final double VALUE_BASE = 0;
	private static final double VALUE_STEP = 50;
	private static final int MAX_LEVEL = 10;

	public ShootingTowerSplashUpgrade() {
		super(GameApplication.getAppContext().getString(R.string.splash_upgrade));
		for (int i = 0; i <= MAX_LEVEL; i ++) {
			addUpgradeLevel(VALUE_BASE + VALUE_STEP * i);
		}
	}

	@Override
	public String getDescription() {
		return GameApplication.getAppContext().getString(R.string.splash_with_number, getNextValue());
	}
}
