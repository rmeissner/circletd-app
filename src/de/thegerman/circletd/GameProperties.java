package de.thegerman.circletd;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.thegerman.circletd.objects.creeps.Creep;
import de.thegerman.circletd.objects.gems.Gem;
import de.thegerman.circletd.objects.projectiles.Projectile;
import de.thegerman.circletd.objects.towers.Tower;

public class GameProperties {
	
	protected List<Tower> towers = new CopyOnWriteArrayList<Tower>();
	protected List<Creep> creeps = new CopyOnWriteArrayList<Creep>();
	protected List<Projectile> projectiles = new CopyOnWriteArrayList<Projectile>();
	protected List<Gem> gems = new CopyOnWriteArrayList<Gem>();
	float ratio = 1;
	int width = 0;
	int height = 0;
	int lives = 0;
	int gemCount = 0;
	boolean gamePaused = false;
	
	public float getRatio() {
		return ratio;
	}
	
	public void setRatio(float ratio) {
		if (ratio != 0) {
			this.ratio = ratio;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void pauseGame(){
		gamePaused = true;
	}
	
	public void unpauseGame() {
		gamePaused = false;
	}
	
	public boolean isGamePaused() {
		return gamePaused;
	}
	
	public List<Tower> getTowers() {
		return towers;
	}
	
	public void clearTowers() {
		towers.clear();
	}
	
	public List<Creep> getCreeps() {
		return creeps;
	}
	
	public void clearCreeps() {
		creeps.clear();
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public void clearProjectiles() {
		projectiles.clear();
	}
	
	public List<Gem> getGems() {
		return gems;
	}
	
	public void clearGems() {
		gems.clear();
	}

	public void initializeNewGame() {
		clearCreeps();
		clearProjectiles();
		clearTowers();
		clearGems();
		setLives(0);
		resetGemCount();
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public int getGemCount() {
		return gemCount;
	}
	
	public void addGems(int gems) {
		gemCount += gems;
	}
	
	public void resetGemCount() {
		gemCount = 0;
	}
	
	public int getEnergy() {
		int energy = 0;
		for (Tower tower : towers) {
			energy += tower.getEnergyLevel();
		}
		return energy;
	}

	public boolean removeGems(int costs) {
		if (gemCount - costs <  0) return false;
		gemCount -= costs;
		return true;
	}
}
