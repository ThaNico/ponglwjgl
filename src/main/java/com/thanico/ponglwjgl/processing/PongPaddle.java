package com.thanico.ponglwjgl.processing;

import com.thanico.ponglwjgl.ui.PongUIConstants;
import com.thanico.ponglwjgl.ui.PongUIDrawer;

public class PongPaddle {

	public static final float maxTopPositionY = 0.99f;
	public static final float maxBottomPosition = -0.69f;

	private float currentX;
	private float currentY;

	public PongPaddle(float startX, float startY) {
		this.setCurrentX(startX);
		this.setCurrentY(startY);
	}

	public void draw() {
		float x1 = this.getCurrentX();
		float x2 = this.getCurrentX() + PongUIConstants.PONG_WIDTH;
		float y1 = this.getCurrentY();
		float y2 = this.getCurrentY() - PongUIConstants.PONG_SIZE;
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
