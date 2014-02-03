package de.thegerman.circletd.objects.creeps;

import java.lang.ref.WeakReference;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.gems.Gem;
import de.thegerman.circletd.objects.gems.MagentaGem;
import de.thegerman.circletd.objects.projectiles.Projectile;
import de.thegerman.circletd.objects.towers.Tower;

public class AttackerCreep extends Creep {

	private WeakReference<Tower> targetReference;
	private WeakReference<Tower> attackerReference;
	private Paint paint;

	public AttackerCreep(float x, float y, Tower target) {
		super(x, y, 20, 4);
		this.targetReference = new WeakReference<Tower>(target);
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.paint.setColor(Color.MAGENTA);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(x, y, radius, paint);
	}
	
	@Override
	public boolean update(long timespan, GameProperties gameProperties) {
		if (targetReference != null) {
			Tower target = null;
			if (attackerReference != null) {
				target = attackerReference.get();
			}
			if (target == null || !target.isAlive()) {
				attackerReference = null;
				target = targetReference.get();
			}
  		if (target != null && target.isAlive()) {
    		float xd = target.getX() - x;
    		float yd = target.getY() - y;
    		float c = (float) Math.sqrt(xd * xd + yd * yd);
    		if(c == 0) c = 1;
    		xspeed = xd / c;
  			yspeed = yd / c;
  		}
		}
		return super.update(timespan, gameProperties);
	}
	
	@Override
	public boolean hitAction(Projectile projectile, GameProperties gameProperties) {
		if (attackerReference == null || attackerReference.get() == null) {
			Tower newTarget = projectile.getOrigin();
			if (newTarget != null) {
				attackerReference = new WeakReference<Tower>(newTarget);
			}
		}
		return super.hitAction(projectile, gameProperties);
	}

	@Override
	public float getSpeed() {
		return 100;
	}

	@Override
	public Gem getDroppedGem() {
		return new MagentaGem(getX(), getY());
	}

}
