package de.thegerman.circletd.objects.towers;

import android.graphics.Color;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.creeps.Creep;
import de.thegerman.circletd.objects.projectiles.FollowingProjectile;

public class ShooterTower extends Tower {
	
	public static final long SHOOTING_DELAY = 3000;
	public static final long SHOOTING_RANGE = 400;
	
	private long lastShot;

	public ShooterTower(float x, float y, ProviderTower providerTower) {
		super(x, y, 30, providerTower);
		this.paint.setColor(Color.GREEN);
	}

	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		lastShot += timespan;
		if (lastShot > SHOOTING_DELAY) {
			Creep target = null;
			double minDist = Double.MAX_VALUE;
			for (Creep creep : gameProperties.getCreeps()) {
				float xDist = creep.getX() - getX();
				float yDist = creep.getY() - getY();
				double distance = Math.sqrt(xDist * xDist + yDist * yDist);
				if ((target == null || distance < minDist) && distance < SHOOTING_RANGE) {
					target = creep;
					minDist = distance;
				}
			}
			if (target != null) {
				gameProperties.getProjectiles().add(new FollowingProjectile(this, target));
				lastShot = 0;
			}
		}
		return false;
	}

	@Override
	public TowerType getType() {
		return TowerType.ShooterTower;
	}

	@Override
	public int getEnergyLevel() {
		return -1;
	}

}
