package Entity.Systems;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;

import Entity.EntityComponents;
import Graphics.CanvasInterface;
import Graphics.GameViewport;
import Level.EntityContainer;
import Level.Level;
import Util.UnitConverter;

public class DrawingSystem {
	private static BufferedImage sNullImage;
	
	private static boolean loadNullImage(String imagePath) {
		if (imagePath.isEmpty())
			return false;
		
		try {
			sNullImage = ImageIO.read(new File(imagePath));
			
         return sNullImage != null;
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
   
   public static BufferedImage getNullImage() {
      if(sNullImage == null) {
         loadNullImage("resources" + File.separator + "NullImage.png");
      }

      return sNullImage;
   }
   
   private GameViewport mViewport;
   
	public DrawingSystem() {
	   Vec2 dummyVec = new Vec2();
	   mViewport = new GameViewport(dummyVec, dummyVec);
	}
	
	public void setViewport(GameViewport viewport) {
	   mViewport = viewport;
	}
	
	public void setScreenSize(Dimension screenSize) {
	   mViewport.setWindowSize(screenSize.width, screenSize.height);
	}

   public void draw(Level level, CanvasInterface canvas) {
      mViewport.update();

      EntityContainer entityContainer = level.entityContainer;
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         int entity = entityContainer.getEntityMask(i);
         if((entity & EntityContainer.ENTITY_DRAWABLE) != 0)
            drawEntity(entityContainer.accessComponents(i), canvas);
      }
   }
   
   private void drawEntity(EntityComponents components, CanvasInterface canvas) {
      Vec2 position = getEntityPosition(components);
      Vec2 size = getEntitySize(components);
      if(entityIsInViewport(position, size)) {
         Vec2 screenCoords = translateToScreen(position);
         canvas.drawImage(components.drawable.image, 
               screenCoords.x,
               screenCoords.y,
               0.0f,
               size.x,
               size.y);
      }
   }
   
   private Vec2 getEntityPosition(EntityComponents components) {
      Vec2 m_position = new Vec2(components.body.getPosition());
      m_position.x -= components.m_width/2.0f;
      m_position.y -= components.m_height/2.0f;
      return UnitConverter.metersToPixels(m_position);
   }
   
   private Vec2 getEntitySize(EntityComponents components) {
      Vec2 m_size = new Vec2(components.m_width, components.m_height);
      return UnitConverter.metersToPixels(m_size);
   }
   
   private boolean entityIsInViewport(Vec2 position, Vec2 size) {
      if(mViewport != null && !mViewport.contains(position.x, position.y, size.x, size.y)) {
         return false;
      }
      return true;
   }
   
   private Vec2 translateToScreen(Vec2 position) {
      if(mViewport != null) {
         return mViewport.translate(position);
      }
      return position;
   }
}
