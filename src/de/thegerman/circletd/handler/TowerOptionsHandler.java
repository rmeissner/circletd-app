package de.thegerman.circletd.handler;

import android.view.MotionEvent;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.dialogs.TowerOptionsDialog;
import de.thegerman.circletd.objects.towers.MainBase;
import de.thegerman.circletd.objects.towers.Tower;

public class TowerOptionsHandler implements TouchEventHandler {
	
	private Tower selectedTower;
	private float eventStartX;
	private float eventStartY;
	private boolean eventMoved;
	private UserMessageHandler dialogHandler;
	
	public TowerOptionsHandler(UserMessageHandler dialogHandler) {
		this.dialogHandler = dialogHandler;
	}
	
	@Override
	public boolean handleTouchEvent(int action, float currentX, float currentY, GameProperties gameProperties) {
		switch (action) {
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_UP: {
			if (selectedTower != null && !eventMoved) {
				gameProperties.pauseGame();
				dialogHandler.openDialog(new TowerOptionsDialog(selectedTower, gameProperties, dialogHandler));
			}
			selectedTower = null;
			break;
		}
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN: {
			for (Tower tower : gameProperties.getTowers()) {
				if (tower.contains(currentX, currentY) && !(tower instanceof MainBase)) {
					eventStartX = currentX;
					eventStartY = currentY;
					eventMoved = false;
					selectedTower = tower;
				}
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if (selectedTower != null && !eventMoved) {
				float xDist = eventStartX - currentX;
				float yDist = eventStartY - currentY;
				eventMoved = Math.sqrt(xDist * xDist + yDist * yDist) > 50;
			}
			break;
		}
		}
		return false;
	}
}