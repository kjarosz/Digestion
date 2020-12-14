package com.kjarosz.digestion.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

class ImageQueue {	
	private LinkedList<ImageItem> mItemBuffer;
	private Comparator<ImageItem> mComparator = new ImageItem();
	
	private ColorMode mColorMode;
	private ColorMode mLastMode;
	
	ImageQueue() {
		mItemBuffer = new LinkedList<>();
	}
	
	ColorMode setMode(ColorMode mode) {
		ColorMode lastMode = mLastMode;
		mLastMode = mode;
		return lastMode;
	}
	
	void addImage(ImageItem image) {
		image.colorMode = mLastMode;
		mItemBuffer.add(image);
	}
	
	boolean hasImages() {
		return !mItemBuffer.isEmpty();
	}
	
	void sort() {
		Collections.sort(mItemBuffer, mComparator);
	}
	
	ImageItem nextImage(Graphics2D g) {
		ImageItem image = mItemBuffer.pollFirst();
		if(image == null)
			return null;
		
		if(mColorMode != image.colorMode)
			switchColorMode(g, image.colorMode);
		
		return image;
	}
	
	private void switchColorMode(Graphics2D g, ColorMode mode) {
		Color color = mode.getColor();
		if(color != null)
			g.setColor(color);
		
		Color background = mode.getBackgroundColor();
		if(background != null)
			g.setBackground(background);
	}
}
