package de.thegerman.circletd.upgrades;

import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.R;

public class ShootingTowerDamageUpgrade extends Upgrade<Integer> {
	
	private static final int VALUE_BASE = 1;
	private static final int VALUE_STEP = 1;
	private static final int MAX_LEVEL = 10;

	public ShootingTowerDamageUpgrade() {
		super(GameApplication.getAppContext().getString(R.string.damage_upgrade));
		for (int i = 0; i <= MAX_LEVEL; i ++) {
			addUpgradeLevel(VALUE_BASE + i * VALUE_STEP);
		}
	}

	@Override
	public String getDescription() {
		return GameApplication.getAppContext().getString(R.string.damage_with_number, getNextValue());
	}
}
