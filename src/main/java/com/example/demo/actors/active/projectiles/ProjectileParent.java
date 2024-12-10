package com.example.demo.actors.active.projectiles;


import com.example.demo.actors.active.ActiveActor;
import com.example.demo.core.GameStateManager;

public class ProjectileParent extends ActiveActor {
	private final double horizontalVelocity;  // Change from int to double

	public ProjectileParent(String imageName, int imageHeight, double initialXPos, double initialYPos, double horizontalVelocity) {  // Change the parameter type to double
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.horizontalVelocity = horizontalVelocity;
	}


	@Override
	public void takeDamage() {
		this.destroy();
	}

	public void updatePosition() {
		if (GameStateManager.getInstance().isGamePaused()) {
			return;
		}
		moveHorizontally(horizontalVelocity);
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
