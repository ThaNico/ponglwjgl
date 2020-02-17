package com.thanico.ponglwjgl.ui;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;

import com.thanico.ponglwjgl.utils.IOUtil;

/**
 * Based on : "STB Truetype oversampling demo."<br>
 * but easier af (or trying to be)
 *
 * <p>
 * This is a Java port of <a href=
 * "https://github.com/nothings/stb/blob/master/tests/oversample/main.c">https://github
 * .com/nothings/stb/blob/master/tests/oversample/main.c</a>.
 * </p>
 */
public class FuckingSimpleLwjglText {
	private static final String TTF_FONT_NAME = "fonts/BebasNeue-Regular.ttf";
	private int ww;
	private int wh;
	private int fbw;
	private int fbh;

	private int font_tex;

	private STBTTPackedchar.Buffer chardata;

	private static final int BITMAP_W = 512;
	private static final int BITMAP_H = 512;

	private static final float[] scale = { 24.0f, 14.0f };

	private final STBTTAlignedQuad q = STBTTAlignedQuad.malloc();
	private final FloatBuffer xb = memAllocFloat(1);
	private final FloatBuffer yb = memAllocFloat(1);

	private static final int[] sf = { 0, 1, 2, 0, 1, 2 };
	private int font = 3;

	private boolean translating;
	private boolean rotating;

	private float rotate_t, translate_t;

	public FuckingSimpleLwjglText() {
		ww = 400;
		wh = 400;
		fbw = 400;
		fbh = 400;
		this.init();
	}

	private void init() {
		load_fonts();
		toggleRotation();
		toggleTranslation();
	}

	public void loop(float dt) {
		if (dt > 0.25f) {
			dt = 0.25f;
		}
		if (dt < 0.01f) {
			dt = 0.01f;
		}

		rotate_t += dt;
		translate_t += dt;

		draw_init();
		draw_world();
	}

	/**
	 * 
	 */
	private void draw_init() {
		// glDisable(GL_CULL_FACE);
		// glDisable(GL_TEXTURE_2D);
		// glDisable(GL_LIGHTING);
		// glDisable(GL_DEPTH_TEST);

		// glViewport(0, 0, fbw, fbh);
		// glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		// glClear(GL_COLOR_BUFFER_BIT);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, ww, wh, 0.0, -1.0, 1.0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	/**
	 * 
	 */
	private void draw_world() {
		int sfont = sf[font];

		float x = 10;

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// glColor3f(1.0f, 1.0f, 1.0f);

		print(80, 30, sfont, "ABC123");

		glMatrixMode(GL_MODELVIEW);
		glTranslatef(200, 350, 0);

		if (translating) {
			x += translate_t * 8 % 30;
		}

		if (rotating) {
			glTranslatef(100, 150, 0);
			glRotatef(rotate_t * 2, 0, 0, 1);
			glTranslatef(-100, -150, 0);
		}
		print(x, 2, font, "This is a test");
		print(x, 10, font, "Now is the time for all good men to come to the aid of their country.");
		print(x, 18, font, "The quick brown fox jumps over the lazy dog.");
		print(x, 26, font, "0123456789");

	}

	public void toggleRotation() {
		rotating = !rotating;
		rotate_t = 0.0f;
	}

	public void toggleTranslation() {
		translating = !translating;
		translate_t = 0.0f;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param font
	 * @param text
	 */
	private void print(float x, float y, int font, String text) {
		xb.put(0, x);
		yb.put(0, y);

		chardata.position(font * 128);

		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, font_tex);

		glBegin(GL_QUADS);
		for (int i = 0; i < text.length(); i++) {
			stbtt_GetPackedQuad(chardata, BITMAP_W, BITMAP_H, text.charAt(i), xb, yb, q, false);
			drawBoxTC(q.x0(), q.y0(), q.x1(), q.y1(), q.s0(), q.t0(), q.s1(), q.t1());
		}
		glEnd();
	}

	private static void drawBoxTC(float x0, float y0, float x1, float y1, float s0, float t0, float s1, float t1) {
		glTexCoord2f(s0, t0);
		glVertex2f(x0, y0);
		glTexCoord2f(s1, t0);
		glVertex2f(x1, y0);
		glTexCoord2f(s1, t1);
		glVertex2f(x1, y1);
		glTexCoord2f(s0, t1);
		glVertex2f(x0, y1);
	}

	/**
	 * 
	 */
	public void destroy() {
		chardata.free();
		memFree(yb);
		memFree(xb);
		q.free();
	}

	/**
	 * 
	 */
	private void load_fonts() {
		font_tex = glGenTextures();
		chardata = STBTTPackedchar.malloc(6 * 128);

		try (STBTTPackContext pc = STBTTPackContext.malloc()) {
			ByteBuffer ttf = IOUtil.ioResourceToByteBuffer(TTF_FONT_NAME, 512 * 1024);

			ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);

			stbtt_PackBegin(pc, bitmap, BITMAP_W, BITMAP_H, 0, 1, NULL);
			for (int i = 0; i < 2; i++) {
				int p = (i * 3 + 0) * 128 + 32;
				chardata.limit(p + 95);
				chardata.position(p);
				stbtt_PackSetOversampling(pc, 1, 1);
				stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);

				p = (i * 3 + 1) * 128 + 32;
				chardata.limit(p + 95);
				chardata.position(p);
				stbtt_PackSetOversampling(pc, 2, 2);
				stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);

				p = (i * 3 + 2) * 128 + 32;
				chardata.limit(p + 95);
				chardata.position(p);
				stbtt_PackSetOversampling(pc, 3, 1);
				stbtt_PackFontRange(pc, ttf, 0, scale[i], 32, chardata);
			}
			chardata.clear();
			stbtt_PackEnd(pc);

			glBindTexture(GL_TEXTURE_2D, font_tex);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_W, BITMAP_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
