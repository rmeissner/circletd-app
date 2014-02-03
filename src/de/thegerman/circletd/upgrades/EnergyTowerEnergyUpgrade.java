package de.thegerman.circletd.upgrades;

import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.R;

public class EnergyTowerEnergyUpgrade extends Upgrade<Integer> {

	private static final int VALUE_BASE = 1;
	private static final int VALUE_STEP = 1;
	private static final int PRICE_STEP = 25;
	private static final int MAX_LEVEL = 10;

	public EnergyTowerEnergyUpgrade() {
		super(GameApplication.getAppContext().getString(R.string.energy_upgrade));
		for (int i = 1; i <= MAX_LEVEL; i ++) {
			addUpgradeLevel(new UpgradeLevel<Integer>(PRICE_STEP * i, VALUE_BASE + i * VALUE_STEP));
		}
	}

	@Override
	public String getDescription() {
		return GameApplication.getAppContext().getString(R.string.energy_with_number, getNextValue());
	}
}
