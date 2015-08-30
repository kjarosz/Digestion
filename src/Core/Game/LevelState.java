package Core.Game;

import java.awt.Dimension;
import java.util.Queue;

import Core.Events.Event;
import Core.Events.KeyEvent;
import Core.Events.KeyEvent.KeyAction;
import Core.Events.ScreenEvent;
import Core.Events.ScreenEvent.ScreenAction;
import Core.Messaging.Message;
import Core.Messaging.Receiver;
import Core.Messaging.Messages.LevelLoaderTracker;
import Core.Messaging.Messages.LevelLoaderTracker.LoadingStatus;
import Core.Messaging.Messages.StartLevelLoadMessage;
import Entity.Systems.ControlSystem;
import Entity.Systems.DrawingSystem;
import Entity.Systems.MotionSystem;
import Graphics.CanvasInterface;
import Graphics.GameViewport;
import Level.Level;
import Level.LevelFactory;

public class LevelState implements GameState, Receiver {
   private Game mGame;
   private Dimension mScreenSize;

   private Level mLevel;

   private ControlSystem mControlSystem;
   private MotionSystem mMotionSystem;
   private DrawingSystem mDrawingSystem;

   public LevelState(Game game) {
      mGame = game;
      mGame.registerReceiver("Level", this);

      mLevel = null;
      mControlSystem = new ControlSystem();
      mMotionSystem = new MotionSystem();
      mDrawingSystem = new DrawingSystem();
   }

   @Override
   public String stateName() {
      return "LEVEL";
   }

   @Override
   public void beforeSwitch(Dimension screenSize) {
      mScreenSize = screenSize;
      focusOnObject();
      restartSystems();
   }

   private void focusOnObject() {
      GameViewport viewport = mLevel.getFocusViewport(mScreenSize);
      mDrawingSystem.setViewport(viewport);
   }

   private void restartSystems() {
      mControlSystem.clearKeys();
      mMotionSystem.resetTimer();
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
   public void update() {
      mControlSystem.manipulate(mLevel);
      mMotionSystem.move(mLevel);
   }

   @Override
   public void draw(CanvasInterface canvas) {
      mDrawingSystem.draw(mLevel, canvas);
   }

   @Override
   public void onSwitch() { }

   @Override
   public void handleEvents(Queue<Event> eventQueue) {
      while(!eventQueue.isEmpty()) {
         Event event = eventQueue.poll();
         processEvent(event);
      }
   }

   private void processEvent(Event event) {
      if(event instanceof KeyEvent) {
         processKeyEvent((KeyEvent)event);
      } else if(event instanceof ScreenEvent) {
         processScreenEvent((ScreenEvent)event);
      }
   }

   private void processKeyEvent(KeyEvent event) {
      if(event.mKeyAction == KeyAction.PRESSED &&
            event.mKeyCode == java.awt.event.KeyEvent.VK_ESCAPE) {
         mGame.switchToState("PAUSE MENU");
      } else {
         mControlSystem.processKeyEvent(event);
      }
   }

   private void processScreenEvent(ScreenEvent event) {
      if(event.mAction == ScreenAction.FOCUS_LOST) {
         mGame.switchToState("PAUSE MENU");
      }
   }
}
