package de.thegerman.circletd.objects.towers;

import de.thegerman.circletd.GameProperties;
import android.graphics.Color;

public class BasicTower extends Tower {
	
	public BasicTower(float x, float y, ProviderTower providerTower) {
		super(x, y, 40, providerTower);
		this.paint.setColor(Color.WHITE);
	}

	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		return false;
	}

	@Override
	public TowerType getType() {
		return TowerType.BasicTower;
	}

	@Override
	public int getEnergyLevel() {
		return 0;
	}

	@Override
	public int getCosts(GameProperties gameProperties) {
		return 0;
	}

	@Override
	public int getUpgradeBaseCosts() {
		return 0;
	}
}
