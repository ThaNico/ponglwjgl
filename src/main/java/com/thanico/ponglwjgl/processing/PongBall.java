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

	// Current position
	private float currentX;
	private float currentY;

	// Expected position
	private float directionX;
	private float directionY;

	/**
	 * Ball speed
	 */
	private static final float BALL_SPEED_X = 0.008f;
	private static final float BALL_SPEED_Y = 0.002f;

	/**
	 * Maximum Y-AXIS (top)
	 */
	public static final float maxTopPositionY = 0.85f - PongUIConstants.PONG_BALL_SIZE;

	/**
	 * Maximum Y-AXIS (bottom)
	 */
	public static final float maxBottomPositionY = -1.0f;

	/**
	 * Maximum X-AXIS (left)
	 */
	public static final float maxLeftPositionX = -1.0f;

	/**
	 * Maximum X-AXIS (right)
	 */
	public static final float maxRightPositionX = 1.0f - PongUIConstants.PONG_BALL_SIZE;

	/**
	 * Constructor
	 * 
	 * @param startX starting X position
	 * @param startY starting Y position
	 */
	public PongBall(float startX, float startY) {
		this.setCurrentX(startX);
		this.setCurrentY(startY);
		this.setDirectionX(maxRightPositionX);
		this.setDirectionY(0.0f);
	}

	/**
	 * Recenter the ball
	 */
	public void resetPosition() {
		this.setCurrentX(-0.025f);
		this.setCurrentY(-0.025f);
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

	/**
	 * Change the position according to the direction
	 */
	public void updatePositionFromDirection() {
		float futureX = getFutureXFromDirection();
		this.setCurrentX(futureX);
		float futureY = getFutureYFromDirection();
		this.setCurrentY(futureY);
	}

	/**
	 * Get the future X position from the direction
	 * 
	 * @return
	 */
	private float getFutureXFromDirection() {
		float futureX = this.getCurrentX();

		// Add or remove position
		if (this.getDirectionX() > this.getCurrentX()) {
			futureX = this.getCurrentX() + BALL_SPEED_X;
		} else {
			futureX = this.getCurrentX() - BALL_SPEED_X;
		}

		// Check for limits
		if (futureX > maxRightPositionX) {
			futureX = maxRightPositionX;
		} else if (futureX < maxLeftPositionX) {
			futureX = maxLeftPositionX;
		}

		return futureX;
	}

	/**
	 * Get the future Y position from the direction
	 * 
	 * @return
	 */
	private float getFutureYFromDirection() {
		float futureY = this.getCurrentY();

		// Add or remove position
		if (this.getDirectionY() > this.getCurrentY()) {
			futureY = this.getCurrentY() + BALL_SPEED_Y;
		} else {
			futureY = this.getCurrentY() - BALL_SPEED_Y;
		}

		// Check for limits
		if (futureY >= maxTopPositionY) {
			futureY = maxTopPositionY;
		} else if (futureY <= maxBottomPositionY) {
			futureY = maxBottomPositionY;
		}

		return futureY;
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

	public float getDirectionX() {
		return directionX;
	}

	protected void setDirectionX(float directionX) {
		this.directionX = directionX;
	}

	public float getDirectionY() {
		return directionY;
	}

	protected void setDirectionY(float directionY) {
		this.directionY = directionY;
	}
}
