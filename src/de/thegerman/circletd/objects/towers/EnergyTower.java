package de.thegerman.circletd.objects.towers;

import java.util.List;

import android.graphics.Color;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.upgrades.EnergyTowerEnergyUpgrade;
import de.thegerman.circletd.upgrades.Upgrade;

public class EnergyTower extends Tower {
	
	EnergyTowerEnergyUpgrade energyUpgrade = new EnergyTowerEnergyUpgrade();

	public EnergyTower(float x, float y, ProviderTower providerTower) {
		super(x, y, 30, providerTower);
		this.paint.setColor(Color.BLUE);
	}

	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		return false;
	}

	@Override
	public TowerType getType() {
		return TowerType.EnergyTower;
	}

	@Override
	public int getEnergyLevel() {
		return energyUpgrade.getCurrentValue();
	}
	
	@Override
	public List<Upgrade<?>> getUpgrades() {
		List<Upgrade<?>> upgrades = super.getUpgrades();
		upgrades.add(energyUpgrade);
		return upgrades;
	}

	@Override
	public int getCosts(GameProperties gameProperties) {
		int energyTowers = 1;
		for (Tower tower : gameProperties.getTowers()) {
			if (tower.getType() == Tower.TowerType.EnergyTower) {
				energyTowers++;
			}
		}
		return 25 * energyTowers;
	}

}
