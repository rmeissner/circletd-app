package de.thegerman.circletd.levels;

import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.creeps.AttackerCreep;
import de.thegerman.circletd.objects.creeps.BasicCreep;
import de.thegerman.circletd.objects.creeps.TankCreep;

public class TestLevel extends Level {

	public static final long CREEP_DELAY = 2000; 
	
	private long lastCreep;
	private int wave;

	private boolean waiting;

	private int type;

	private int spawned;
	
	public TestLevel(GameProperties gameProperties) {
		super(gameProperties);
	}
	
	@Override
	public void initialize() {
		super.initialize();
		gameProperties.addGems(200);
	}
	
	@Override
	public void additionalUpdates(long timespan) {
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
}
