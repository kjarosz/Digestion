package Entity.Systems;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jbox2d.common.Vec2;

import Entity.EntityComponents;
import Graphics.CanvasInterface;
import Level.EntityContainer;

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
   
   public static void draw(EntityContainer world, CanvasInterface canvas) {
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         int entity = world.getEntityMask(i);
         if((entity & EntityContainer.ENTITY_DRAWABLE) != 0)
            drawEntity(world.accessComponents(i), canvas);
      }
   }
   
   private static void drawEntity(EntityComponents components, CanvasInterface canvas) {
      Vec2 position = components.body.getPosition();
      canvas.drawImage(components.drawable.image, 
              position.x,
              position.y,
              0.0f,
              components.width,
              components.height);
   }
}
