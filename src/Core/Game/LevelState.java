package Core.Game;

import java.awt.Dimension;
import java.util.Queue;

import Core.Events.Event;
import Core.Messaging.Message;
import Core.Messaging.Receiver;
import Core.Messaging.Messages.LevelLoaderTracker;
import Core.Messaging.Messages.LevelLoaderTracker.LoadingStatus;
import Core.Messaging.Messages.StartLevelLoadMessage;
import Entity.Systems.DrawingSystem;
import Graphics.CanvasInterface;
import Level.Level;
import Level.LevelFactory;

public class LevelState implements GameState, Receiver {
   private Game mGame;

   private Level mLevel;

   public LevelState(Game game) {
      mGame = game;
      mGame.registerReceiver("Level", this);

      mLevel = null;
   }
   
   @Override
   public String stateName() {
      return "LEVEL";
   }

   @Override
   public void beforeSwitch(Dimension screenSize) {
      
   }

   @Override
   public void processMessage(Message message) {
      if(message instanceof StartLevelLoadMessage) {
         loadLevel((StartLevelLoadMessage)message);
      }
   }

   private void loadLevel(StartLevelLoadMessage message) {
      new Thread(() -> loadFromScript(message.mLevelName)).start();
   }
   
   private void loadFromScript(String levelName) {
      mLevel = LevelFactory.loadLevel(levelName);
      LevelLoaderTracker tracker;
      if(mLevel == null) {
         tracker = new LevelLoaderTracker(LoadingStatus.FAILED);
      } else {
         tracker = new LevelLoaderTracker(LoadingStatus.FINISHED);
      }
      mGame.publishMessage("Level Loader", tracker);
   }

   @Override
   public void handleEvents(Queue<Event> eventQueue) {
   }

   @Override
   public void update() {
      
   }

   @Override
   public void draw(CanvasInterface canvas) {
      DrawingSystem.draw(mLevel, canvas);
   }

   @Override
   public void onSwitch() {
   }

}
