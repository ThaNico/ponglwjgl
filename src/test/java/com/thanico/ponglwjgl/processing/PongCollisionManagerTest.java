package com.thanico.ponglwjgl.processing;

import static org.junit.Assert.*;

import org.junit.Test;

import com.thanico.ponglwjgl.ui.PongUIConstants;

public class PongCollisionManagerTest {

	@Test
	public void testBasicConstructor() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, null);

		assertEquals("", PongCollisionManager.EXPECTED_DIRECTION.GOTO_NONE, pcm.getExpectedDirection());
	}

	// Tests on the isBallCollidingWithBorder method
	//

	@Test
	public void test_isBallCollidingWithBorder_withCollisionRightSide() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, new PongBall(PongBall.maxRightPositionX, 0));

		assertTrue("", pcm.isBallCollidingWithBorder());
		assertEquals("", PongCollisionManager.EXPECTED_DIRECTION.GOTO_LEFT, pcm.getExpectedDirection());
	}

	@Test
	public void test_isBallCollidingWithBorder_withCollisionLeftSide() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, new PongBall(PongBall.maxLeftPositionX, 0));

		assertTrue("", pcm.isBallCollidingWithBorder());
		assertEquals("", PongCollisionManager.EXPECTED_DIRECTION.GOTO_RIGHT, pcm.getExpectedDirection());
	}

	@Test
	public void test_isBallCollidingWithBorder_withCollisionTopSide() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, new PongBall(0, PongBall.maxTopPositionY));

		assertTrue("", pcm.isBallCollidingWithBorder());
		assertEquals("", PongCollisionManager.EXPECTED_DIRECTION.GOTO_BOT, pcm.getExpectedDirection());
	}

	@Test
	public void test_isBallCollidingWithBorder_withCollisionBotSide() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, new PongBall(0, PongBall.maxBottomPositionY));

		assertTrue("", pcm.isBallCollidingWithBorder());
		assertEquals("", PongCollisionManager.EXPECTED_DIRECTION.GOTO_TOP, pcm.getExpectedDirection());
	}

	@Test
	public void test_isBallCollidingWithBorder_withoutCollision() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, new PongBall(0.01f, 0.01f));

		assertFalse("", pcm.isBallCollidingWithBorder());
		assertEquals("", PongCollisionManager.EXPECTED_DIRECTION.GOTO_NONE, pcm.getExpectedDirection());
	}

	// tests on the changeBallDirection method
	//

	@Test
	public void test_changeBallDirection_gotoTop_shouldChangeYaxis() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, new PongBall(0, 0));
		pcm.setExpectedDirection(PongCollisionManager.EXPECTED_DIRECTION.GOTO_TOP);

		pcm.changeBallDirection();
		assertEquals("", PongBall.maxRightPositionX, pcm.getTheBall().getDirectionX(), 0);
		assertEquals("", PongBall.maxTopPositionY, pcm.getTheBall().getDirectionY(), 0);
	}

	@Test
	public void test_changeBallDirection_gotoBot_shouldChangeYaxis() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, new PongBall(0, 0));
		pcm.setExpectedDirection(PongCollisionManager.EXPECTED_DIRECTION.GOTO_BOT);

		pcm.changeBallDirection();
		assertEquals("", PongBall.maxRightPositionX, pcm.getTheBall().getDirectionX(), 0);
		assertEquals("", PongBall.maxBottomPositionY, pcm.getTheBall().getDirectionY(), 0);
	}

	@Test
	public void test_changeBallDirection_gotoLeft_shouldChangeXaxis() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, new PongBall(0, 0));
		pcm.setExpectedDirection(PongCollisionManager.EXPECTED_DIRECTION.GOTO_LEFT);

		pcm.changeBallDirection();
		assertEquals("", PongBall.maxLeftPositionX, pcm.getTheBall().getDirectionX(), 0);
		assertEquals("", 0, pcm.getTheBall().getDirectionY(), 0);
	}

	@Test
	public void test_changeBallDirection_gotoRight_shouldChangeXaxis() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, new PongBall(0, 0));
		pcm.setExpectedDirection(PongCollisionManager.EXPECTED_DIRECTION.GOTO_RIGHT);

		pcm.changeBallDirection();
		assertEquals("", PongBall.maxRightPositionX, pcm.getTheBall().getDirectionX(), 0);
		assertEquals("", 0, pcm.getTheBall().getDirectionY(), 0);
	}

	@Test
	public void test_changeBallDirection_gotoNone_shouldNotChange() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, new PongBall(0, 0));
		assertEquals("", PongCollisionManager.EXPECTED_DIRECTION.GOTO_NONE, pcm.getExpectedDirection());

		pcm.changeBallDirection();
		assertEquals("", PongBall.maxRightPositionX, pcm.getTheBall().getDirectionX(), 0);
		assertEquals("", 0, pcm.getTheBall().getDirectionY(), 0);
	}

	// tests on the isBallCollidingWithPaddle method
	//
	@Test
	public void test_isBallCollidingWithPaddle_withoutCoords_shouldBeTrue() {
		PongBall pongball = new PongBall(0, 0);
		PongPaddle paddle = new PongPaddle(0, 0);
		PongCollisionManager pcm = new PongCollisionManager(null, null, pongball);

		boolean collision = pcm.isBallCollidingWithPaddle(paddle, true);
		assertTrue("", collision);
	}

	@Test
	public void test_isBallCollidingWithPaddle_defaultCoords_shouldBeFalse() {
		PongBall pongball = new PongBall(-0.025f, -0.025f);
		PongPaddle paddle = new PongPaddle(-0.97f, 0.95f);
		PongCollisionManager pcm = new PongCollisionManager(null, null, pongball);

		boolean collision = pcm.isBallCollidingWithPaddle(paddle, true);
		assertFalse("", collision);
	}

	// tests on the isBallCollidingWithPaddle method
	// (X axis correct ; Y axis variation : top/bottom)
	//
	@Test
	public void test_isBallCollidingWithPaddle_topBallAsTopPaddle_shouldBeTrue() {
		PongBall pongball = new PongBall(-0.97f, -0.025f);
		PongPaddle paddle = new PongPaddle(-0.97f, -0.025f);
		PongCollisionManager pcm = new PongCollisionManager(null, null, pongball);

		assertTrue("", pcm.isBallCollidingWithPaddle(paddle, true));
		assertTrue("", pcm.isBallCollidingWithPaddle(paddle, false));
	}

	@Test
	public void test_isBallCollidingWithPaddle_botBallAsTopPaddle_shouldBeTrue() {
		PongBall pongball = new PongBall(-0.97f, -0.025f + PongUIConstants.PONG_BALL_SIZE);
		PongPaddle paddle = new PongPaddle(-0.97f, -0.025f);
		PongCollisionManager pcm = new PongCollisionManager(null, null, pongball);

		assertTrue("", pcm.isBallCollidingWithPaddle(paddle, true));
		assertTrue("", pcm.isBallCollidingWithPaddle(paddle, false));
	}

	@Test
	public void test_isBallCollidingWithPaddle_topBallAsBotPaddle_shouldBeTrue() {
		PongBall pongball = new PongBall(-0.97f, -0.025f);
		PongPaddle paddle = new PongPaddle(-0.97f, -0.025f + PongUIConstants.PONG_SIZE);
		PongCollisionManager pcm = new PongCollisionManager(null, null, pongball);

		assertTrue("", pcm.isBallCollidingWithPaddle(paddle, true));
		assertTrue("", pcm.isBallCollidingWithPaddle(paddle, false));
	}

	@Test
	public void test_isBallCollidingWithPaddle_botBallAsBotPaddle_shouldBeTrue() {
		PongBall pongball = new PongBall(-0.97f, -0.025f + PongUIConstants.PONG_BALL_SIZE);
		PongPaddle paddle = new PongPaddle(-0.97f, -0.025f + PongUIConstants.PONG_SIZE);
		PongCollisionManager pcm = new PongCollisionManager(null, null, pongball);

		assertTrue("", pcm.isBallCollidingWithPaddle(paddle, true));
		assertTrue("", pcm.isBallCollidingWithPaddle(paddle, false));
	}

	@Test
	public void test_isBallCollidingWithPaddle_ballTooHigh_shouldBeFalse() {
		PongBall pongball = new PongBall(-0.97f, -0.025f + PongUIConstants.PONG_BALL_SIZE + 0.1f);
		PongPaddle paddle = new PongPaddle(-0.97f, -0.025f);
		PongCollisionManager pcm = new PongCollisionManager(null, null, pongball);

		assertFalse("", pcm.isBallCollidingWithPaddle(paddle, true));
		assertFalse("", pcm.isBallCollidingWithPaddle(paddle, false));
	}

	@Test
	public void test_isBallCollidingWithPaddle_ballTooLow_shouldBeFalse() {
		PongBall pongball = new PongBall(-0.97f, -0.025f - 0.1f);
		PongPaddle paddle = new PongPaddle(-0.97f, -0.025f + PongUIConstants.PONG_SIZE);
		PongCollisionManager pcm = new PongCollisionManager(null, null, pongball);

		assertFalse("", pcm.isBallCollidingWithPaddle(paddle, true));
		assertFalse("", pcm.isBallCollidingWithPaddle(paddle, false));
	}

	// tests on the isBallCollidingWithPaddle method
	// (X axis incorrect)
	//

	@Test
	public void test_isBallCollidingWithPaddle_ballRightOfThePaddle() {
		PongBall pongball = new PongBall(-0.67f, -0.025f);
		PongPaddle paddle = new PongPaddle(-0.97f, -0.025f);
		PongCollisionManager pcm = new PongCollisionManager(null, null, pongball);

		assertFalse("", pcm.isBallCollidingWithPaddle(paddle, true));
		assertTrue("", pcm.isBallCollidingWithPaddle(paddle, false));
	}

	@Test
	public void test_isBallCollidingWithPaddle_ballLeftOfThePaddle() {
		PongBall pongball = new PongBall(-0.97f, -0.025f);
		PongPaddle paddle = new PongPaddle(-0.67f, -0.025f);
		PongCollisionManager pcm = new PongCollisionManager(null, null, pongball);

		assertTrue("", pcm.isBallCollidingWithPaddle(paddle, true));
		assertFalse("", pcm.isBallCollidingWithPaddle(paddle, false));
	}

	// rightPaddle = new PongPaddle(0.97f, 0.95f);
}
