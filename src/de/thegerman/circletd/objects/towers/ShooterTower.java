package de.thegerman.circletd.objects.towers;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.creeps.Creep;
import de.thegerman.circletd.objects.projectiles.FollowingProjectile;
import de.thegerman.circletd.upgrades.ShootingTowerDamageUpgrade;
import de.thegerman.circletd.upgrades.ShootingTowerDelayUpgrade;
import de.thegerman.circletd.upgrades.ShootingTowerRangeUpgrade;
import de.thegerman.circletd.upgrades.Upgrade;

public class ShooterTower extends Tower {
	
	private long lastShot = Long.MAX_VALUE;
	private ShootingTowerDelayUpgrade speedUpgrade;
	private ShootingTowerRangeUpgrade rangeUpgrade;
	private ShootingTowerDamageUpgrade damageUpgrade;
	private Paint rangePaint;

	public ShooterTower(float x, float y, ProviderTower providerTower) {
		super(x, y, 40, providerTower);
		this.paint.setColor(Color.GREEN);
		this.speedUpgrade = new ShootingTowerDelayUpgrade();
		this.rangeUpgrade = new ShootingTowerRangeUpgrade();
		this.damageUpgrade = new ShootingTowerDamageUpgrade();
		this.rangePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.rangePaint.setStyle(Style.STROKE);
		this.rangePaint.setColor(Color.WHITE);
	}

	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		if (lastShot < (Long.MAX_VALUE - timespan)) {
			lastShot += timespan;
		}
		if (lastShot > getShootingDelay()) {
			Creep target = null;
			double minDist = Double.MAX_VALUE;
			for (Creep creep : gameProperties.getCreeps()) {
				float xDist = creep.getX() - getX();
				float yDist = creep.getY() - getY();
				double distance = Math.sqrt(xDist * xDist + yDist * yDist);
				if ((target == null || distance < minDist) && distance < getShootingRange()) {
					target = creep;
					minDist = distance;
				}
			}
			if (target != null) {
				gameProperties.getProjectiles().add(new FollowingProjectile(this, target, damageUpgrade));
				lastShot = 0;
			}
		}
		return false;
	}
	
	@Override
	protected void additionalDraw(Canvas canvas) {
		canvas.drawCircle(x, y, (float) getShootingRange(), rangePaint);
	}

	private double getShootingRange() {
		return this.rangeUpgrade.getCurrentValue();
	}

	private long getShootingDelay() {
		return this.speedUpgrade.getCurrentValue();
	}

	@Override
	public TowerType getType() {
		return TowerType.ShooterTower;
	}

	@Override
	public int getEnergyLevel() {
		return -1;
	}

	@Override
	public int getCosts(GameProperties gameProperties) {
		return 100;
	}

	@Override
	public List<Upgrade<?>> getUpgrades() {
		List<Upgrade<?>> upgrades = super.getUpgrades();
		upgrades.add(damageUpgrade);
		upgrades.add(speedUpgrade);
		upgrades.add(rangeUpgrade);
		return upgrades;
	}

	@Override
	public int getUpgradeBaseCosts() {
		return 25;
	}

}
