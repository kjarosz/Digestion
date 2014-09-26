package Graphics;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.jbox2d.common.Vec2;

import Util.UnitConverter;

import Entity.EntityComponents;

public class GameViewport {
	private Vec2 mLevelSize;
	
	private EntityComponents mFocusObject;
	
	private Rectangle2D mLevelViewport;
	
	/**
	 * Creates a viewport class that translates objects within the game
	 * world so that the focus object is always visible.
	 * 
	 * @param levelSize Size of the level that contains the game world. 
	 *                  This must be in the same units as the focus object.
	 * @param screenSize Size of the window canvas where things are drawn.
	 *                  This must be in the same units as the focus object.
	 */
	
	public GameViewport(Vec2 levelSize, Vec2 screenSize) {
		mLevelSize = new Vec2(levelSize);

      mFocusObject = null;
		
		mLevelViewport = new Rectangle2D.Double(0, 0, screenSize.x, screenSize.y);
	}
	
	public void setFocusObject(EntityComponents focusObject) {
		mFocusObject = focusObject;
	}
			
	public void setLevelSize(Vec2 levelSize) {
		if(levelSize.x <= 0 || levelSize.y <= 0)
			return;
		
		mLevelSize = levelSize;
	}
	
	public void setWindowSize(float width, float height) {
	   mLevelViewport.setRect(0, 0, width, height);
	}
	
	public boolean contains(double x, double y, double width, double height) {
		return mLevelViewport.intersects(x, y, width, height);
	}
	
	public boolean contains(Rectangle2D object) {
		return mLevelViewport.intersects(object);
	}
	
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
		output.setRect(translation);
		
		return translation;
	}
	
	public void update() {
		if(mFocusObject == null)
			return;
		
		Vec2 m_position = mFocusObject.body.getPosition();
		Vec2 px_position = UnitConverter.metersToPixels(m_position);
		
		// Shifting the viewport to center on the object of focus
      Rectangle2D center = mLevelViewport.getBounds2D();
		double centerWidth = center.getWidth();
		double centerHeight = center.getHeight();
		
		double centerX = centerDimension(mLevelSize.x, centerWidth, px_position.x);
		double centerY = centerDimension(mLevelSize.y, centerHeight, px_position.y);
		
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
