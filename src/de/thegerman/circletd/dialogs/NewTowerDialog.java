package de.thegerman.circletd.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.handler.DialogHandler;
import de.thegerman.circletd.objects.towers.EnergyTower;
import de.thegerman.circletd.objects.towers.ExtensionTower;
import de.thegerman.circletd.objects.towers.ProviderTower;
import de.thegerman.circletd.objects.towers.ShooterTower;
import de.thegerman.circletd.objects.towers.Tower;
import de.thegerman.circletd.objects.towers.Tower.TowerType;

public class NewTowerDialog extends GameDialog {
	
	private static final int DIALOG_PADDING = 100;
	
	private Tower tempTower;
	private ProviderTower selectedTower;
	private GameProperties gameProperties;
	private Paint paint;
	private List<NewTowerDialogItem> items = new ArrayList<NewTowerDialogItem>();

	public NewTowerDialog(Tower tempTower, ProviderTower selectedTower, GameProperties gameProperties, DialogHandler dialogHandler) {
		super(dialogHandler);
		this.tempTower = tempTower;
		this.selectedTower = selectedTower;
		this.gameProperties = gameProperties;
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.DKGRAY);
		this.paint.setAlpha(200);
		
		List<Tower> buildableTowers = getBuildableTowers();
		
		float itemGroupHeight = (buildableTowers.size() + 1) * (NewTowerDialogItem.HEIGHT + DIALOG_PADDING/2) - DIALOG_PADDING/2;
		
		float itemTopPos = (gameProperties.getHeight() - itemGroupHeight) / 2;
		for (Tower tower : buildableTowers) {
  		items.add(new NewTowerDialogItem(tower, 
  				gameProperties.getWidth() - DIALOG_PADDING*2, 
  				NewTowerDialogItem.HEIGHT, DIALOG_PADDING, 
  				itemTopPos));
  		
  		itemTopPos += NewTowerDialogItem.HEIGHT + DIALOG_PADDING/2;
		}
		
		items.add(new NewTowerCancelDialogItem(gameProperties.getWidth() - DIALOG_PADDING*2, 
				NewTowerDialogItem.HEIGHT, DIALOG_PADDING, 
				itemTopPos));
	}
	
	private List<Tower> getBuildableTowers() {
		return Arrays.asList(new ExtensionTower(0, 0, null), new ShooterTower(0, 0, null), new EnergyTower(0, 0, null));
	}

	private void createTower(TowerType towerType) {
		Tower newTower = null;
		switch (towerType) {
		case ExtensionTower:
			newTower = new ExtensionTower(tempTower.getX(), tempTower.getY(), selectedTower);
			break;
		case ShooterTower:
  		newTower = new ShooterTower(tempTower.getX(), tempTower.getY(), selectedTower);
  		break;
  	case EnergyTower:
  		newTower = new EnergyTower(tempTower.getX(), tempTower.getY(), selectedTower);
  		break;
  	case BasicTower:
  	case MainBase:
  		// Cannot be build
  		break;
		}
		
		// TODO: add notification
		if (newTower != null && (gameProperties.getEnergy() + newTower.getEnergyLevel()) >= 0) {
  		gameProperties.getTowers().add(newTower);
  		selectedTower.addChild(newTower);
		}
	}

	protected void continueGame() {
		gameProperties.unpauseGame();
		dismiss();
	}

	@Override
	public boolean handleTouchEvent(int action, float currentX, float currentY, GameProperties gameProperties) {
		if (isDismissed()) return false;
		for (NewTowerDialogItem item : items) {
			if (item.contains(currentX, currentY)) {
				if (item.getTowerType() != null) {
					createTower(item.getTowerType());
				}
				continueGame();
				break;
			}
		};
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		tempTower.draw(canvas);
		canvas.drawRect(0, 0, gameProperties.getWidth(), gameProperties.getHeight(), paint);
		for (NewTowerDialogItem item : items) {
			item.draw(canvas);
		}
	}

}
