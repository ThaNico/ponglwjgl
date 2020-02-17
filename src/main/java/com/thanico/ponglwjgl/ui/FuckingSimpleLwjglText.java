package com.thanico.ponglwjgl.ui;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

	private int font_tex;

	private STBTTPackedchar.Buffer chardata;

	private static final int BITMAP_W = 512;
	private static final int BITMAP_H = 512;

	private static final float[] scale = { 24.0f, 14.0f };

	private final STBTTAlignedQuad q = STBTTAlignedQuad.malloc();
	private final FloatBuffer xb = memAllocFloat(1);
	private final FloatBuffer yb = memAllocFloat(1);

	private float rotate_t, translate_t;

	/**
	 * 
	 */
	private Map<String, PongUIText> textList;

	/**
	 * 
	 */
	public FuckingSimpleLwjglText(int width, int height) {
		this.setTextList(new HashMap<String, PongUIText>());
		load_fonts();
		this.addText("T1", "ABC123", 80, 30, false, false, 0);
		this.addText("T2", "DEF456", 80, 20, true, false, 1);
		this.addText("T3", "GHI789", 80, 10, false, true, 2);

		ww = width;
		wh = height;

	}

	/**
	 * 
	 * 
	 * @param textKey
	 * @param text
	 * @param x
	 * @param y
	 * @param rotationEnabled
	 * @param translationEnabled
	 * @param fontID
	 */
	public void addText(String textKey, String text, float x, float y, boolean rotationEnabled,
			boolean translationEnabled, int fontID) {
		PongUIText put = new PongUIText(text, x, y, rotationEnabled, translationEnabled, fontID);
		this.getTextList().put(textKey, put);
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
		// Needed to display clear text
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// glColor3f(1.0f, 1.0f, 1.0f);

		for (Entry<String, PongUIText> entry : this.getTextList().entrySet()) {
			PongUIText put = entry.getValue();

			float x = put.getX();
			if (put.isTranslationEnabled()) {
				glMatrixMode(GL_MODELVIEW);
				glTranslatef(200, 350, 0);
				x += translate_t * 8 % 30;
			}

			if (put.isRotationEnabled()) {
				glTranslatef(100, 150, 0);
				glRotatef(rotate_t * 2, 0, 0, 1);
				glTranslatef(-100, -150, 0);
			}

			print(x, put.getY(), put.getFontID(), put.getText());
		}

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

	public Map<String, PongUIText> getTextList() {
		return textList;
	}

	public void setTextList(Map<String, PongUIText> textList) {
		this.textList = textList;
	}

}
