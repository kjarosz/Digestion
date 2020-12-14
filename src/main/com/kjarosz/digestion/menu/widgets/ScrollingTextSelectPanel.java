package com.kjarosz.digestion.menu.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ScrollingTextSelectPanel implements MouseListener, MouseMotionListener {
	private int mX, mY;
	private int mWidth, mHeight;

	private final int mScrollbarWidth = 8;
	private Rectangle mScrollbar;
	private int mScrollLineHeight;
	private int mPixelRange;

	private String[] mTextOptions;
	private int mSelection, mTemporarySelection;

	BufferedImage mSelectionPanel;
	private Font mTextFont;
	Rectangle mOffsetRectangle;
	private int mFontSize = 15;
	private int mGapSize = 5;

	private boolean mScrollbarGrabbed;
	private Point mGrabbedPos;

	private boolean mActive;

	public ScrollingTextSelectPanel(int x, int y, int width, int height, String[] StringList) {
		mX = x;
		mY = y;
		mActive = false;
		mOffsetRectangle = new Rectangle(0, 0, width, height);
		mTextOptions = StringList;
		mSelection = 0;
		mTemporarySelection = -1;
		mTextFont = new Font(Font.MONOSPACED, Font.PLAIN, mFontSize);
		mScrollbar = new Rectangle(0, 0, mScrollbarWidth, height);
		setSize(width, height);
	}

	public final void setSize(int width, int height) {
		mOffsetRectangle.height = height;
		mOffsetRectangle.width = width - mScrollbarWidth;
		mWidth = width;
		mHeight = height;
		mScrollLineHeight = height;
		int requiredHeight = mTextOptions.length*mFontSize + (mTextOptions.length)*mGapSize;
		if(requiredHeight < mHeight) {
			requiredHeight = mHeight;
		}
		mSelectionPanel = new BufferedImage(mOffsetRectangle.width, requiredHeight, BufferedImage.TYPE_INT_ARGB);
		mScrollbar.x = mX+mWidth-mScrollbar.width;
		mScrollbar.y = mY;
		mScrollbar.height = (height*mScrollLineHeight)/requiredHeight;
		mPixelRange = height - mScrollbar.height;
	}

	public String getSelection() {
		if(mSelection < mTextOptions.length) {
			return mTextOptions[mSelection];
		} else {
			return null;
		}
	}

	public void setActive(boolean flag) {
		mActive = flag;
	}

	public void Draw(Graphics2D g) {
		Graphics2D g2 = mSelectionPanel.createGraphics();
		
		Font oldFont = g2.getFont();
		Color oldColor = g2.getColor();
		g2.setFont(mTextFont);
		g2.setColor(Color.BLACK);
		
		//System.out.println("( " + x + ", " + y + ", " + width + ", " + height + ")");

		g2.clearRect(mOffsetRectangle.x, mOffsetRectangle.y, mOffsetRectangle.width, mOffsetRectangle.height);

		g2.setColor(Color.BLUE);

		for(int i = 0; i < mTextOptions.length; i++) {
			if(i == mSelection || i == mTemporarySelection) {
				g2.setBackground(Color.WHITE);
				g2.setColor(Color.WHITE);
				g2.fillRect(0, (i*mFontSize + i*mGapSize), mOffsetRectangle.width, mFontSize + 2);
				g2.setBackground(Color.BLACK);
				g2.setColor(Color.BLUE);
			}
			g2.drawString(mTextOptions[i], mGapSize, mFontSize + (i*mFontSize + i*mGapSize));
		}

		g2.setFont(oldFont);
		g2.setColor(oldColor);

		g.clearRect(mX, mY, mWidth, mHeight);

		int selectHeight = mHeight;
		if(mOffsetRectangle.height < mHeight) {
			selectHeight = mOffsetRectangle.height;
		}

		g.drawImage(mSelectionPanel.getSubimage(mOffsetRectangle.x, mOffsetRectangle.y, mOffsetRectangle.width, mOffsetRectangle.height), mX, mY, mWidth-mScrollbar.width, selectHeight, null);

		g.drawRect(mScrollbar.x, mScrollbar.y, mScrollbar.width, mScrollbar.height);
	}

	public void mouseMoved(MouseEvent e) {
		if(mActive) {
			Point mouseCoords = e.getPoint();

			if(!mScrollbarGrabbed) {
				int x = mOffsetRectangle.x + (mouseCoords.x-mX);
				int y = mOffsetRectangle.y + (mouseCoords.y-mY);

				for(int i = 0; i < mTextOptions.length; i++) {
					Rectangle fontRect = new Rectangle(0, (i*mFontSize + i*mGapSize), mOffsetRectangle.width, mFontSize);
					if(fontRect.contains(x, y)) {
						mTemporarySelection = i;
						return;
					}
				}
				mTemporarySelection = -1;
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if(mActive) {
			Point mouseCoords = e.getPoint();

			if(mScrollbarGrabbed) {
				int yDifference = mouseCoords.y - mGrabbedPos.y;
				mScrollbar.y += yDifference;
				if(mScrollbar.y-mY > mPixelRange) {
					mScrollbar.y = mY+mPixelRange;
				} else if(mScrollbar.y < mY) {
					mScrollbar.y = mY;
				}

				mOffsetRectangle.x = 0;
				mOffsetRectangle.y = (int)((float)mSelectionPanel.getHeight()*((float)(mScrollbar.y-mY)/(float)mScrollLineHeight));
				if(mOffsetRectangle.y < 0) {
					mOffsetRectangle.y = 0;
				} else if (mOffsetRectangle.y+mOffsetRectangle.height > mSelectionPanel.getHeight()) {
					mOffsetRectangle.y = mSelectionPanel.getHeight()-mOffsetRectangle.height;
				}

				mGrabbedPos = mouseCoords;
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		if(mActive) {
			Point mouseCoords = e.getPoint();

			if(mScrollbar.contains(mouseCoords)) {
				mScrollbarGrabbed = true;
				mGrabbedPos = mouseCoords;
			} else {
				if(mTemporarySelection != -1) {
					mSelection = mTemporarySelection;
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(mActive) {
			mScrollbarGrabbed = false;
		}
	}

	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
}
