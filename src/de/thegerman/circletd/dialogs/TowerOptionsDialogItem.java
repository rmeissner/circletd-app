package de.thegerman.circletd.dialogs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.GraphicalObject;
import de.thegerman.circletd.objects.towers.Tower;

public abstract class TowerOptionsDialogItem implements GraphicalObject{
	
	public static final int TOWER_GRAFIK_BOX_SIZE = 100; 
	public static final int DIALOG_ITEM_PADDING = 10;
	public static final int TEXT_PADDING = 30;
	public static final int TEXT_SIZE = 50;
	public static final float HEIGHT = TOWER_GRAFIK_BOX_SIZE + DIALOG_ITEM_PADDING * 2;
	public static final float TEXT_LEFT_MARGIN = DIALOG_ITEM_PADDING + TOWER_GRAFIK_BOX_SIZE;
	
	protected float width;
	protected float height;
	protected float left;
	protected float top;
	protected Paint borderPaint;
	protected Paint textPaint;
	protected String text;
	
	public TowerOptionsDialogItem(String text, float width, float height, float left, float top) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.text = text;
		this.borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.borderPaint.setColor(Color.WHITE);
		this.borderPaint.setStyle(Style.STROKE);
		this.textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.textPaint.setColor(Color.WHITE);
		this.textPaint.setStyle(Style.FILL_AND_STROKE);
		this.textPaint.setTextSize(TEXT_SIZE);
		this.textPaint.setTextAlign(Align.CENTER);
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(left, top, left + width, top + height, borderPaint);
		float textXPos = left + (width/2);
		canvas.drawText(text, textXPos, top + height - TEXT_PADDING - DIALOG_ITEM_PADDING, textPaint);
	}
	@Override
	public boolean contains(float x, float y) {
		if (x < left) return false;
		if (y < top) return false;
		if (x > left + width) return false;
		if (y > top + height) return false;
		return true;
	}
	
	@Override
	public void setPosition(float left, float top) {
		this.left = left;
		this.top = top;
	}
	
	public abstract void performAction(Tower tower, GameProperties gameProperties);
}
