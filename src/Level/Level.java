package Level;

import java.awt.image.BufferedImage;

import org.jbox2d.common.Vec2;

public class Level {
   public String name;
	public Vec2 size;
	public Vec2 gravity;
   
   public BufferedImage mBackground;

	public Level() {
      name = "";
      size = new Vec2(25.0f, 18.75f);
      gravity = new Vec2(0.0f, 9.81f);
	}
}
