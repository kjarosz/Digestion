package Core.Game;

import java.util.HashMap;

import Core.Events.EventPump;
import Core.Menu.MainMenu;
import Core.Menu.SinglePlayerMenu;
import Graphics.CanvasInterface;
import Graphics.GameWindow;

public class Game extends Thread {
	private GameWindow mWindow;
	
	private EventPump mEventPump;

	private GameState mCurrentState;
	private String mNextState;

   private HashMap<String, GameState> mStates;
	
	public Game() {
	   mEventPump = new EventPump();
	   mWindow = new GameWindow("Digestion");
	   mWindow.addEventPump(mEventPump);
	   setupGameStates();
	   mNextState = "TITLE SCREEN";
	}

	private void setupGameStates() {
	   mStates = new HashMap<>();
	   addState(new MainMenu(this));
	   addState(new SinglePlayerMenu(this));
	}

	private void addState(GameState state) {
	   mStates.put(state.stateName(), state);
	}
	
	public void switchToState(String state) {
	   mNextState = state;
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
	
	@Override
	public void run() {
		while(true) {
		   if(mNextState != null) 
		      switchToQueuedState();

		   handleEvents();
		   mCurrentState.update();
		   draw();
		   
		}
	}

	private void handleEvents() {
	   mCurrentState.handleEvents(mEventPump.getQueue());
	}
	
	private void draw() {
	   CanvasInterface canvas = mWindow.getCanvas();
	   mCurrentState.draw(canvas);
	   mWindow.draw();
	}
}
