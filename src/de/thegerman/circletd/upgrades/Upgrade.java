package de.thegerman.circletd.upgrades;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.R;

public abstract class Upgrade<T> {
	private String basename;
	private int level;
	private List<UpgradeLevel<T>> levels = new ArrayList<UpgradeLevel<T>>();

	public Upgrade(String basename) {
		this.basename = basename;
	}

	protected void addUpgradeLevel(UpgradeLevel<T> upgradeLevel) {
		levels.add(upgradeLevel);
	}

	public String getNameWithLevel() {
		Context appContext = GameApplication.getAppContext();
		StringBuilder sb = new StringBuilder();
		sb.append(basename);
		if(isAvailable()) {
			sb.append(" ").append(appContext.getString(R.string.level)).append(" ").append(level + 1);
		}
		return sb.toString();
	}

	public T getCurrentValue() {
		return levels.get(level).getValue();
	}
	
	public T getNextValue() {
		return isAvailable() ? levels.get(level + 1).getValue() : getCurrentValue();
	}

	public int getUpgradePrize() {
		return isAvailable() ? levels.get(level + 1).getPrice() : -1;
	}

	public void increaseUpgradeLevel() {
		if (isAvailable()) {
			level++;
		}
	}

	public boolean isAvailable() {
		return (level + 1 < levels.size());
	}

	public abstract String getDescription();
}
