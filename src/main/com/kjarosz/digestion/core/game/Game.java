package com.kjarosz.digestion.core.game;

import java.util.HashMap;

import com.kjarosz.digestion.core.events.EventPump;
import com.kjarosz.digestion.core.leveleditor.LevelEditor;
import com.kjarosz.digestion.core.menu.LevelMenu;
import com.kjarosz.digestion.core.menu.LoadingScreen;
import com.kjarosz.digestion.core.menu.MainMenu;
import com.kjarosz.digestion.core.menu.PauseMenu;
import com.kjarosz.digestion.core.menu.SinglePlayerMenu;
import com.kjarosz.digestion.core.messaging.Message;
import com.kjarosz.digestion.core.messaging.MessageSystem;
import com.kjarosz.digestion.core.messaging.Receiver;
import com.kjarosz.digestion.graphics.CanvasInterface;
import com.kjarosz.digestion.graphics.GameWindow;
import com.kjarosz.digestion.util.GameTimer;


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
