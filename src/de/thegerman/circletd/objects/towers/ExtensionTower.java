package de.thegerman.circletd.objects.towers;

import de.thegerman.circletd.GameProperties;
import android.graphics.Color;

public class ExtensionTower extends ProviderTower {

	public ExtensionTower(float x, float y, ProviderTower providerTower) {
		super(x, y, 45, providerTower);
		this.paint.setColor(Color.RED);
	}

	@Override
	int getMaxChildren() {
		return 3;
	}

	@Override
	public TowerType getType() {
		return TowerType.ExtensionTower;
	}

	@Override
	public int getEnergyLevel() {
		return 0;
	}

	@Override
	public int getCosts(GameProperties gameProperties) {
		return 50;
	}

}
