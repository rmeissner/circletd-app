package de.thegerman.circletd.handler;

import android.view.MotionEvent;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.gems.Gem;

public class GemPickHandler implements TouchEventHandler {
	
	private Gem selectedGem;
	private float eventStartX;
	private float eventStartY;
	private boolean eventMoved;

	@Override
	public boolean handleTouchEvent(int action, float currentX, float currentY, GameProperties gameProperties) {
		switch (action) {
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_UP: {
			if (selectedGem != null && !eventMoved) {
				gameProperties.addGems(selectedGem.getValue());
				selectedGem.destroy();
			}
			selectedGem = null;
			break;
		}
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN: {
			for (Gem gem : gameProperties.getGems()) {
				if (gem.contains(currentX, currentY)) {
					eventStartX = currentX;
					eventStartY = currentY;
					eventMoved = false;
					selectedGem = gem;
				}
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if (selectedGem != null && !eventMoved) {
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
