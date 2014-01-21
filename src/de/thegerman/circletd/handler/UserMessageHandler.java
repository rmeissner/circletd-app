package de.thegerman.circletd.handler;

import de.thegerman.circletd.dialogs.GameDialog;
import de.thegerman.circletd.notification.Notification;

public interface UserMessageHandler {
	public void openDialog(GameDialog dialog);

	public void closeDialog(GameDialog gameDialog);
	
	public void addNotification(Notification notification);
}
