package de.thegerman.circletd.objects.towers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.thegerman.circletd.GameProperties;

public abstract class ProviderTower extends Tower {
	
	public static float MAX_TOWER_DIST = 200;
	public static float MAX_CONNECTION_WIDTH = 18;
	public static float MIN_CONNECTION_WIDTH = 8;
	public static float CONNECTION_WIDTH_CHANGE_RATE = 5;
	
	private List<Tower> children = new CopyOnWriteArrayList<Tower>();

	private Paint connectionPaint;
	private float connectionWidth = MIN_CONNECTION_WIDTH;
	private int connectionChangeDirection = 1;

	public ProviderTower(float x, float y, float radius, ProviderTower providerTower) {
		super(x, y, radius, providerTower);
		connectionPaint = new Paint();
		connectionPaint.setColor(Color.RED);
		connectionPaint.setStrokeWidth(connectionWidth);
	}
	
	abstract int getMaxChildren();
	
	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		updateConnections(timespan);
		return false;
	}
	
	private void updateConnections(long timespan) {
		if ((connectionWidth < MIN_CONNECTION_WIDTH && connectionChangeDirection < 0) || (connectionWidth > MAX_CONNECTION_WIDTH && connectionChangeDirection > 0)) {
			connectionChangeDirection *= -1;
		}
		connectionWidth += connectionChangeDirection * CONNECTION_WIDTH_CHANGE_RATE * (timespan / 1000f); 
		connectionPaint.setStrokeWidth(connectionWidth);
	}

	public void drawConnections(Canvas canvas) {
		for (Tower child : children) {
			canvas.drawLine(getX(), getY(), child.getX(), child.getY(), connectionPaint);
		}
	}
	
	public int getChildrenCount() {
		return children.size();
	}
	
	public void addChild(Tower child){
		children.add(child);
	}
	
	public void removeChild(Tower child) {
		children.remove(child);
	}

	public boolean hasSpaceForChildren() {
		return getChildrenCount() < getMaxChildren();
	}

	public Collection<? extends Tower> getAllChildren() {
		List<Tower> allChildren = new ArrayList<Tower>();
		for (Tower child : children) {
			allChildren.add(child);
			if (child instanceof ProviderTower) {
				allChildren.addAll(((ProviderTower)child).getAllChildren());
			}
		}
		return allChildren;
	}

}
