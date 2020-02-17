package com.thanico.ponglwjgl.processing;

import static org.junit.Assert.*;

import org.junit.Test;

public class PongCollisionManagerTest {

	@Test
	public void testBasicConstructor() {
		PongCollisionManager pcm = new PongCollisionManager(null, null, null);

		assertEquals("", PongCollisionManager.EXPECTED_DIRECTION.GOTO_NONE, pcm.getExpectedDirection());
	}

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

}
