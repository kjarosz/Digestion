package Entity.Components;

import Input.KeyMapping;
import java.util.LinkedList;

public class Controllable {
   public LinkedList<KeyMapping> keyMappings;
   
   public Controllable() {
      keyMappings = new LinkedList<>();
   }
}
