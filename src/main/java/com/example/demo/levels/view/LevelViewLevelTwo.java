package com.example.demo.levels.view;

import com.example.demo.actors.collectibles.ShieldImage;
import com.example.demo.levels.LevelParent;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.util.Duration;



public class LevelViewLevelTwo extends LevelView {


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
	//	System.out.println("LevelViewLevelTwo: Showing Shield");
		shieldImage.showShield();

		if (!shieldMessageDisplayed) {
			showShieldActivatedMessage();
			shieldMessageDisplayed = true;  // Set flag to true after displaying the message
		}
	}

	public void hideShield() {
		//System.out.println("LevelViewLevelTwo: Hiding Shield");
		shieldImage.hideShield();
		shieldMessageDisplayed = false;  // Reset flag when shield is hidden
	}

	public void updateShieldPosition(double x, double y) {
		shieldImage.updatePosition(x, y);
	}

	// Method to show "Shield Activated!" message
	private void showShieldActivatedMessage() {
		Label shieldLabel = new Label("Shield Activated!");
		shieldLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: red;");
		// Position label at the center of the screen using screenWidth and screenHeight
		shieldLabel.setLayoutX(screenWidth / 2 - 150);
		shieldLabel.setLayoutY(screenHeight / 2 - 50);
		root.getChildren().add(shieldLabel);

		// Scale Transition to make the label grow larger and then return to normal size, with fading in and out
		ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(0.5), shieldLabel);
		scaleUp.setFromX(1.0);
		scaleUp.setFromY(1.0);
		scaleUp.setToX(2.0);
		scaleUp.setToY(2.0);

		ScaleTransition scaleDown = new ScaleTransition(Duration.seconds(0.5), shieldLabel);
		scaleDown.setFromX(2.0);
		scaleDown.setFromY(2.0);
		scaleDown.setToX(1.0);
		scaleDown.setToY(1.0);

		// Fade Transition to make the label fade in
		FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), shieldLabel);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);

		// Pause Transition to keep the message visible for 2 seconds
		PauseTransition pause = new PauseTransition(Duration.seconds(2));

		// Fade Transition to make the label disappear smoothly
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), shieldLabel);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);

		// Set actions after transitions complete
		fadeOut.setOnFinished(event -> root.getChildren().remove(shieldLabel));

		// Play animations in sequence: fade in -> scale up -> scale down -> pause -> fade out
		fadeIn.setOnFinished(event -> scaleUp.play());
		scaleUp.setOnFinished(event -> scaleDown.play());
		scaleDown.setOnFinished(event -> pause.play());
		pause.setOnFinished(event -> fadeOut.play());

		// Start the fading in effect
		fadeIn.play();
	}
}
