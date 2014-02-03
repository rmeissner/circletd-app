package de.thegerman.circletd.objects.towers;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.CircleObject;
import de.thegerman.circletd.objects.creeps.Creep;
import de.thegerman.circletd.upgrades.Upgrade;

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
	protected boolean additionalDraw;
	protected ProviderTower providerTower;

	public Tower(float x, float y, float radius, ProviderTower providerTower) {
		super(x, y, radius);
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.alive = true;
		this.providerTower = providerTower;
	}

	@Override
	public final void draw(Canvas canvas) {
		baseDraw(canvas);
		if (additionalDraw) {
			additionalDraw(canvas);
		}
	}
	
	protected void baseDraw(Canvas canvas) {
		canvas.drawCircle(x, y, radius, paint);
	}

	protected void additionalDraw(Canvas canvas) {
	}

	@Override
	public abstract boolean update(long timespan, GameProperties gameProperties);
	public abstract TowerType getType();
	public abstract int getEnergyLevel();
	public abstract int getCosts(GameProperties gameProperties);
	
	public List<Upgrade<?>> getUpgrades() {
		return new ArrayList<Upgrade<?>>();
	}

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

	public void activateAdditionalDraw() {
		additionalDraw = true;
	}

	public void deactivateAdditionalDraw() {
		additionalDraw = false;
	}

	public boolean additionalDrawActivated() {
		return additionalDraw;
	}
	
}