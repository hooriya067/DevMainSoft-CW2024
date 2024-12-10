package com.example.demo.actors.user;

import com.example.demo.Managers.BulletSystemManager;
import com.example.demo.Managers.SoundManager;
import com.example.demo.actors.active.FighterPlane;
import com.example.demo.actors.active.ActiveActor;
import com.example.demo.actors.active.Factories.ProjectileFactory;

public class UserPlane extends FighterPlane {

	private final double yLowerBound;
	private final double toolbarHeight;
	private int verticalVelocity;
	private int horizontalVelocity;
	private int velocityMultiplier;
	private int horizontalVelocityMultiplier;

	public UserPlane(String imageName, int imageHeight, double initialX, double initialY, int initialHealth,
					 double yLowerBound, double toolbarHeight, int verticalVelocity, int horizontalVelocity) {
		super(imageName, imageHeight, initialX, initialY, initialHealth);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
		this.yLowerBound = yLowerBound;
		this.toolbarHeight = toolbarHeight;
		this.verticalVelocity = verticalVelocity;
		this.horizontalVelocity = horizontalVelocity;
		this.velocityMultiplier = 0;
		this.horizontalVelocityMultiplier = 0;
	}

	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			double initialTranslateX = getTranslateX();

			this.moveVertically(verticalVelocity * velocityMultiplier);
			double newYPosition = getLayoutY() + getTranslateY();
			if (newYPosition < toolbarHeight || newYPosition > yLowerBound) {
				this.setTranslateY(initialTranslateY);
			}

			this.moveHorizontally(horizontalVelocity * horizontalVelocityMultiplier);
			double newXPosition = getLayoutX() + getTranslateX();
			double screenWidth = getScene().getWidth();
			if (newXPosition < 0 || newXPosition > screenWidth - getFitWidth()) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	@Override
	public ActiveActor fireProjectile() {
		SoundManager.getInstance().playSoundEffect("/com/example/demo/sound/userfire.mp3");
		BulletSystemManager bulletManager = BulletSystemManager.getInstance();

		if (bulletManager.subtractBullets(1)) {
			BulletSystemManager.getInstance().incrementBulletsUsed();
			double currentXPosition = getLayoutX() + getTranslateX() + getFitWidth() + 120;
			double currentYPosition = getLayoutY() + getTranslateY() + 20;
			return ProjectileFactory.createProjectile("USER_PROJECTILE", currentXPosition, currentYPosition, (Object) null);
		} else {
			return null;
		}
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stopVerticalMovement() {
		velocityMultiplier = 0;
	}

	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	public void stopHorizontalMovement() {
		horizontalVelocityMultiplier = 0;
	}

	private boolean isMoving() {
		return velocityMultiplier != 0 || horizontalVelocityMultiplier != 0;
	}

	public void addHealth(int amount) {
		health += amount;
	}

	// Getter method for horizontal velocity
	public double getVerticalVelocity() {
		return verticalVelocity;
	}
}