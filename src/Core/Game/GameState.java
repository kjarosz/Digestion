package Core.Game;

import Graphics.CanvasInterface;

public interface GameState {
   /* Called when state machine is switching to this state. */
   public void beforeSwitch();
   
   /* All mouse/keyboard/other events happen here. */
   public void handleEvents();
   
   /* Regular tick method. */
   public void update();
   
   /* Self explanatory */
   public void draw(CanvasInterface canvas);
   
   /* Called when state machine is switching away from this state. */
   public void onSwitch();
}
