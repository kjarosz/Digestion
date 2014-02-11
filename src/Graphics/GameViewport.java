package Graphics;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.jbox2d.common.Vec2;

import Entity.EntityComponents;

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
	private Vec2 mLevelSize;
	
	private EntityComponents mFocusObject;
	
	private Rectangle2D mLevelViewport;
	
	public GameViewport() {
		mLevelSize = new Vec2(0, 0);
		
		mFocusObject = null;
		
		mLevelViewport = new Rectangle2D.Double();
	}
	
	public boolean initialize(float screenWidth, float screenHeight, float levelWidth, float levelHeight) {
		mLevelSize.x = levelWidth;
		mLevelSize.y = levelHeight;
		
		mLevelViewport.setRect(0, 0, screenWidth, screenHeight);
		
		return true;
	}
	
	public void setFocusObject(EntityComponents focusObject) {
		mFocusObject = focusObject;
	}
			
	public void setLevelSize(Vec2 levelSize) {
		if(levelSize.x <= 0 || levelSize.y <= 0)
			return;
		
		mLevelSize = levelSize;
	}
	
	public void setWindowSize(int width, int height) {
	   mLevelViewport.setRect(0, 0, width, height);
	}
	
	public boolean contains(double x, double y, double width, double height) {
		return mLevelViewport.intersects(x, y, width, height);
	}
	
	public boolean contains(Rectangle2D object) {
		return mLevelViewport.intersects(object);
	}
	
	// If output is null, the function creates a new rectangle and
	// returns that instead. Otherwise, the output rectangle is returned.
	public Rectangle2D translate(Rectangle2D input, Rectangle2D output) {
		if(input == null)
			return null;
		
		return translate(input.getX(), input.getY(), input.getWidth(), input.getHeight(), output);
	}
	
	public Rectangle2D translate(double x, double y, double width, double height, Rectangle2D output) {
		Rectangle2D translation;
		if(output == null)
			translation = new Rectangle();
		else
			translation = output;
		
		translation.setRect(mLevelViewport.getX(), mLevelViewport.getY(), width, height);
		
		return translation;
	}
	
	public void update() {
		if(mFocusObject == null)
			return;
		
		Vec2 position = mFocusObject.body.getPosition();
		
		// Shifting the viewport to center on the object of focus
      Rectangle2D center = mLevelViewport.getBounds2D();
		double centerWidth = center.getWidth();
		double centerHeight = center.getHeight();
		
		double centerX = centerDimension(mLevelSize.x, centerWidth, position.x);
		double centerY = centerDimension(mLevelSize.y, centerHeight, position.y);
		
		mLevelViewport.setRect(centerX, centerY, centerWidth, centerHeight);
	}
	
	private double centerDimension(double dimensionRange, double centerRange, double position) {
      double center = 0.0;
      if(dimensionRange < centerRange) {
         center = 0;
      } else {
         center = position - centerRange/2;
         
         if(center < 0)
            center = 0;
         else if(center + centerRange > dimensionRange)
            center = dimensionRange - centerRange;
      }
      return center;
	}
}
