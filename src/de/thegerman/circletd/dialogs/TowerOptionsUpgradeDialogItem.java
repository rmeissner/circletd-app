package de.thegerman.circletd.dialogs;

import android.graphics.Canvas;
import android.graphics.Color;
import de.thegerman.circletd.GameApplication;
import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.R;
import de.thegerman.circletd.handler.UserMessageHandler;
import de.thegerman.circletd.notification.Notification;
import de.thegerman.circletd.objects.towers.Tower;
import de.thegerman.circletd.upgrades.Upgrade;


public class TowerOptionsUpgradeDialogItem extends TowerOptionsDialogItem {
	
	public static final int SMALL_TEXT_SIZE = 40;
	public static final int DETAILS_TEXT_SIZE = 30;
	public static final int TEXT_PADDING = 10;

	private Upgrade<?> upgrade;
	private UserMessageHandler userMessageHandler;

	public TowerOptionsUpgradeDialogItem(float width, float height, float left, float top, Upgrade<?> upgrade, UserMessageHandler userMessageHandler) {
		super(upgrade.getNameWithLevel(), width, height, left, top);
		this.upgrade = upgrade;
		this.userMessageHandler = userMessageHandler;
	}
	
	@Override
	public void draw(Canvas canvas) {
		if (upgrade.isAvailable()) {
			borderPaint.setColor(Color.WHITE);
			textPaint.setColor(Color.WHITE);

			canvas.drawRect(left, top, left + width, top + height, borderPaint);
			float textXPos = left + (width/2);
			textPaint.setTextSize(SMALL_TEXT_SIZE);
			canvas.drawText(text, textXPos, top + TEXT_SIZE + DIALOG_ITEM_PADDING, textPaint);

			textPaint.setTextSize(DETAILS_TEXT_SIZE);
			canvas.drawText(GameApplication.getAppContext().getString(R.string.upgrade_for_gems, upgrade.getDescription(), upgrade.getUpgradePrize()), textXPos, top + height - TEXT_PADDING - DIALOG_ITEM_PADDING, textPaint);
		} else {
			borderPaint.setColor(Color.GRAY);
			textPaint.setColor(Color.GRAY);
			super.draw(canvas);
		}
	}
	
	@Override
	public boolean contains(float x, float y) {
		if (!upgrade.isAvailable()) return false;
		return super.contains(x, y);
	}

	@Override
	public void performAction(Tower tower, GameProperties gameProperties) {
		if (!upgrade.isAvailable()) {
			userMessageHandler.addNotification(new Notification(GameApplication.getAppContext().getString(R.string.max_upgrade_level), gameProperties));
		}else if (!gameProperties.removeGems(upgrade.getUpgradePrize())) {
			userMessageHandler.addNotification(new Notification(GameApplication.getAppContext().getString(R.string.no_gems), gameProperties));
		} else {
			upgrade.increaseUpgradeLevel();
		}
	}

}
