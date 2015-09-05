package Core.Game;

import java.util.HashMap;

import Core.Events.EventPump;
import Core.LevelEditor.LevelEditor;
import Core.Menu.LevelMenu;
import Core.Menu.LoadingScreen;
import Core.Menu.MainMenu;
import Core.Menu.PauseMenu;
import Core.Menu.SinglePlayerMenu;
import Core.Messaging.Message;
import Core.Messaging.MessageSystem;
import Core.Messaging.Receiver;
import Graphics.CanvasInterface;
import Graphics.GameWindow;
import Util.GameTimer;

public class Game extends Thread {
   private GameWindow mWindow;

   private MessageSystem mMessageSystem;
   private EventPump mEventPump;

   private GameState mCurrentState;
   private String mNextState;

   private HashMap<String, GameState> mStates;

   public Game() {
      mMessageSystem = new MessageSystem();
      mEventPump = new EventPump();
      setupGameStates();
      mWindow = new GameWindow("Digestion");
      mWindow.addEventPump(mEventPump);
      setupLevelEditor();
   }

   private void setupGameStates() {
      mStates = new HashMap<>();
      addState(new MainMenu(this));
      addState(new SinglePlayerMenu(this));
      addState(new LevelMenu(this));
      addState(new LoadingScreen(this));
      addState(new LevelState(this));
      addState(new PauseMenu(this));
      switchToState("TITLE SCREEN");
   }

   private void setupLevelEditor() {
      LevelEditor levelEditor = new LevelEditor(this);
      addState(new LevelEditor(this));
      mWindow.addCard(levelEditor, "LEVEL EDITOR");
   }

   private void addState(GameState state) {
      mStates.put(state.stateName(), state);
   }

   public void switchToState(String state) {
      mNextState = state;
   }

   public void switchCard(String cardName) {
      mWindow.switchCard(cardName);
   }

   private void switchToQueuedState() {
      if(mCurrentState != null) {
         mCurrentState.onSwitch();
      }
      GameState newState = mStates.get(mNextState);
      newState.beforeSwitch(mWindow.getCanvasSize());
      mCurrentState = newState;
      mNextState = null;
   }

   public void registerReceiver(String channel, Receiver receiver) {
      mMessageSystem.registerReceiver(channel, receiver);
   }

   public void publishMessage(String channel, Message message) {
      mMessageSystem.queueMessage(channel, message);
   }

   @Override
   public void run() {
      while(true) {
         if(mNextState != null) 
            switchToQueuedState();

         mMessageSystem.dispatchMessages();
         handleEvents();
         mCurrentState.update();
         draw();
      }
   }

   private void handleEvents() {
      mCurrentState.handleEvents(mEventPump.getQueue());
   }

   private double lastFrame = GameTimer.nanoToSeconds(System.nanoTime()) - 1/60.0;
   private void draw() {
      double now = GameTimer.nanoToSeconds(System.nanoTime());
      if (now - lastFrame > 1/60.0) {
         CanvasInterface canvas = mWindow.getCanvas();
         mCurrentState.draw(canvas);
         mWindow.draw();
         lastFrame = now;
      }
   }
}
