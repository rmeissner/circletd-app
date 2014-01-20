package de.thegerman.circletd.objects.creeps;

import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.gems.Gem;
import de.thegerman.circletd.objects.CircleObject;
import de.thegerman.circletd.objects.projectiles.Projectile;
import de.thegerman.circletd.objects.towers.Tower;

public abstract class Creep extends CircleObject {

	protected boolean alive = true;
	protected float xspeed = 0;
	protected float yspeed = 1;

	public Creep(float x, float y, float radius) {
		super(x, y, radius);
	}

	public abstract float getSpeed();
	public abstract Gem getDroppedGem();
	
	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		move(timespan);
		return ((x + radius) < 0 || (y + radius) < 0 || (y - radius) > gameProperties.getHeight() || x - radius > gameProperties.getWidth());
	}

	protected void move(long timespan) {
		x += xspeed * (timespan / 1000f) * getSpeed();
		y += yspeed * (timespan / 1000f) * getSpeed();
	}

	public boolean hitAction(Projectile projectile, GameProperties gameProperties) {
		return true;
	}
	
	public boolean hitAction(Tower tower, GameProperties gameProperties) {
		return true;
	}

	public void destroy() {
		alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}
	

}
