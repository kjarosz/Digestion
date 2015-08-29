package Core.Menu;

import java.awt.Dimension;

import Core.Game.Game;
import Core.Messaging.Message;
import Core.Messaging.Receiver;
import Menu.MenuScreen;

public class LoadingScreen extends MenuScreen implements Receiver {
   private final String LOADING_SCREEN = "resources/Images/loading_screen.png";
   private long start_time;

   public LoadingScreen(Game game) {
      super(game);
      loadBackground(LOADING_SCREEN);
      mGame.registerReceiver("Level loader", this);
   }

   @Override
   public String stateName() {
      return "LOADING SCREEN";
   }
   
   @Override
   public void beforeSwitch(Dimension screenSize) {
      super.beforeSwitch(screenSize);
      start_time = System.currentTimeMillis();
   }
   
   @Override
   public void processMessage(Message message) {
      
   }

   @Override
   public void update() {
      super.update();
      
      long elapsed_time = System.currentTimeMillis() - start_time;
      if(elapsed_time > 5000) {
         mGame.switchToState("TITLE SCREEN");
      }
   }
}
