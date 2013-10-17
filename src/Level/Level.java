package Level;

import Util.Size;
import java.awt.image.BufferedImage;

public class Level {
   private String pwd;
	
   public String name;
	public Size size;
	public double gravity;
   
   public BufferedImage mBackground;

	public Level() {
      name = "";
      size = new Size(800, 600);
      gravity = 9.8;
	}
}
