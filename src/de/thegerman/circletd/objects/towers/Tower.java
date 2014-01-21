package de.thegerman.circletd.objects.towers;

import android.graphics.Canvas;
import android.graphics.Paint;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.CircleObject;
import de.thegerman.circletd.objects.creeps.Creep;

public abstract class Tower extends CircleObject {
	
	public enum TowerType {
		MainBase,
		BasicTower,
		ExtensionTower,
		ShooterTower, 
		EnergyTower
	}

	protected Paint paint;
	protected boolean alive;
	protected ProviderTower providerTower;

	public Tower(float x, float y, float radius, ProviderTower providerTower) {
		super(x, y, radius);
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.alive = true;
		this.providerTower = providerTower;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(x, y, radius, paint);
	}

	@Override
	public abstract boolean update(long timespan, GameProperties gameProperties);
	public abstract TowerType getType();
	public abstract int getEnergyLevel();
	public abstract int getCosts();

	public boolean isAlive() {
		return alive;
	}
	
	public void destroy() {
		if (alive && providerTower != null) {
			providerTower.removeChild(this);
		}
		alive = false;
	}

	public boolean hitAction(Creep creep, GameProperties gameProperties) {
		return true;
	}
	
}