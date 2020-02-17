package com.thanico.ponglwjgl.processing;

import com.thanico.ponglwjgl.ui.PongUIConstants;

/**
 * Class used to manage the ball's collisions
 * 
 * @author Nicolas
 *
 */
public class PongCollisionManager {
	private PongPaddle leftPaddle;
	private PongPaddle rightPaddle;
	private PongBall theBall;

	// Expected move direction
	enum EXPECTED_DIRECTION {
		GOTO_NONE, GOTO_LEFT, GOTO_RIGHT, GOTO_TOP, GOTO_BOT,
	}

	private EXPECTED_DIRECTION expectedDirection;

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
		this.setTheBall(theBall);
		this.setExpectedDirection(EXPECTED_DIRECTION.GOTO_NONE);
	}

	/**
	 * Change the ball direction if collision detected
	 */
	public void changeDirectionIfCollision() {
		if (isBallCollidingWithPaddle(leftPaddle, true) || isBallCollidingWithPaddle(rightPaddle, false)
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
	private boolean isBallCollidingWithPaddle(PongPaddle paddleToCheck, boolean isLeftPaddle) {
		boolean collisionDetected = false;

		// check X axis first
		boolean possibleCollisionOnX = false;
		if (isLeftPaddle) {
			float ballLeftCoord = this.getTheBall().getCurrentX();
			float paddleBorder = paddleToCheck.getCurrentX() + PongUIConstants.PONG_WIDTH;
			possibleCollisionOnX = (ballLeftCoord <= paddleBorder);
		} else {
			float ballRightCoord = this.getTheBall().getCurrentX() + PongUIConstants.PONG_BALL_SIZE;
			float paddleBorder = paddleToCheck.getCurrentX();
			possibleCollisionOnX = (ballRightCoord >= paddleBorder);
		}

		if (possibleCollisionOnX) {
			float ballTopCoord = 2.0f + this.getTheBall().getCurrentY();
			float ballBotCoord = ballTopCoord - PongUIConstants.PONG_BALL_SIZE;

			float paddleTopCoord = 2.0f + paddleToCheck.getCurrentY();
			float paddleBotCoord = paddleTopCoord - PongUIConstants.PONG_SIZE;

			collisionDetected = (ballBotCoord <= paddleTopCoord && ballTopCoord >= paddleBotCoord);

			System.out.println(" (" + ballBotCoord + " <= " + paddleTopCoord + " && " + ballTopCoord + " >= "
					+ paddleBotCoord + ")");
			System.out.println("collisionDetected ==> " + collisionDetected);

		}

		if (collisionDetected) {
			// if collision on left paddle then goto right
			this.setExpectedDirection((isLeftPaddle ? EXPECTED_DIRECTION.GOTO_RIGHT : EXPECTED_DIRECTION.GOTO_LEFT));
		}
		return collisionDetected;
	}

	/**
	 * Method used to check if the ball is colliding with the borders
	 * 
	 * @return true if colliding, false if not
	 */
	protected boolean isBallCollidingWithBorder() {
		boolean collision = false;

		// touch right => expect left
		if (this.getTheBall().getCurrentX() >= PongBall.maxRightPositionX) {
			collision = true;
			this.setExpectedDirection(EXPECTED_DIRECTION.GOTO_LEFT);
		}
		// touch left => expect right
		else if (this.getTheBall().getCurrentX() <= PongBall.maxLeftPositionX) {
			collision = true;
			this.setExpectedDirection(EXPECTED_DIRECTION.GOTO_RIGHT);
		}
		// touch top => expect bottom
		else if (this.getTheBall().getCurrentY() >= PongBall.maxTopPositionY) {
			collision = true;
			this.setExpectedDirection(EXPECTED_DIRECTION.GOTO_BOT);
		}
		// touch bottom => expect top
		else if (this.getTheBall().getCurrentY() <= PongBall.maxBottomPositionY) {
			collision = true;
			this.setExpectedDirection(EXPECTED_DIRECTION.GOTO_TOP);
		}

		return collision;
	}

	/**
	 * Change the ball direction
	 */
	protected void changeBallDirection() {
		float expectedPosX = 0;
		float expectedPosY = 0;

		// Set a border as a direction
		switch (this.getExpectedDirection()) {
		case GOTO_TOP:
			expectedPosY = PongBall.maxTopPositionY;
			break;
		case GOTO_BOT:
			expectedPosY = PongBall.maxBottomPositionY;
			break;
		case GOTO_LEFT:
			expectedPosX = PongBall.maxLeftPositionX;
			break;
		case GOTO_RIGHT:
			expectedPosX = PongBall.maxRightPositionX;
			break;
		default:
		}

		// Change one axis
		if (expectedPosX != 0)
			this.getTheBall().setDirectionX(expectedPosX);
		else if (expectedPosY != 0)
			this.getTheBall().setDirectionY(expectedPosY);
	}

	protected EXPECTED_DIRECTION getExpectedDirection() {
		return expectedDirection;
	}

	protected void setExpectedDirection(EXPECTED_DIRECTION expectedDirection) {
		this.expectedDirection = expectedDirection;
	}

	public PongBall getTheBall() {
		return theBall;
	}

	private void setTheBall(PongBall theBall) {
		this.theBall = theBall;
	}
}
