package Level;

import java.awt.image.BufferedImage;

import org.jbox2d.common.Vec2;

public class Level {
   public String name;
	public Vec2 m_size;
	public Vec2 m_gravity;
   
   public BufferedImage mBackground;

	public Level() {
      name = "";
      m_size = new Vec2(25.0f, 18.75f);
      m_gravity = new Vec2(0.0f, 9.81f);
	}
	
	
}
