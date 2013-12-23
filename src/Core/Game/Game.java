package Core.Game;

import java.awt.Color;
import java.awt.event.KeyEvent;

import Core.Menu.MainMenu;
import Core.Menu.PauseMenu;
import Entity.EntityComponents;
import Entity.EntityFactory;
import Entity.Systems.AnimationSystem;
import Entity.Systems.ControlSystem;
import Entity.Systems.DrawingSystem;
import Entity.Systems.MotionSystem;
import Graphics.GameCanvas;
import Graphics.GameViewport;
import Graphics.GameWindow;
import Input.KeyManager;
import Level.Level;
import Level.LevelLoadingScript;
import Level.World;
import Menu.MenuStack;
import Util.GameTimer;

public class Game {
   private GameWindow mWindow;
   
   private MenuStack mMenuStack;
   private GameCanvas mGameCanvas;
	
   private boolean mPaused;
   private boolean mQuit;

   private EntityFactory mEntityFactory;
   private Level mLevel;
   private World mWorld;
	
	private GameTimer mFrameTimer;

	public Game() {
		mWindow = new GameWindow("Digestion");
		mGameCanvas = new GameCanvas();
      mEntityFactory = new EntityFactory();
		mFrameTimer = new GameTimer();
		mFrameTimer.setTimeInterval( 1000/30 );
      mLevel = new Level();
      mWorld = new World();
		mPaused = true;
      setupMenuStack();
	}
   
   private void setupMenuStack() {
      mMenuStack = new MenuStack();
      mWindow.switchTo(mMenuStack);
      MainMenu mainMenu = new MainMenu(this, mMenuStack);
      mainMenu.setBackground(Color.BLACK);
      mMenuStack.pushScreen(mainMenu);
   }
	
   public void startLevel(LevelLoadingScript loadingScript) {
      loadingScript.loadLevel(mLevel);
      loadingScript.createEntities(mEntityFactory, mWorld);

      mQuit = false;
      mMenuStack.popScreen(); // Get rid of loading screen.
      mWindow.switchTo(mGameCanvas);
      mWindow.update();
      execute();
   }
   
	public void pause() { 
      mPaused = true;
      PauseMenu pauseMenu = new PauseMenu(this, mMenuStack);
      mMenuStack.pushScreen(pauseMenu);
      mWindow.switchTo(mMenuStack);
   }
   
   public void resume() {
      mPaused = false;
      mWorld.resetTimers();
      mFrameTimer.reset();
      mWindow.switchTo(mGameCanvas);
   }
   
   public void quitToMenu() {
      mQuit = true;
   }
   
	private void execute() {
      mPaused = false;
      mFrameTimer.reset();
		
      setFocusObject();
      
      boolean escProcessed = false;
		while(!mQuit) {
			if(mPaused) {
				if(KeyManager.isKeyPressed(KeyEvent.VK_ESCAPE) && !escProcessed) {
               escProcessed = true;
               resume();
            }
			} else {
            ControlSystem.manipulate(mWorld);
            MotionSystem.move(mWorld);
            AnimationSystem.animate(mWorld);
            
				if(mFrameTimer.hasTimeIntervalPassed()) {
               DrawingSystem.draw(mWorld, mGameCanvas);	
               mWindow.update();
					mFrameTimer.reset();
				}
            
            if(KeyManager.isKeyPressed(KeyEvent.VK_ESCAPE) && !escProcessed) {
               escProcessed = true;
               pause();
            }
			}
         
         if(!KeyManager.isKeyPressed(KeyEvent.VK_ESCAPE) && escProcessed)
            escProcessed = false;
		}
      
      mWindow.switchTo(mMenuStack);
	}
	
	private void setFocusObject() {
	   for(int i = 0; i < World.MAXIMUM_ENTITIES; i++) {
	      int entityMask = mWorld.getEntityMask(i);
	      if((entityMask & World.ENTITY_FOCUSABLE) != 0) {
	         EntityComponents components = mWorld.accessComponents(i);
	         
	         GameViewport viewport = new GameViewport();
	         viewport.initialize(mWindow.getWidth(), mWindow.getHeight(), 
	               mLevel.size.width, mLevel.size.height);
	         viewport.setFocusObject(components);
	         
	         mGameCanvas.setViewport(viewport);
	         break;
	      }
	   }
	}
}
