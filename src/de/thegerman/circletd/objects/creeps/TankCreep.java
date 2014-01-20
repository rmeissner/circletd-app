package de.thegerman.circletd.objects.creeps;

import java.lang.ref.WeakReference;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.gems.Gem;
import de.thegerman.circletd.gems.GreenGem;
import de.thegerman.circletd.objects.projectiles.Projectile;
import de.thegerman.circletd.objects.towers.Tower;

public class TankCreep extends Creep {

	private WeakReference<Tower> targetReference;
	private int lives;
	private Paint paint;

	public TankCreep(float x, float y, Tower target) {
		super(x, y, 20);
		this.targetReference = new WeakReference<Tower>(target);
		this.lives = 5;
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.CYAN);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(x, y, radius, paint);
	}
	
	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		Tower target = targetReference.get();
		if (target != null && target.isAlive()) {
  		float xd = target.getX() - x;
  		float yd = target.getY() - y;
  		float c = (float) Math.sqrt(xd * xd + yd * yd);
  		if(c == 0) c = 1;
  		xspeed = xd / c;
			yspeed = yd / c;
		}
		return super.update(timespan, gameProperties);
	}
	
	private boolean looseLive() {
		lives--;
		return lives <= 0;
	}
	
	@Override
	public boolean hitAction(Projectile projectile, GameProperties gameProperties) {
		return looseLive();
	}

	@Override
	public boolean hitAction(Tower tower, GameProperties gameProperties) {
		return looseLive();
	}
	
	@Override
	public void destroy() {
		targetReference = null;
		super.destroy();
	}

	@Override
	public float getSpeed() {
		return 100;
	}

	@Override
	public Gem getDroppedGem() {
		return new GreenGem(getX(), getY());
	}

}