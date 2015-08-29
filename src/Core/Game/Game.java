package Core.Game;

import java.util.HashMap;

import Graphics.CanvasInterface;
import Graphics.GameWindow;

public class Game extends Thread {
	private GameWindow mWindow;

	private GameState mCurrentState;
	private String mNextState;

   private HashMap<String, GameState> mStates;
	
	public Game() {
	   mWindow = new GameWindow("Digestion");
	   setupGameStates();
	   mNextState = "TITLE_STATE";
	}

	private void setupGameStates() {
	   mStates = new HashMap<>();
	}
	
	public void switchToState(String state) {
	   mNextState = state;
	}
	
	public void run() {
		while(true) {
		   if(mNextState != null) 
		      switchToQueuedState();

		   mCurrentState.handleEvents();
		   mCurrentState.update();
		   
		   CanvasInterface canvas = mWindow.getCanvas();
		   mCurrentState.draw(canvas);
		}
	}
	
	private void switchToQueuedState() {
	   mCurrentState.onSwitch();
	   GameState newState = mStates.get(mNextState);
	   newState.beforeSwitch();
	   mCurrentState = newState;
	}
}
