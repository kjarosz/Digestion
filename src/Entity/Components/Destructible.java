package Entity.Components;

public class Destructible {
   public int health;
   public int maxHealth;
   
   public boolean selfReviving;
   public long revivalInterval;
   public long timeOfDeath;
   
   public Destructible() {
      health = 0;
      maxHealth = 0;
      
      selfReviving = false;
      revivalInterval = 0;
      timeOfDeath = 0;
   }
}
