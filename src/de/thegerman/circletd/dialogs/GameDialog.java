package de.thegerman.circletd.dialogs;

import android.graphics.Canvas;
import de.thegerman.circletd.handler.UserMessageHandler;
import de.thegerman.circletd.handler.TouchEventHandler;

public abstract class GameDialog implements TouchEventHandler {

	private boolean dismissed = false;
	protected UserMessageHandler userMessageHandler;
	
	public GameDialog(UserMessageHandler userMessageHandler) {
		this.userMessageHandler = userMessageHandler;
	}

	final public boolean isDismissed() {
		return dismissed;
	}
	
	final public void dismiss() {
		userMessageHandler.closeDialog(this);
		dismissed = true;
	}
	
	public abstract void draw(Canvas canvas);

}
