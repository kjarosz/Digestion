package Core.Game;

import java.awt.Dimension;
import java.util.Queue;

import Core.Events.Event;
import Core.Messaging.Message;
import Core.Messaging.Receiver;
import Graphics.CanvasInterface;

public class LevelState implements GameState, Receiver {
   private Game mGame;

   public LevelState(Game game) {
      mGame = game;
      mGame.registerReceiver("Level", this);
   }
   
   @Override
   public String stateName() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void beforeSwitch(Dimension screenSize) {
      // TODO Auto-generated method stub

   }

   @Override
   public void processMessage(Message message) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void handleEvents(Queue<Event> eventQueue) {
      // TODO Auto-generated method stub

   }

   @Override
   public void update() {
      // TODO Auto-generated method stub

   }

   @Override
   public void draw(CanvasInterface canvas) {
      // TODO Auto-generated method stub

   }

   @Override
   public void onSwitch() {
      // TODO Auto-generated method stub

   }

}
