package de.thegerman.circletd.objects.projectiles;

import java.lang.ref.WeakReference;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.creeps.Creep;
import de.thegerman.circletd.objects.effects.SplashDamageEffect;
import de.thegerman.circletd.objects.towers.Tower;
import de.thegerman.circletd.upgrades.ShootingTowerDamageUpgrade;

public class FollowingProjectile extends Projectile {
	
	private WeakReference<Creep> targetReference;
	private Paint paint;
	private ShootingTowerDamageUpgrade upgrade;
	private double splashRadius = 0;
	

	public FollowingProjectile(Tower origin, Creep target, double splashRadius, ShootingTowerDamageUpgrade upgrade) {
		super(origin.getX(), origin.getY(), 10, origin);
		this.targetReference = new WeakReference<Creep>(target);
		this.upgrade = upgrade;
		this.splashRadius = splashRadius;
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.MAGENTA);
	}
	
	@Override
	public int getDamage() {
		return upgrade.getCurrentValue();
	}

	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		if (targetReference != null) {
  		Creep target = targetReference.get();
  		if (target != null && target.isAlive()) {
  			float xd = target.getX() - getX();
  			float yd = target.getY() - getY();
  			float c = (float) Math.sqrt(xd * xd + yd * yd);
  			if(c == 0) c = 1;
  			xspeed = xd / c;
  			yspeed = yd / c;
  		}
		}
		return super.update(timespan, gameProperties);
	}
	
	@Override
	public boolean hitAction(Creep creep, GameProperties gameProperties) {
		if (splashRadius > 0) {
			gameProperties.getEffects().add(new SplashDamageEffect(getX(), getY(), (float) splashRadius));
   		for (Creep c : gameProperties.getCreeps()) {
  			if (!creep.equals(c) && c.isAlive() && c.distanceTo(this) < splashRadius) {
  				c.hitAction(this, gameProperties);
  			}
  		}
		}
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(getX(), getY(), getRadius(), paint);
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public float getSpeed() {
		return 250;
	}

}
