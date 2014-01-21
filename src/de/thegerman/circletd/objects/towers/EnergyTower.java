package de.thegerman.circletd.objects.towers;

import android.graphics.Color;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.creeps.Creep;
import de.thegerman.circletd.objects.projectiles.FollowingProjectile;

public class EnergyTower extends Tower {

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
		return 1;
	}

	@Override
	public int getCosts() {
		return 25;
	}

}
