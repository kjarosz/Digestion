package Core.Game;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

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
import Level.EntityContainer;
import Level.Level;
import Level.LevelLoadingScript;
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
   private World mBox2DWorld;
   private EntityContainer mWorld;

	public Game() {
		mWindow = new GameWindow("Digestion");
		mGameCanvas = new GameCanvas();
		mGameCanvas.invertYAxis(true);
      mEntityFactory = new EntityFactory();
      mLevel = new Level();
      mWorld = new EntityContainer();
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
      mBox2DWorld = new World(mLevel.m_gravity);
      
      MotionSystem.resetTimer();
      
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
      MotionSystem.resetTimer();
      mWindow.switchTo(mGameCanvas);
   }
   
   public void quitToMenu() {
      mQuit = true;
   }
   
	private void execute() {
      mPaused = false;
		
      setFocusObject();
      
      GameTimer fpsUpdateTimer = new GameTimer();
      fpsUpdateTimer.setTimeInterval(5000);
      
      int frameCounter = 0;
      boolean escProcessed = false;
		while(!mQuit) {
			if(mPaused) {
				if(KeyManager.isKeyPressed(KeyEvent.VK_ESCAPE) && !escProcessed) {
               escProcessed = true;
               resume();
            }
			} else {
            ControlSystem.manipulate(mWorld);
            MotionSystem.move(mBox2DWorld, mWorld);
            AnimationSystem.animate(mWorld);
            DrawingSystem.draw(mWorld, mGameCanvas);
            
            mWindow.update();
            
            if(KeyManager.isKeyPressed(KeyEvent.VK_ESCAPE) && !escProcessed) {
               escProcessed = true;
               pause();
            }
			}
         
         if(!KeyManager.isKeyPressed(KeyEvent.VK_ESCAPE) && escProcessed)
            escProcessed = false;
         
         frameCounter++;
         if(fpsUpdateTimer.hasTimeIntervalPassed()) {
            updateFPS(frameCounter, fpsUpdateTimer.getElapsedTime());
            fpsUpdateTimer.reset();
            frameCounter = 0;
         }
		}
		mWindow.setTitle("Digestion");
      
      mWindow.switchTo(mMenuStack);
	}
	
	private void setFocusObject() {
	   for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
	      int entityMask = mWorld.getEntityMask(i);
	      if((entityMask & EntityContainer.ENTITY_FOCUSABLE) != 0) {
	         EntityComponents components = mWorld.accessComponents(i);
	         
	         GameViewport viewport = new GameViewport(mLevel.m_size, new Vec2(mWindow.getWidth(), mWindow.getHeight()));
	         viewport.setFocusObject(components);
	         
	         mGameCanvas.setViewport(viewport);
	         break;
	      }
	   }
	}
	
	private void updateFPS(int frameCount, long timePassed) {
	   double fps = (double)frameCount / (timePassed/5000.0);
	   mWindow.setTitle("Digestion - " + fps + " FPS");
	}
}
