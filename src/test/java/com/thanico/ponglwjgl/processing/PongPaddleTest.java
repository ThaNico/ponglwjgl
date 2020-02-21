package com.thanico.ponglwjgl.processing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class PongPaddleTest {

	@Test
	public void testBasicConstructor() {
		PongPaddle paddle = new PongPaddle(1.2f, 2.3f);
		assertEquals("", 1.2f, paddle.getCurrentX(), 0);
		assertEquals("", 2.3f, paddle.getCurrentY(), 0);
	}

	@Test
	public void test_getFutureYafterMoveTop_addSimpleValue() {
		PongPaddle paddle = new PongPaddle(0.0f, 0.0f);
		assertEquals("", 0.2f, paddle.getFutureYafterMoveTop(0.2f), 0);
	}

	@Test
	public void test_getFutureYafterMoveTop_addValueAboveLimit() {
		PongPaddle paddle = new PongPaddle(0.0f, 0.0f);
		assertEquals("", PongPaddle.maxTopPositionY, paddle.getFutureYafterMoveTop(PongPaddle.maxTopPositionY + 1.0f),
				0.0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_getFutureYafterMoveTop_addNegativeValue_shouldThrowException() {
		try {
			new PongPaddle(0, 0).getFutureYafterMoveTop(-1.0f);
		} catch (IllegalArgumentException e) {
			assertEquals("", "Move amount should be positive.", e.getMessage());
			throw e;
		}
		fail("This test should throw an IllegalArgumentException");
	}

	@Test
	public void test_getFutureYafterMoveBottom_addSimpleValue() {
		PongPaddle paddle = new PongPaddle(0.0f, 0.0f);
		assertEquals("", -0.2f, paddle.getFutureYafterMoveBottom(0.2f), 0);
	}

	@Test
	public void test_getFutureYafterMoveBottom_addValueAboveLimit() {
		PongPaddle paddle = new PongPaddle(0.0f, 0.0f);
		assertEquals("", PongPaddle.maxBottomPosition,
				paddle.getFutureYafterMoveBottom((PongPaddle.maxBottomPosition - 1.0f) * -1.0f), 0.0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_getFutureYafterMoveBottom_addNegativeValue_shouldThrowException() {
		try {
			new PongPaddle(0, 0).getFutureYafterMoveBottom(-1.0f);
		} catch (IllegalArgumentException e) {
			assertEquals("", "Move amount should be positive.", e.getMessage());
			throw e;
		}
		fail("This test should throw an IllegalArgumentException");
	}

}
