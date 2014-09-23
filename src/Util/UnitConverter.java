package Util;

import org.jbox2d.common.Vec2;

public class UnitConverter {
   private final static float PIXELS_PER_METER = 32;
   
   public static float metersToPixels(float meters) {
      return meters * PIXELS_PER_METER;
   }
   
   public static float pixelsToMeters(float pixels) {
      return pixels / PIXELS_PER_METER;
   }
   
   public static Vec2 metersToPixels(Vec2 meters) {
      Vec2 pixels = new Vec2();
      pixels.x = meters.x * PIXELS_PER_METER;
      pixels.y = meters.y * PIXELS_PER_METER;
      return pixels;
   }
   
   public static Vec2 pixelsToMeters(Vec2 pixels) {
      Vec2 meters = new Vec2();
      meters.x = pixels.x / PIXELS_PER_METER;
      meters.y = pixels.y / PIXELS_PER_METER;
      return meters;
   }
}
