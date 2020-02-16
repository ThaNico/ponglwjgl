package com.thanico.ponglwjgl.ui;

import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

public class PongUIDrawer {
	/**
	 * Method used to draw a rectangle and fill it
	 * 
	 * @param x1    x-axis left side
	 * @param x2    x-axis right side
	 * @param y1    y-axis top
	 * @param y2    y-axis bottom
	 * 
	 * @param red   float for color
	 * @param green float for color
	 * @param blue  float for color
	 */
	public static void drawRectangle(float x1, float x2, float y1, float y2, float red, float green, float blue) {
		glColor3f(red, green, blue);

		glBegin(GL_POLYGON);
		glVertex3f(x1, y1, 0f); // XYZ left, top
		glVertex3f(x2, y1, 0f); // XYZ right, top
		glVertex3f(x2, y2, 0f); // XYZ right, bottom
		glVertex3f(x1, y2, 0f); // XYZ left, bottom
		glEnd();
	}
}
