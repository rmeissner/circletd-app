package de.thegerman.circletd.levels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.dialogs.GameDialog;
import de.thegerman.circletd.handler.DeleteTowerHandler;
import de.thegerman.circletd.handler.DialogHandler;
import de.thegerman.circletd.handler.NewTowerHandler;
import de.thegerman.circletd.objects.CircleObject;
import de.thegerman.circletd.objects.GraphicalObject;
import de.thegerman.circletd.objects.creeps.Creep;
import de.thegerman.circletd.objects.projectiles.Projectile;
import de.thegerman.circletd.objects.towers.MainBase;
import de.thegerman.circletd.objects.towers.ProviderTower;
import de.thegerman.circletd.objects.towers.Tower;
import de.thegerman.circletd.ui.EnergyUIElement;
import de.thegerman.circletd.ui.GemsUIElement;
import de.thegerman.circletd.ui.LivesUIElement;
import de.thegerman.circletd.ui.UIElement;

public abstract class Level implements DialogHandler {
	
	public static float INTERFACE_PADDING = 20;
	
	private List<GameDialog> dialogs = new CopyOnWriteArrayList<GameDialog>();
	private List<CircleObject> removeCreeps = new ArrayList<CircleObject>();
	private List<CircleObject> removeProjectiles = new ArrayList<CircleObject>();
	private List<CircleObject> removeTowers = new ArrayList<CircleObject>();
	private List<UIElement> uiElements = new ArrayList<UIElement>();
	protected MainBase mainBase;
	protected GameProperties gameProperties;
	protected boolean initialized = false;
	protected NewTowerHandler newTowerHandler;
	protected DeleteTowerHandler deleteTowerHandler;
	private Paint connectionPaint;
	
	public Level(GameProperties gameProperties) {
		this.gameProperties = gameProperties;
	}
	
	private void initiliazeUI() {
		uiElements.clear();
		uiElements.add(new LivesUIElement(INTERFACE_PADDING, INTERFACE_PADDING, gameProperties));
		uiElements.add(new GemsUIElement((gameProperties.getWidth() - UIElement.INTERFACE_ELEMENT_WIDTH) / 2, INTERFACE_PADDING, gameProperties));
		uiElements.add(new EnergyUIElement(gameProperties.getWidth() - UIElement.INTERFACE_ELEMENT_WIDTH - INTERFACE_PADDING, INTERFACE_PADDING, gameProperties));
	}

	final public void update(long timespan) {
		removeCreeps.clear();
		removeProjectiles.clear();
		removeTowers.clear();
		
		updateCreeps(timespan);
		updateProjectiles(timespan);
		updateTower(timespan);
		
		additionalUpdates(timespan);
		
		gameProperties.getCreeps().removeAll(removeCreeps);
		gameProperties.getProjectiles().removeAll(removeProjectiles);
		gameProperties.getTowers().removeAll(removeTowers);
	}
	
	public void additionalUpdates(long timespan) {
		// Can be overridden by subclass to perform level-dependent updates
	}

	private void updateCreeps(long timespan) {
		for(Creep creep : gameProperties.getCreeps()) {
			if(creep.update(timespan, gameProperties)){
				removeCreep(creep);
			}
		}
	}

	private void updateProjectiles(long timespan) {
		for(Projectile projectile : gameProperties.getProjectiles()) {
			if(projectile.update(timespan, gameProperties)){
				removeProjectile(projectile);
			} else {
				for(Creep creep : gameProperties.getCreeps()) {
					if(projectile.collides(creep)){
						if (projectile.hitAction(creep, gameProperties)) {
							removeProjectile(projectile);
						}
						if (creep.hitAction(projectile, gameProperties)) {
							removeCreep(creep);
						}
					}
				}
			}
		}
	}

	private void updateTower(long timespan) {
		for(Tower tower : gameProperties.getTowers()) {
			if (!tower.isAlive() || tower.update(timespan, gameProperties)) {
				removeTower(tower);
			} else {
				for(Creep creep : gameProperties.getCreeps()) {
					if(tower.collides(creep)){
						if (tower.hitAction(creep, gameProperties)) {
							removeTower(tower);
						}
						if (creep.hitAction(tower, gameProperties)) {
							removeCreep(creep);
						}
					}
				}
			}
		};
	}

	protected void removeCreep(Creep creep) {
		gameProperties.getGems().add(creep.getDroppedGem());
		creep.destroy();
		removeCreeps.add(creep);
	}

	protected void removeTower(Tower tower) {
		tower.destroy();
		removeTowers.add(tower);
		if (tower instanceof ProviderTower) {
			removeTowers.addAll(((ProviderTower)tower).getAllChildren());
		}
	}

	protected void removeProjectile(Projectile projectile) {
		projectile.destroy();
		removeProjectiles.add(projectile);
	}

	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		drawConnections(canvas);
		drawTower(canvas);
		drawCreeps(canvas);
		drawGems(canvas);
		drawProjectiles(canvas);
		drawNewTower(canvas);
		drawInterface(canvas);
		drawDialogs(canvas);
	}

	private void drawGems(Canvas canvas) {
		for(GraphicalObject gem : gameProperties.getGems()) {
			gem.draw(canvas);
		};
	}

	private void drawInterface(Canvas canvas) {
		for (UIElement uiElement : uiElements) {
			uiElement.draw(canvas);
		}
	}

	private void drawDialogs(Canvas canvas) {
		if (!dialogs.isEmpty()) {
			dialogs.get(dialogs.size() - 1).draw(canvas);
		}
	}

	private void drawProjectiles(Canvas canvas) {
		for(GraphicalObject projectile : gameProperties.getProjectiles()) {
			projectile.draw(canvas);
		};
	}

	private void drawCreeps(Canvas canvas) {
		for(GraphicalObject creep : gameProperties.getCreeps()) {
			creep.draw(canvas);
		};
	}

	private void drawConnections(Canvas canvas) {
		if(newTowerHandler.getNewTower() != null) {
			canvas.drawLine(newTowerHandler.getNewTower().getX(), newTowerHandler.getNewTower().getY(), newTowerHandler.getSelectedTower().getX(), newTowerHandler.getSelectedTower().getY(), connectionPaint);
		}
		for(GraphicalObject tower : gameProperties.getTowers()) {
			if (tower instanceof ProviderTower) {
				((ProviderTower) tower).drawConnections(canvas);
			}
		};
	}

	protected void drawNewTower(Canvas canvas) {
		if(newTowerHandler.getNewTower() != null) {
			newTowerHandler.getNewTower().draw(canvas);
		}
	}

	protected void drawTower(Canvas canvas) {
		for(GraphicalObject tower : gameProperties.getTowers()) {
			tower.draw(canvas);
		};
	}
	
	public void initialize() {
		gameProperties.initializeNewGame();
		initialized = true;
		connectionPaint = new Paint();
		connectionPaint.setColor(Color.WHITE);
		connectionPaint.setStrokeWidth(10);
		newTowerHandler = new NewTowerHandler(this);
		deleteTowerHandler = new DeleteTowerHandler();
		mainBase = new MainBase(gameProperties.getWidth()/2, gameProperties.getHeight() - 400);
		gameProperties.setLives(mainBase.getLives());
		gameProperties.getTowers().add(mainBase);
		initiliazeUI();
	}
	
	public boolean isInitialized() {
		return initialized;
	}

	public boolean handleTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float currentX = event.getX() / gameProperties.getRatio();
		float currentY = event.getY() / gameProperties.getRatio();
		boolean returnValue = false;
		if (!dialogs.isEmpty()) {
			returnValue |= dialogs.get(dialogs.size() - 1).handleTouchEvent(action, currentX, currentY, gameProperties);
		}
		if (!returnValue) {
  		returnValue |= newTowerHandler.handleTouchEvent(action, currentX, currentY, gameProperties);
  		returnValue |= deleteTowerHandler.handleTouchEvent(action, currentX, currentY, gameProperties);
		}
		return returnValue;
	}
	
	@Override
	public void openDialog(GameDialog dialog) {
		this.dialogs.add(dialog);		
	}
	
	@Override
	public void closeDialog(GameDialog dialog) {
		this.dialogs.remove(dialog);
	}
	
	public MainBase getMainBase() {
		return mainBase;
	}
}
