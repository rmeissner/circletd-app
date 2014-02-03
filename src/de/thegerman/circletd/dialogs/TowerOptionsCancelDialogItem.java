package de.thegerman.circletd.dialogs;

import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.R;


public class TowerOptionsCancelDialogItem extends TowerOptionsDialogItem {

	public TowerOptionsCancelDialogItem(float width, float height, float left, float top) {
		super(GameApplication.getAppContext().getString(R.string.cancel), width, height, left, top);
	}

	@Override
	public void performAction(GameProperties gameProperties) {
		// no action to perform
	}
}
