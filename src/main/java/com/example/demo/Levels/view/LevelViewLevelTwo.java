package com.example.demo.Levels.view;

import com.example.demo.Managers.AlertManager;
import com.example.demo.actors.collectibles.ShieldImage;
import com.example.demo.Levels.LevelParent;
import javafx.scene.Group;


public class LevelViewLevelTwo extends LevelViewParent {


	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;
	private final double screenWidth;
	private final double screenHeight;
	private boolean shieldMessageDisplayed = false;  // Flag to track if shield message has been displayed


	public LevelViewLevelTwo(Group root, int heartsToDisplay, double screenWidth, double screenHeight, LevelParent levelParent) {
		super(root, heartsToDisplay, screenWidth, screenHeight, levelParent);
		this.root = root;
		this.screenWidth = getLevelParent().getScreenWidth(); // Assign after superclass call
		this.screenHeight = screenHeight;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);

		addImagesToRoot();
	}

	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}

	@Override
    protected void initializeWinningParameter() {
	}
	@Override
	public void updateWinningParameter() {}

	public void showShield() {
		shieldImage.showShield();

		if (!shieldMessageDisplayed) {
			AlertManager.getInstance().showInfoAlert(
					"Shield Activated!",
					screenWidth,
					screenHeight
			);
			shieldMessageDisplayed = true;  // Set flag to true after displaying the message
		}
	}

	public void hideShield() {
		shieldImage.hideShield();
		shieldMessageDisplayed = false;  // Reset flag when shield is hidden
	}

	public void updateShieldPosition(double x, double y) {
		shieldImage.updatePosition(x, y);
	}

}
