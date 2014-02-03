package de.thegerman.circletd.dialogs;

import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.R;
import de.thegerman.circletd.objects.towers.Tower;


public class TowerOptionsDestroyDialogItem extends TowerOptionsDialogItem {

	public TowerOptionsDestroyDialogItem(float width, float height, float left, float top) {
		super(GameApplication.getAppContext().getString(R.string.destroy), width, height, left, top);
	}

	@Override
	public void performAction(Tower tower, GameProperties gameProperties) {
		tower.destroy();
	}

}
