package de.thegerman.circletd.handler;

import android.view.MotionEvent;
import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.R;
import de.thegerman.circletd.dialogs.NewTowerDialog;
import de.thegerman.circletd.notification.Notification;
import de.thegerman.circletd.objects.towers.BasicTower;
import de.thegerman.circletd.objects.towers.ProviderTower;
import de.thegerman.circletd.objects.towers.Tower;

public class NewTowerHandler implements TouchEventHandler {
	
	private ProviderTower selectedTower;
	private Tower tempTower;
	private float eventStartX;
	private float eventStartY;
	private boolean eventMoved;
	private UserMessageHandler userMessageHandler;
	
	public NewTowerHandler(UserMessageHandler userMessageHandler) {
		this.userMessageHandler = userMessageHandler;
	}
	
	public Tower getNewTower() {
		return tempTower;
	}
	
	public ProviderTower getSelectedTower() {
		return selectedTower;
	}
	
	@Override
	public boolean handleTouchEvent(int action, float currentX, float currentY, GameProperties gameProperties) {
		switch (action) {
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_UP: {
			if (tempTower != null && selectedTower != null && selectedTower.isAlive()) {
				boolean validPosition = true;
				for (Tower tower : gameProperties.getTowers()) {
					if (tempTower.collides(tower)) validPosition = false;
				}
				if (validPosition) {
					gameProperties.pauseGame();
					userMessageHandler.openDialog(new NewTowerDialog(tempTower, selectedTower, gameProperties, userMessageHandler));
				} else {
					userMessageHandler.addNotification(new Notification(GameApplication.getAppContext().getString(R.string.no_space), gameProperties));
				}
				tempTower = null;
			}
			selectedTower = null;
			break;
		}
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN: {
			for (Tower tower : gameProperties.getTowers()) {
				if (tower.contains(currentX, currentY) && tower instanceof ProviderTower) {
					eventStartX = currentX;
					eventStartY = currentY;
					eventMoved = false;
					selectedTower = (ProviderTower) tower;
					tempTower = null;
				}
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if (selectedTower != null && selectedTower.isAlive()) {
				if (!eventMoved) {
					float xDist = eventStartX - currentX;
					float yDist = eventStartY - currentY;
					eventMoved = Math.sqrt(xDist * xDist + yDist * yDist) > 50;
				} else if (selectedTower.hasSpaceForChildren()) {
					float xDist = selectedTower.getX() - currentX;
					float yDist = selectedTower.getY() - currentY;
					float dist = (float) Math.sqrt(xDist * xDist + yDist * yDist);
					if (dist > ProviderTower.MAX_TOWER_DIST) {
						float ratio = ProviderTower.MAX_TOWER_DIST / dist;
						xDist *= ratio;
						yDist *= ratio;
					}
					if (tempTower == null) {
						tempTower = new BasicTower(selectedTower.getX() - xDist, selectedTower.getY() - yDist, null);
					} else {
						tempTower.setPosition(selectedTower.getX() - xDist, selectedTower.getY() - yDist);
					}
				} else {
					userMessageHandler.addNotification(new Notification(GameApplication.getAppContext().getString(R.string.no_children_space), gameProperties));
					selectedTower = null;
				}
			}
			break;
		}
		}
		return false;
	}
}