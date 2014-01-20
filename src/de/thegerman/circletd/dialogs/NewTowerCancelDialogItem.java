package de.thegerman.circletd.dialogs;

import android.graphics.Canvas;
import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.R;
import de.thegerman.circletd.objects.towers.Tower.TowerType;


public class NewTowerCancelDialogItem extends NewTowerDialogItem {

	public NewTowerCancelDialogItem(float width, float height, float left, float top) {
		super(null, width, height, left, top);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(left, top, left + width, top + height, borderPaint);
		float textXPos = left + (width/2);
		canvas.drawText(GameApplication.getAppContext().getString(R.string.cancel), textXPos, top + height - TEXT_PADDING - DIALOG_ITEM_PADDING, textPaint);
	}
	
	@Override
	public TowerType getTowerType() {
		return null;
	}

}
