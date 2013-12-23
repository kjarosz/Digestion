package Entity.Components;

import Entity.EntitySpawner;
import Util.GameTimer;

public class Spawner {
   public EntitySpawner spawner;
   
   public boolean timedSpawns;
   public GameTimer timer;
   public int monstersSpawned;
   public int maximumSpawns;
   
   public Spawner() {
      
   }
}
