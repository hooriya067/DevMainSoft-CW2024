package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;

	public LevelViewLevelTwo(Group root, int heartsToDisplay, double screenWidth, double screenHeight) {
		super(root, heartsToDisplay, screenWidth, screenHeight); // Pass screen dimensions to the parent class
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}

	public void showShield() {
		System.out.println("LevelViewLevelTwo: Showing Shield");
		shieldImage.showShield();
	}

	public void hideShield() {
		System.out.println("LevelViewLevelTwo: Hiding Shield");
		shieldImage.hideShield();
	}
	public void updateShieldPosition(double x, double y) {
		shieldImage.updatePosition(x, y);
	}



}
