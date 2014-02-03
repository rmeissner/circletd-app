package de.thegerman.circletd.levels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.dialogs.GameDialog;
import de.thegerman.circletd.handler.GemPickHandler;
import de.thegerman.circletd.handler.NewTowerHandler;
import de.thegerman.circletd.handler.TouchEventHandler;
import de.thegerman.circletd.handler.TowerOptionsHandler;
import de.thegerman.circletd.handler.UserMessageHandler;
import de.thegerman.circletd.notification.Notification;
import de.thegerman.circletd.objects.CircleObject;
import de.thegerman.circletd.objects.GraphicalObject;
import de.thegerman.circletd.objects.creeps.Creep;
import de.thegerman.circletd.objects.gems.Gem;
import de.thegerman.circletd.objects.projectiles.Projectile;
import de.thegerman.circletd.objects.towers.MainBase;
import de.thegerman.circletd.objects.towers.ProviderTower;
import de.thegerman.circletd.objects.towers.Tower;
import de.thegerman.circletd.ui.EnergyUIElement;
import de.thegerman.circletd.ui.GemsUIElement;
import de.thegerman.circletd.ui.LivesUIElement;
import de.thegerman.circletd.ui.UIElement;

public abstract class Level implements UserMessageHandler {
	
	public static float INTERFACE_PADDING = 20;
	
	private List<GameDialog> dialogs = new CopyOnWriteArrayList<GameDialog>();
	private List<Notification> notifications = new CopyOnWriteArrayList<Notification>();
	private List<TouchEventHandler> touchEventHandler = new CopyOnWriteArrayList<TouchEventHandler>();
	private Set<CircleObject> removeCreeps = new HashSet<CircleObject>();
	private Set<CircleObject> removeProjectiles = new HashSet<CircleObject>();
	private Set<CircleObject> removeTowers = new HashSet<CircleObject>();
	private Set<CircleObject> removeGems = new HashSet<CircleObject>();
	private List<UIElement> uiElements = new ArrayList<UIElement>();
	protected MainBase mainBase;
	protected GameProperties gameProperties;
	protected boolean initialized = false;
	protected NewTowerHandler newTowerHandler;
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
		updateNotification(timespan);
		
		if (gameProperties.isGamePaused()) return;
		
		removeCreeps.clear();
		removeProjectiles.clear();
		removeTowers.clear();
		removeGems.clear();
		
		updateCreeps(timespan);
		updateProjectiles(timespan);
		updateTower(timespan);
		updateGems(timespan);
		
		additionalUpdates(timespan);
		
		gameProperties.getCreeps().removeAll(removeCreeps);
		gameProperties.getProjectiles().removeAll(removeProjectiles);
		gameProperties.getTowers().removeAll(removeTowers);
		gameProperties.getGems().removeAll(removeGems);
	}
	public void additionalUpdates(long timespan) {
		// Can be overridden by subclass to perform level-dependent updates
	}
	
	private void updateGems(long timespan) {
		for(Gem gem : gameProperties.getGems()) {
			if(!gem.isAlive() || gem.update(timespan, gameProperties)){
				removeGem(gem);
			}
		}
	}

	private void updateCreeps(long timespan) {
		iterate_creeps: for(Creep creep : gameProperties.getCreeps()) {
			if(!creep.isAlive() || creep.update(timespan, gameProperties)){
				removeCreep(creep);
			} else {
				
				// Check for collision with projectiles
				for (Projectile projectile : gameProperties.getProjectiles()) {
					if(projectile.isAlive() && projectile.collides(creep)){
						if (projectile.hitAction(creep, gameProperties)) {
							removeProjectile(projectile);
						}
						if (creep.hitAction(projectile, gameProperties)) {
							removeCreep(creep);
							continue iterate_creeps;
						}
					}
				}
				
				// Check for collision with towers
				for (Tower tower : gameProperties.getTowers()) {
					if(tower.isAlive() && tower.collides(creep)){
						if (tower.hitAction(creep, gameProperties)) {
							removeTower(tower);
						}
						if (creep.hitAction(tower, gameProperties)) {
							removeCreep(creep);
							continue iterate_creeps;
						}
					}
				}
				
			}
		}
	}

	private void updateProjectiles(long timespan) {
		for(Projectile projectile : gameProperties.getProjectiles()) {
			if(!projectile.isAlive() || projectile.update(timespan, gameProperties)){
				removeProjectile(projectile);
			}
		}
	}

	private void updateTower(long timespan) {
		for(Tower tower : gameProperties.getTowers()) {
			if (!tower.isAlive() || tower.update(timespan, gameProperties)) {
				removeTower(tower);
			} 
		};
	}

	private void updateNotification(long timespan) {
		if (!notifications.isEmpty()) {
			Notification notification = notifications.get(notifications.size() - 1);
			if (notification.update(timespan)) {
				notifications.remove(notification);
			}
		}
	}

	
	protected void removeGem(Gem gem) {
		gem.destroy();
		removeGems.add(gem);
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

	final public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		drawConnections(canvas);
		drawTower(canvas);
		drawCreeps(canvas);
		drawGems(canvas);
		drawProjectiles(canvas);
		drawNewTower(canvas);
		drawInterface(canvas);
		
		additionalDrawing(canvas);
		
		drawDialog(canvas);
		drawNotification(canvas);
	}
	
	public void additionalDrawing(Canvas canvas) {
		// Can be overridden by subclass to perform level-dependent drawing
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

	private void drawDialog(Canvas canvas) {
		if (!dialogs.isEmpty()) {
			dialogs.get(dialogs.size() - 1).draw(canvas);
		}
	}

	private void drawNotification(Canvas canvas) {
		if (!notifications.isEmpty()) {
			notifications.get(notifications.size() - 1).draw(canvas);
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
		initializeTouchEventHandler();
		mainBase = new MainBase(gameProperties.getWidth()/2, gameProperties.getHeight() - 400);
		gameProperties.setLives(mainBase.getLives());
		gameProperties.getTowers().add(mainBase);
		initiliazeUI();
	}
	
	private void initializeTouchEventHandler() {
		touchEventHandler.clear();
		
		newTowerHandler = new NewTowerHandler(this);
		touchEventHandler.add(newTowerHandler);
		
		touchEventHandler.add(new GemPickHandler());
		touchEventHandler.add(new TowerOptionsHandler(this));
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
			returnValue = dialogs.get(dialogs.size() - 1).handleTouchEvent(action, currentX, currentY, gameProperties);
		}
		
		Iterator<TouchEventHandler> handlerIter = touchEventHandler.iterator();
		while(!returnValue && handlerIter.hasNext()) {
			TouchEventHandler handler = handlerIter.next();
			returnValue = handler.handleTouchEvent(action, currentX, currentY, gameProperties);
		}
		
		return true;
	}
	
	@Override
	public void openDialog(GameDialog dialog) {
		this.dialogs.add(dialog);		
	}
	
	@Override
	public void closeDialog(GameDialog dialog) {
		this.dialogs.remove(dialog);
	}
	
	@Override
	public void addNotification(Notification notification) {
		this.notifications.add(notification);
	}
	
	public MainBase getMainBase() {
		return mainBase;
	}
}
