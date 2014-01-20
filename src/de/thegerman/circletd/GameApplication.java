package de.thegerman.circletd;

import de.thegerman.circletd.objects.towers.Tower.TowerType;
import android.app.Application;
import android.content.Context;

public class GameApplication  extends Application {
	
	private static GameApplication instance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
	
	public static Context getAppContext() {
		return instance.getApplicationContext();
	}

	public static String getTowerName(TowerType type) {
		int nameId;
		switch (type) {
		case BasicTower:
			nameId = R.string.basic_tower;
			break;
		case ExtensionTower:
			nameId = R.string.extension_tower;
			break;
		case MainBase:
			nameId = R.string.main_base;
			break;
		case EnergyTower:
			nameId = R.string.energy_tower;
			break;
		case ShooterTower:
			nameId = R.string.shooter_tower;
			break;
		default:
			nameId = R.string.basic_tower;
			break;
		}
		return instance.getApplicationContext().getString(nameId);
	}

}
