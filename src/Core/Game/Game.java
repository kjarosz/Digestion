package Core.Game;

import java.awt.event.KeyEvent;

import javax.swing.SwingUtilities;

import Core.Menu.MainMenu;
import Entity.Systems.AnimationSystem;
import Entity.Systems.ControlSystem;
import Entity.Systems.DrawingSystem;
import Entity.Systems.MotionSystem;
import Graphics.GameCanvas;
import Graphics.GameViewport;
import Graphics.GameWindow;
import Input.KeyManager;
import Level.Level;
import Level.LevelFactory;
import Util.GameTimer;

public class Game {
	private GameWindow mWindow;

	private MainMenu mMainMenu;
	private GameCanvas mGameCanvas;

	private boolean mPause;
	private boolean mQuit;

	private Level mLevel;

	public Game() {
		mMainMenu = new MainMenu(this);
		mGameCanvas = new GameCanvas();
		SwingUtilities.invokeLater(
		      () -> initWindow()
		);
	}

	public void startLevel(String loadingScript) {
	   mLevel = LevelFactory.loadLevel(loadingScript);
		if(mLevel == null) {
			return;
		}

		MotionSystem.resetTimer();

		mPause = false;
		mQuit = false;
		mWindow.switchTo(mGameCanvas);
		mWindow.update();
		execute();
	}

	public void pause() { 
	   mPause = true;
	   mMainMenu.showPauseMenu();
	   switchToMainMenu();
	}

	public void resume() {
	   mPause = false;
	   switchToLevel();
	}

	public void quitToMenu() {
		mQuit = true;
	}
	
	private void initWindow() {
	   mWindow = new GameWindow("Digestion");
	   switchToMainMenu();
	}
	
	private void switchToMainMenu() {
		mWindow.switchTo(mMainMenu.getStack());
		mWindow.update();
	}
	
	private void switchToLevel() {
	   mWindow.switchTo(mGameCanvas);
	   mWindow.update();
	}
	
	private void execute() {
		setFocusObject();

		GameTimer fpsUpdateTimer = new GameTimer();
		fpsUpdateTimer.setTimeInterval(5000);

		int frameCounter = 0;
		boolean escProcessed = false;
		while(!mQuit) {
			if(mPause) {
				if(KeyManager.isKeyPressed(KeyEvent.VK_ESCAPE) && !escProcessed) {
					escProcessed = true;
					resume();
				}
			} else {
				ControlSystem.manipulate(mLevel);
				MotionSystem.move(mLevel);
				AnimationSystem.animate(mLevel);
				DrawingSystem.draw(mLevel, mGameCanvas);

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
		mLevel = null;

		mWindow.setTitle("Digestion");
		switchToMainMenu();
	}

	private void setFocusObject() {
	   GameViewport viewport = mLevel.getFocusViewport(mWindow.getSize());
	   if(viewport != null) {
	      mGameCanvas.setViewport(viewport);
	   }
	}

	private void updateFPS(int frameCount, long timePassed) {
		double fps = (double)frameCount / (timePassed/5000.0);
		mWindow.setTitle("Digestion - " + fps + " FPS");
	}
}
