package de.thegerman.circletd.dialogs;

import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.R;
import de.thegerman.circletd.objects.towers.Tower;


public class TowerOptionsDestroyDialogItem extends TowerOptionsDialogItem {

	private Tower tower;

	public TowerOptionsDestroyDialogItem(float width, float height, float left, float top, Tower tower) {
		super(GameApplication.getAppContext().getString(R.string.destroy), width, height, left, top);
		this.tower = tower;
	}

	@Override
	public void performAction(GameProperties gameProperties) {
		tower.destroy();
	}

}
