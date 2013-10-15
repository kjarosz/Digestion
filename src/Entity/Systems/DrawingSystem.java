package Entity.Systems;

import Entity.EntityComponents;
import Graphics.CanvasInterface;
import Level.World;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
   
   public static void draw(World world, CanvasInterface canvas) {
      for(int i = 0; i < World.MAXIMUM_ENTITIES; i++) {
         int entity = world.getEntityMask(i);
         if((entity & World.ENTITY_DRAWABLE) != 0)
            drawEntity(world.accessComponents(i), canvas);
      }
   }
   
   private static void drawEntity(EntityComponents components, CanvasInterface canvas) {
      canvas.drawImage(components.drawable.image, 
              (int)components.position.x,
              (int)components.position.y,
              (int)components.position.z,
              components.drawable.image.getWidth(),
              components.drawable.image.getHeight());
   }
}
