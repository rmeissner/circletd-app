package de.thegerman.circletd.objects.projectiles;

import java.lang.ref.WeakReference;

import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.CircleObject;
import de.thegerman.circletd.objects.creeps.Creep;
import de.thegerman.circletd.objects.towers.Tower;

public abstract class Projectile extends CircleObject {
	
	private WeakReference<Tower> towerReference;
	private boolean alive = true;
	protected float xspeed = 0;
	protected float yspeed = -1;

	public Projectile(float x, float y, float radius, Tower origin) {
		super(x, y, radius);
		this.towerReference = new WeakReference<Tower>(origin);
	}
	
	public abstract float getSpeed();
	
	public Tower getOrigin() {
		return towerReference.get();
	}
	
	public int getDamage() {
		return 1;
	}

	public boolean hitAction(Creep creep, GameProperties gameProperties) {
		return true;
	}
	
	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		move(timespan);
		return ((x + radius) < 0 || (y + radius) < 0 || (y - radius) > gameProperties.getHeight() || x - radius > gameProperties.getWidth());
	}
	
	protected void move(long timespan){
		x += xspeed * (timespan / 1000f) * getSpeed();
		y += yspeed * (timespan / 1000f) * getSpeed();
	}

	public void destroy() {
		alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}

}
