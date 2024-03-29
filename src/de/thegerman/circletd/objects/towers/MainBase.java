package de.thegerman.circletd.objects.towers;

import android.graphics.Color;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.creeps.Creep;

public class MainBase extends ProviderTower {
	
	private int lives;
	
	public MainBase(float x, float y) {
		super(x, y, 75, null);
		this.lives = 20;
		this.paint.setColor(Color.CYAN);
	}

	@Override
	int getMaxChildren() {
		return 6;
	}

	@Override
	public TowerType getType() {
		return TowerType.MainBase;
	}
	
	@Override
	public boolean hitAction(Creep creep, GameProperties gameProperties) {
		lives --;
		gameProperties.setLives(lives);
		return (lives <= 0);
	}
	
	public int getLives() {
		return lives;
	}

	@Override
	public int getEnergyLevel() {
		return 5;
	}

	@Override
	public int getCosts(GameProperties gameProperties) {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getUpgradeBaseCosts() {
		return 0;
	}

}
