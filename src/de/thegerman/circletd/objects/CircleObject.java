package de.thegerman.circletd.objects;


public abstract class CircleObject implements AnimatedObject {

	protected float x;
	protected float y;
	protected float radius;

	public CircleObject(float x, float y, float radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	@Override
	public boolean contains(float xCoord, float yCoord) {
		float xDist = xCoord - getX();
		float yDist = yCoord - getY();
		double distance = Math.sqrt(xDist * xDist + yDist * yDist);
		return(distance <= getRadius());
	}
	
	public boolean collides(CircleObject otherCircle){
		float xDist = otherCircle.getX() - getX();
		float yDist = otherCircle.getY() - getY();
		double distance = Math.sqrt(xDist * xDist + yDist * yDist);
		return(distance <= getRadius() + otherCircle.getRadius());
	}
	
	@Override
	public void setPosition(float newX, float newY) {
		x = newX;
		y = newY;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getRadius() {
		return radius;
	}

}