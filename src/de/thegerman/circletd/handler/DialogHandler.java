package de.thegerman.circletd.handler;

import de.thegerman.circletd.dialogs.GameDialog;

public interface DialogHandler {
	public void openDialog(GameDialog dialog);

	public void closeDialog(GameDialog gameDialog);
}
