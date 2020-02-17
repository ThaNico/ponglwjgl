package com.thanico.ponglwjgl.ui;

/**
 * 
 * @author Nicolas
 *
 */
public class PongUIText {

	private String text;

	private boolean rotationEnabled;
	private boolean translationEnabled;

	private int fontID;

	private float x, y;

	/**
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param rotationEnabled
	 * @param translationEnabled
	 * @param fontID
	 */
	public PongUIText(String text, float x, float y, boolean rotationEnabled, boolean translationEnabled, int fontID) {
		this.setText(text);
		this.setX(x);
		this.setY(y);
		this.setRotationEnabled(rotationEnabled);
		this.setTranslationEnabled(translationEnabled);
		this.setFontID(fontID);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isRotationEnabled() {
		return rotationEnabled;
	}

	public void setRotationEnabled(boolean rotationEnabled) {
		this.rotationEnabled = rotationEnabled;
	}

	public boolean isTranslationEnabled() {
		return translationEnabled;
	}

	public void setTranslationEnabled(boolean translationEnabled) {
		this.translationEnabled = translationEnabled;
	}

	public int getFontID() {
		return fontID;
	}

	public void setFontID(int fontID) {
		this.fontID = fontID;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
