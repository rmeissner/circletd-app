package de.thegerman.circletd.dialogs;

import android.graphics.Canvas;
import de.thegerman.circletd.handler.DialogHandler;
import de.thegerman.circletd.handler.TouchEventHandler;

public abstract class GameDialog implements TouchEventHandler {

	private boolean dismissed = false;
	private DialogHandler dialogHandler;
	
	public GameDialog(DialogHandler dialogHandler) {
		this.dialogHandler = dialogHandler;
	}

	final public boolean isDismissed() {
		return dismissed;
	}
	
	final public void dismiss() {
		dialogHandler.closeDialog(this);
		dismissed = true;
	}
	
	public abstract void draw(Canvas canvas);

}
