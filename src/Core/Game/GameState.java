package Core.Game;

import java.awt.Dimension;
import java.util.Queue;

import Core.Events.Event;
import Graphics.CanvasInterface;

public interface GameState {
   public String stateName(); 

   /* Called when state machine is switching to this state. */
   public void beforeSwitch(Dimension screenSize);
   
   /* All mouse/keyboard/other events happen here. */
   public void handleEvents(Queue<Event> eventQueue);
   
   /* Regular tick method. */
   public void update();
   
   /* Self explanatory */
   public void draw(CanvasInterface canvas);
   
   /* Called when state machine is switching away from this state. */
   public void onSwitch();
}
