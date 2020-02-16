package com.thanico.ponglwjgl.processing;

import com.thanico.ponglwjgl.ui.PongUIConstants;
import com.thanico.ponglwjgl.ui.PongUIDrawer;

/**
 * Class representing a pong ball and its position
 * 
 * @author Nicolas
 *
 */
public class PongBall {

	private float currentX;
	private float currentY;

	/**
	 * Constructor
	 * 
	 * @param startX starting X position
	 * @param startY starting Y position
	 */
	public PongBall(float startX, float startY) {
		this.setCurrentX(startX);
		this.setCurrentY(startY);
	}

	/**
	 * Draw the rectangle using current position
	 */
	public void draw() {
		float x1 = this.getCurrentX();
		float x2 = x1 + PongUIConstants.PONG_BALL_SIZE;
		float y1 = this.getCurrentY();
		float y2 = y1 + PongUIConstants.PONG_BALL_SIZE;

		PongUIDrawer.drawRectangle(x1, x2, y1, y2, 1.0f, 1.0f, 1.0f);
	}

	public float getCurrentX() {
		return currentX;
	}

	private void setCurrentX(float currentX) {
		this.currentX = currentX;
	}

	public float getCurrentY() {
		return currentY;
	}

	private void setCurrentY(float currentY) {
		this.currentY = currentY;
	}
}
