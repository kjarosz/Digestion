package Entity.Systems;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;

import Entity.EntityComponents;
import Graphics.CanvasInterface;
import Level.EntityContainer;
import Level.Level;
import Util.UnitConverter;

public class DrawingSystem {
	private static BufferedImage gNullImage;
	
	private static boolean loadNullImage(String imagePath) {
		if (imagePath.isEmpty())
			return false;
		
		try {
			gNullImage = ImageIO.read(new File(imagePath));
			
         return gNullImage != null;
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
   
   public static BufferedImage getNullImage() {
      if(gNullImage == null)
         loadNullImage("resources" + File.separator + "NullImage.png");
         
      return gNullImage;
   }
   
   public static void draw(Level level, CanvasInterface canvas) {
      EntityContainer entityContainer = level.entityContainer;
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         int entity = entityContainer.getEntityMask(i);
         if((entity & EntityContainer.ENTITY_DRAWABLE) != 0)
            drawEntity(entityContainer.accessComponents(i), canvas);
      }
   }
   
   private static void drawEntity(EntityComponents components, CanvasInterface canvas) {
      Vec2 m_position = new Vec2(components.body.getPosition());
      m_position.x -= components.m_width/2.0f;
      m_position.y -= components.m_height/2.0f;
      Vec2 px_position = UnitConverter.metersToPixels(m_position);
      canvas.drawImage(components.drawable.image, 
              px_position.x,
              px_position.y,
              0.0f,
              Math.round(UnitConverter.metersToPixels(components.m_width)),
              Math.round(UnitConverter.metersToPixels(components.m_height)));
   }
}
