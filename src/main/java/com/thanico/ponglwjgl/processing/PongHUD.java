package com.thanico.ponglwjgl.processing;

import com.thanico.ponglwjgl.ui.PongUIDrawer;

/**
 * Class used for the HUD (score)
 * 
 * @author Nicolas
 *
 */
public class PongHUD {

	// the scores
	private int scoreLeft;
	private int scoreRight;

	/**
	 * Constuctor
	 */
	public PongHUD() {
		this.resetMatch();
	}

	/**
	 * Add a point to one side
	 * 
	 * @param isLeftSide true if left paddle, false if right paddle
	 */
	public void addPoint(boolean isLeftSide) {
		if (isLeftSide) {
			setScoreLeft(getScoreLeft() + 1);
		} else {
			setScoreRight(getScoreRight() + 1);
		}
		System.out.println("LEFT : " + getScoreLeft() + " | RIGHT : " + getScoreRight());
	}

	/**
	 * Reset the scores
	 */
	public void resetMatch() {
		setScoreLeft(0);
		setScoreRight(0);
	}

	/**
	 * Draw the HUD
	 */
	public void draw() {
		PongUIDrawer.drawRectangle(-1.0f, 1.0f, 1.0f, 0.85f, 0.5f, 0.5f, 0.5f);
	}

	public int getScoreLeft() {
		return scoreLeft;
	}

	private void setScoreLeft(int scoreLeft) {
		this.scoreLeft = scoreLeft;
	}

	public int getScoreRight() {
		return scoreRight;
	}

	private void setScoreRight(int scoreRight) {
		this.scoreRight = scoreRight;
	}
}
