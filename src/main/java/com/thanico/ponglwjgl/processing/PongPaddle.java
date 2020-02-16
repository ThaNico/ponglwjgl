package com.thanico.ponglwjgl.processing;

import com.thanico.ponglwjgl.ui.PongUIConstants;
import com.thanico.ponglwjgl.ui.PongUIDrawer;

public class PongPaddle {

	private float minX;
	private float maxX;
	private float minY;
	private float maxY;

	private float currentX;
	private float currentY;

	public PongPaddle(float startX, float startY, float minX, float maxX, float minY, float maxY) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;

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

	public float getCurrentX() {
		return currentX;
	}

	public void setCurrentX(float currentX) {
		this.currentX = currentX;
	}

	public float getCurrentY() {
		return currentY;
	}

	public void setCurrentY(float currentY) {
		this.currentY = currentY;
	}
}
