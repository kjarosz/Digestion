package Graphics;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import Entity.EntityComponents;
import Util.Size;

/*******************************
 * 
 * To shed some light on the mysterious purpose
 * of this class, its basic purpose is to translate
 * level coordinates to screen coordinates and 
 * provide mechanism to check if objects fall
 * within the viewable area of the level. This,
 * of course, is not without a catch. 
 * 
 * The class provides a mechanism to set up
 * a following camera. That is, given a reference
 * to a some object on the screen, the class will
 * move the coordinates so that the object is 
 * always displayed on the screen.
 * 
 */

public class GameViewport {
	private Size mLevelSize;
	
	private EntityComponents mFocusObject;
	
	private Rectangle2D mLevelViewport;
	
	public GameViewport() {
		mLevelSize = new Size(0, 0);
		
		mFocusObject = null;
		
		mLevelViewport = new Rectangle();
	}
	
	public boolean initialize(int screenWidth, int screenHeight, int levelWidth, int levelHeight) {
		mLevelSize.width = levelWidth;
		mLevelSize.height = levelHeight;
		
		mLevelViewport.setRect(0, 0, screenWidth, screenHeight);
		
		return true;
	}
	
	public void setFocusObject(EntityComponents focusObject) {
		mFocusObject = focusObject;
	}
	
	public void setLevelSize(Size levelSize) {
		if(levelSize.width <= 0 || levelSize.height <= 0)
			return;
		
		mLevelSize = levelSize;
	}
	
	public void setWindowSize(int width, int height) {
	   mLevelViewport.setRect(0, 0, width, height);
	}
	
	public boolean contains(double x, double y, double width, double height) {
		return mLevelViewport.intersects(x, y, width, height);
	}
	
	public boolean contains(Rectangle object) {
		return mLevelViewport.intersects(object);
	}
	
	// If output is null, the function creates a new rectangle and
	// returns that instead. Otherwise, the output rectangle is returned.
	public Rectangle translate(Rectangle input, Rectangle output) {
		if(input == null)
			return null;
		
		return translate(input.x, input.y, input.width, input.height, output);
	}
	
	public Rectangle translate(int x, int y, int width, int height, Rectangle output) {
		Rectangle translation;
		if(output == null)
			translation = new Rectangle();
		else
			translation = output;
		
		translation.x = x - (int)mLevelViewport.getX();
		translation.y = y - (int)mLevelViewport.getY();
		translation.width = width;
		translation.height = height;
		
		return translation;
	}
	
	public void update() {
		if(mFocusObject == null)
			return;
		
		// Shifting the viewport to center on the object of focus
		Rectangle center = mLevelViewport.getBounds();
		if(mLevelSize.width < center.width) {
			center.x = 0;
		} else {
			center.x = (int)getFocusX() - center.width/2;
			
			if(center.x < 0)
				center.x = 0;
			else if(center.x + center.width > mLevelSize.width)
				center.x = mLevelSize.width - center.width;
		}
		
		if(mLevelSize.height < center.height) {
			center.y = 0;
		} else {
			center.y = (int)getFocusY() - center.height/2;
			
			if(center.y < 0)
				center.y = 0;
			else if(center.y + center.height > mLevelSize.height) 
				center.y = mLevelSize.height - center.height;
		}
		
		mLevelViewport.setRect(center);
	}
	
	private double getFocusX() {
	   double width = 0.0;
	   if(mFocusObject.collidable.bindToImageDimensions) {
	      width = mFocusObject.drawable.image.getWidth();
	   } else {
	      width = mFocusObject.collidable.width;
	   }
	   
	   return mFocusObject.position.x + width/2.0;
	}
	
	private double getFocusY() {
      double height = 0.0;
      if(mFocusObject.collidable.bindToImageDimensions) {
         height = mFocusObject.drawable.image.getHeight();
      } else {
         height = mFocusObject.collidable.height;
      }
      
      return mFocusObject.position.y + height/2.0;
	}
}
