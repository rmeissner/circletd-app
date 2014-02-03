package de.thegerman.circletd.upgrades;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.R;

public abstract class Upgrade<T> {
	private String basename;
	private int level;
	private List<T> levels = new ArrayList<T>();

	public Upgrade(String basename) {
		this.basename = basename;
	}

	protected void addUpgradeLevel(T value) {
		levels.add(value);
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
		return levels.get(level);
	}
	
	public T getNextValue() {
		return isAvailable() ? levels.get(level + 1) : getCurrentValue();
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

	public int getCurrentLevel() {
		return level;
	}
}
