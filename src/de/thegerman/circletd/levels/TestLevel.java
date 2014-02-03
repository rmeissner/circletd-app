package de.thegerman.circletd.levels;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.TextPaint;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.creeps.AttackerCreep;
import de.thegerman.circletd.objects.creeps.BasicCreep;
import de.thegerman.circletd.objects.creeps.TankCreep;

public class TestLevel extends Level {

	public static final long CREEP_DELAY = 2000; 
	public static final long TITLE_TIME = 4000; 
	
	private long lastCreep;
	private long titleTime;
	private int wave;
	private boolean waiting;
	private int type;
	private int spawned;
	private Paint titlePaint;

	private String title;
	
	public TestLevel(GameProperties gameProperties) {
		super(gameProperties);
		this.titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.titlePaint.setColor(Color.WHITE);
		this.titlePaint.setTextSize(50);
		this.titlePaint.setTextAlign(Align.CENTER);
	}
	
	@Override
	public void additionalDrawing(Canvas canvas) {
		if (title != null) {
			canvas.drawText(title, gameProperties.getWidth() / 2, 100, titlePaint);
		}
	}
	
	@Override
	public void initialize() {
		super.initialize();
		gameProperties.addGems(200);
	}
	
	protected void showTitle(String title) {
		titleTime = 0;
		this.title = title;
	}
	
	@Override
	public void additionalUpdates(long timespan) {
		updateTitle(timespan);
		
		lastCreep += timespan;		
		if (!waiting && spawned >= 40) {
			waiting = true;
		} else if (waiting && gameProperties.getCreeps().size() == 0) {
			waiting = false;
			wave++;
			spawned = 0;
			type = 0;
		}
		if (!waiting && lastCreep > CREEP_DELAY) {
			lastCreep = 0;
			spawned ++;
			float startX = (float) ((Math.random() * (gameProperties.getWidth() - 200)) + 100);
			switch (wave) {
			case 2:
				switch (type) {
				default:
					gameProperties.getCreeps().add(new BasicCreep(startX, 100, mainBase));
				case 0:
					gameProperties.getCreeps().add(new AttackerCreep(startX, 100, mainBase));
				case 1:
					gameProperties.getCreeps().add(new TankCreep(startX, 100, mainBase));
				}
				type = (type + 1) % 5;
				break;
			case 1:
				switch (type) {
				default:
					gameProperties.getCreeps().add(new BasicCreep(startX, 100, mainBase));
				case 0:
					gameProperties.getCreeps().add(new TankCreep(startX, 100, mainBase));
				}
				type = (type + 1) % 4;
				break;
			default:
			case 0:
				gameProperties.getCreeps().add(new BasicCreep(startX, 100, mainBase));
				break;
			}
		}
	}

	protected void updateTitle(long timespan) {
		if (title != null && titleTime <= TITLE_TIME) {
			titleTime += timespan;
		} else if (title != null && titleTime > TITLE_TIME){
			titleTime = 0;
			title = null;
		}
	}
}
