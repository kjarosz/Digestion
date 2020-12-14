package com.kjarosz.digestion.graphics;

import java.awt.Color;

public class ColorMode {
	private Color mColor;
	private Color mBackgroundColor;
	
	public ColorMode() {
		mColor = null;
		mBackgroundColor = null;
	}
	
	public ColorMode(Color color, Color background) {
		mColor = color;
		mBackgroundColor = background;
	}
	
	public ColorMode(ColorMode mode) {
		mColor = mode.mColor;
		mBackgroundColor = mode.mBackgroundColor;
	}
	
	public void setColor(Color color) {
		mColor = color;
	}
	
	public Color getColor() {
		return mColor;
	}
	
	public void setBackgroundColor(Color background) {
		mBackgroundColor = background;
	}
	
	public Color getBackgroundColor() {
		return mBackgroundColor;
	}
}
