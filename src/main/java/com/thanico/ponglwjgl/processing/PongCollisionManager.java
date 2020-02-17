package com.thanico.ponglwjgl.processing;

/**
 * Class used to manage the ball's collisions
 * 
 * @author Nicolas
 *
 */
public class PongCollisionManager {
	PongPaddle leftPaddle;
	PongPaddle rightPaddle;
	PongBall theBall;

	/**
	 * Constructor
	 * 
	 * @param leftPaddle
	 * @param rightPaddle
	 * @param theBall
	 */
	public PongCollisionManager(PongPaddle leftPaddle, PongPaddle rightPaddle, PongBall theBall) {
		this.leftPaddle = leftPaddle;
		this.rightPaddle = rightPaddle;
		this.theBall = theBall;
	}

	/**
	 * Change the ball direction if collision detected
	 */
	public void changeDirectionIfCollision() {
		if (isBallCollidingWithPaddle(leftPaddle) || isBallCollidingWithPaddle(rightPaddle)
				|| isBallCollidingWithBorder()) {
			changeBallDirection();
		}
	}

	/**
	 * Method used to check if the ball is colliding with a paddle
	 * 
	 * @param paddleToCheck
	 * @return true if colliding, false if not
	 */
	private boolean isBallCollidingWithPaddle(PongPaddle paddleToCheck) {
		return false;
	}

	/**
	 * Method used to check if the ball is colliding with the borders
	 * 
	 * @return true if colliding, false if not
	 */
	private boolean isBallCollidingWithBorder() {
		return false;
	}

	/**
	 * Change the ball direction
	 */
	private void changeBallDirection() {
		this.theBall.setDirectionX(0.4f);
		this.theBall.setDirectionY(0.4f);
	}
}
