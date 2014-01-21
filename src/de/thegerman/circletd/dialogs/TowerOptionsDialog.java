package de.thegerman.circletd.dialogs;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.handler.UserMessageHandler;
import de.thegerman.circletd.objects.towers.Tower;

public class TowerOptionsDialog extends GameDialog {
	
	private static final int DIALOG_PADDING = 100;
	
	private Tower selectedTower;
	private GameProperties gameProperties;
	private Paint paint;
	private List<TowerOptionsDialogItem> items = new ArrayList<TowerOptionsDialogItem>();

	public TowerOptionsDialog(Tower selectedTower, GameProperties gameProperties, UserMessageHandler dialogHandler) {
		super(dialogHandler);
		this.selectedTower = selectedTower;
		this.gameProperties = gameProperties;
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.DKGRAY);
		this.paint.setAlpha(200);
		
		float itemGroupHeight = 2 * (NewTowerDialogItem.HEIGHT + DIALOG_PADDING/2) - DIALOG_PADDING/2;
		
		float itemTopPos = (gameProperties.getHeight() - itemGroupHeight) / 2;
		items.add(new TowerOptionsDestroyDialogItem(gameProperties.getWidth() - DIALOG_PADDING*2, 
				NewTowerDialogItem.HEIGHT, DIALOG_PADDING, 
				itemTopPos));
		
		itemTopPos += NewTowerDialogItem.HEIGHT + DIALOG_PADDING/2;
		items.add(new TowerOptionsCancelDialogItem(gameProperties.getWidth() - DIALOG_PADDING*2, 
				NewTowerDialogItem.HEIGHT, DIALOG_PADDING, 
				itemTopPos));
	}

	protected void continueGame() {
		gameProperties.unpauseGame();
		dismiss();
	}

	@Override
	public boolean handleTouchEvent(int action, float currentX, float currentY, GameProperties gameProperties) {
		if (isDismissed()) return false;
		if (action != MotionEvent.ACTION_UP) return true;

		for (TowerOptionsDialogItem item : items) {
			if (item.contains(currentX, currentY)) {
				item.performAction(selectedTower);
				continueGame();
				break;
			}
		};
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(0, 0, gameProperties.getWidth(), gameProperties.getHeight(), paint);
		for (TowerOptionsDialogItem item : items) {
			item.draw(canvas);
		}
	}

}
