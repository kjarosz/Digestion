package Level;

import java.awt.image.BufferedImage;

import org.jbox2d.common.Vec2;

import Util.Size;

public class Level {
   public String name;
	public Size size;
	public Vec2 gravity;
   
   public BufferedImage mBackground;

	public Level() {
      name = "";
      size = new Size(800, 600);
      gravity = new Vec2(0.0f, 9.81f);
	}
}
