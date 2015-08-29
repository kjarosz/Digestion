package Core.Game;

import java.util.HashMap;

import Core.Menu.MainMenu;
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
	   mNextState = "TITLE SCREEN";
	}

	private void setupGameStates() {
	   mStates = new HashMap<>();
	   addState(new MainMenu(this));
	}

	private void addState(GameState state) {
	   mStates.put(state.stateName(), state);
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
		   mWindow.draw();
		   
		}
	}
	
	private void switchToQueuedState() {
	   if(mCurrentState != null) {
	      mCurrentState.onSwitch();
	   }
	   GameState newState = mStates.get(mNextState);
	   newState.beforeSwitch(mWindow.getSize());
	   mCurrentState = newState;
	   mNextState = null;
	}
}
