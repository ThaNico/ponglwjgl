package com.thanico.ponglwjgl.processing;

import com.thanico.ponglwjgl.ui.PongUIConstants;
import com.thanico.ponglwjgl.ui.PongUIDrawer;

/**
 * Class representing a paddle and its position
 * 
 * @author Nicolas
 *
 */
public class PongPaddle {

	/**
	 * Move speed of the paddles
	 */
	public static final float PADDLE_SPEED_Y = 0.042f;

	/**
	 * Maximum Y-AXIS (top)
	 */
	public static final float maxTopPositionY = 0.85f;

	/**
	 * Maximum Y-AXIS (bottom)
	 */
	public static final float maxBottomPosition = -0.69f;

	private float currentX;
	private float currentY;

	/**
	 * Constructor
	 * 
	 * @param startX starting X position
	 * @param startY starting Y position
	 */
	public PongPaddle(float startX, float startY) {
		this.setCurrentX(startX);
		this.setCurrentY(startY);
	}

	/**
	 * Draw the rectangle using current position
	 */
	public void draw() {
		float x1 = this.getCurrentX();
		float x2 = this.getCurrentX() + PongUIConstants.PONG_PADDLE_WIDTH;
		float y1 = this.getCurrentY();
		float y2 = this.getCurrentY() - PongUIConstants.PONG_PADDLE_YSIZE;
		PongUIDrawer.drawRectangle(x1, x2, y1, y2, 1.0f, 1.0f, 1.0f);
	}

	/**
	 * Move the paddle to the top
	 * 
	 * @param amount amount to move
	 */
	public void moveTop(float amount) {
		float futureY = getFutureYafterMoveTop(amount);
		this.setCurrentY(futureY);
		this.draw();
	}

	/**
	 * Calculate the future Y position after moveTop call
	 * 
	 * @param amount amount to move
	 * @return future Y position (maximum is maxTopPositionY)
	 */
	protected float getFutureYafterMoveTop(float amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Move amount should be positive.");
		}

		float futureY = this.getCurrentY() + amount;
		if (futureY > maxTopPositionY) {
			futureY = maxTopPositionY;
		}
		return futureY;
	}

	/**
	 * Move the paddle to the bottom
	 * 
	 * @param amount amount to move
	 */
	public void moveBottom(float amount) {
		float futureY = getFutureYafterMoveBottom(amount);
		this.setCurrentY(futureY);
		this.draw();
	}

	/**
	 * Calculate the future Y position after moveBottom call
	 * 
	 * @param amount amount to move
	 * @return future Y position (minimum is maxBottomPosition)
	 */
	protected float getFutureYafterMoveBottom(float amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Move amount should be positive.");
		}

		float futureY = this.getCurrentY() - amount;
		if (futureY < maxBottomPosition) {
			futureY = maxBottomPosition;
		}
		return futureY;
	}

	protected float getCurrentX() {
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
