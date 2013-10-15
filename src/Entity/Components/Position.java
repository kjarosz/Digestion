package Entity.Components;

public class Position {
   public double x;
   public double y;
   public double z;
   
   public Position() {
      x = 0.0;
      y = 0.0;
      z = 0.0;
   }
   
   public Position(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }
}
