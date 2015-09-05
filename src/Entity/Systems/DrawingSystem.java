package Entity.Systems;

import static java.lang.Math.min;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entity.EntityComponents;
import Entity.Components.Drawable;
import Graphics.CanvasInterface;
import Graphics.GameViewport;
import Level.EntityContainer;
import Level.Level;
import Util.Vector2D;

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
	   Vector2D dummyVec = new Vector2D();
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
      Vector2D position = components.body.position;
      Vector2D size = components.body.size;
      if(entityIsInViewport(position, size)) {
         Vector2D screenCoords = translateToScreen(position);
         if(components.drawable.tiled) {
            drawTiled(canvas, components.drawable, screenCoords, size);
         } else {
            drawImage(canvas, components.drawable, screenCoords, size);
         }
      }
   }
   
   private boolean entityIsInViewport(Vector2D position, Vector2D size) {
      if(!mViewport.contains(position.x, position.y, size.x, size.y)) {
         return false;
      }
      return true;
   }
   
   private Vector2D translateToScreen(Vector2D position) {
      return mViewport.translate(position);
   }
   
   private void drawTiled(CanvasInterface canvas, Drawable drawable,
         Vector2D position, Vector2D size) {
      int imgWidth = drawable.image.getWidth();
      int imgHeight = drawable.image.getHeight();
      for(int x = (int)position.x; x < position.x + size.x; x += imgWidth) {
         for(int y = (int)position.y; y < position.y + size.y; y += imgHeight) {
            int width = (int)min(imgWidth, (position.x + size.x) - x);
            int height = (int)min(imgWidth, (position.y + size.y) - y);
            drawImage(canvas, drawable, x, y, 0, width, height);
         }
      }
   }
   
   private void drawImage(CanvasInterface canvas, Drawable drawable, Vector2D pos, Vector2D size) {
      drawImage(canvas, drawable, pos.x, pos.y, 0, size.x, size.y);
   }
   
   private void drawImage(CanvasInterface canvas, Drawable drawable, double x, double y, double z, double width, double height) {
      int reflector = drawable.flipped ? -1 : 1;
      int reflectionOffset = drawable.flipped ? 1 : 0;
      canvas.drawImage(drawable.image,
            x + reflectionOffset * width, 
            y, 
            z, 
            reflector * width, 
            height);
   }
}

