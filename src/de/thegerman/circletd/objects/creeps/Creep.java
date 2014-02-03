package de.thegerman.circletd.objects.creeps;

import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.CircleObject;
import de.thegerman.circletd.objects.gems.Gem;
import de.thegerman.circletd.objects.projectiles.Projectile;
import de.thegerman.circletd.objects.towers.Tower;

public abstract class Creep extends CircleObject {

	protected boolean alive = true;
	protected float xspeed = 0;
	protected float yspeed = 1;
	private int lives;

	public Creep(float x, float y, float radius, int lives) {
		super(x, y, radius);
		this.lives = lives;
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
		return looseLives(projectile.getDamage());
	}

	public boolean hitAction(Tower tower, GameProperties gameProperties) {
		return looseLives(1);
	}

	public void destroy() {
		alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}

	protected boolean looseLives(int damage) {
		lives -= damage;
		return lives <= 0;
	}
	

}
