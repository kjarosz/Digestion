package Core.Game;

import Core.Menu.MainMenu;
import Core.Menu.PauseMenu;
import Entity.EntityFactory;
import Entity.Systems.AnimationSystem;
import Entity.Systems.ControlSystem;
import Entity.Systems.DrawingSystem;
import Entity.Systems.MotionSystem;
import Graphics.GameCanvas;
import Graphics.GameWindow;
import Input.KeyManager;
import Level.Level;
import Level.LevelLoadingScript;
import Level.World;
import Menu.MenuStack;
import Util.GameTimer;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class Game {
	private GameWindow mWindow;
   
   private MenuStack mMenuStack;
	
	private boolean mPaused;
   private boolean mQuit;

   private EntityFactory mEntityFactory;
   private Level mLevel;
   private World mWorld;
	
	private GameTimer mFrameTimer;

	public Game() {
		mWindow = new GameWindow("Digestion");
      mEntityFactory = new EntityFactory();
		mFrameTimer = new GameTimer();
		mFrameTimer.setTimeInterval( (1/30)*1000 /* 33 millisecond */ );
      mLevel = new Level();
      mWorld = new World();
		mPaused = true;
      setupMenuStack();
	}
   
   private void setupMenuStack() {
      mMenuStack = new MenuStack();
      mWindow.switchTo(mMenuStack);
      MainMenu mainMenu = new MainMenu(this, mMenuStack);
      mainMenu.addKeyListener(new GameWindowListener());
      mainMenu.setBackground(Color.BLACK);
      mMenuStack.pushScreen(mainMenu);
   }
	
   public void startLevel(LevelLoadingScript loadingScript) {
      loadingScript.loadLevel(mLevel);
      loadingScript.createEntities(mEntityFactory, mWorld);
      
      mQuit = false;
      
      GameCanvas canvas = new GameCanvas();
      canvas.addKeyListener(new GameWindowListener());
      mWindow.switchTo(canvas);
      execute(canvas);
   }
   
	public void pause() { 
      mPaused = true;
      PauseMenu pauseMenu = new PauseMenu(this, mMenuStack);
      pauseMenu.display();
   }
   
   public void resume() {
      mPaused = false;
   }
   
	public void unpause() { 
      if(mLevel == null)
         return;
      
      mPaused = false;
      mFrameTimer.reset();
   }
   
	private void execute(GameCanvas canvas) {
		unpause();
		
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
               DrawingSystem.draw(mWorld, canvas);					
               canvas.showCanvas();
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
      
      mPaused = true;
      
      mWindow.switchTo(mMenuStack);
	}
}
