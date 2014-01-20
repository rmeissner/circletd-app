package de.thegerman.circletd.levels;

import de.thegerman.circletd.GameProperties;
import de.thegerman.circletd.objects.creeps.BasicCreep;
import de.thegerman.circletd.objects.creeps.TankCreep;

public class TestLevel extends Level {

	public static final long CREEP_DELAY = 7000; 
	
	private long lastCreep;
	private int creepType;
	
	public TestLevel(GameProperties gameProperties) {
		super(gameProperties);
	}
	
	@Override
	public void additionalUpdates(long timespan) {
		lastCreep += timespan;
		if (lastCreep > CREEP_DELAY) {
			lastCreep = 0;
			float startX = (float) ((Math.random() * (gameProperties.getWidth() - 200)) + 100);
			switch (creepType) {
			case 0:
				gameProperties.getCreeps().add(new BasicCreep(startX, 100, mainBase));
				break;
			default:
			case 1:
				gameProperties.getCreeps().add(new TankCreep(startX, 100, mainBase));
				break;
			}
			creepType = (creepType+1)%2;
		}
	}
}
