package Graphics;

import Core.Game.GameWindowListener;
import Util.ErrorLog;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import javax.swing.JFrame;

public class GameWindow {
	private JFrame mWindow;
   
   private GameWindowListener mWindowListener;
   private Component mDisplayedItem;
	
	private GraphicsDevice mFullscreenDevice;
	
	private boolean mFullscreen;
	private int mWidth;
	private int mHeight;
   
	public GameWindow(String title) {
      setupWindow(title);
      loadSettings();
	}
   
   private void setupWindow(String title) {
		mWindow = new JFrame(title);
      mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mWindowListener = new GameWindowListener();
		mWindow.addWindowListener(mWindowListener);
      mWindow.addKeyListener(mWindowListener);
		mWindow.setVisible(true);
   }
   
   private void loadSettings() {
		mFullscreenDevice = null;
		mFullscreen = false;
      setSize(800, 600);
   }
	
	public void switchFullscreen() {
      mFullscreen = !mFullscreen;
      
		if(mFullscreen) {
         switchToFullscreen();
		} else {
         switchToWindow();
		}
	}
   
   private void switchToFullscreen() {
      GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

      mFullscreenDevice = env.getDefaultScreenDevice();
      if(mFullscreenDevice.isFullScreenSupported()) {
         mFullscreenDevice.setFullScreenWindow(mWindow);
         mFullscreen = true;
      }

      if(!mFullscreen) {
         ErrorLog errorLog = ErrorLog.getInstance();
         errorLog.displayMessageDialog("It seems there are no screens that support fullscreen mode.\nRemaining in Windowed mode");
      }
   }
	
   private void switchToWindow() {
      mFullscreenDevice.setFullScreenWindow(null);
      mFullscreenDevice = null;

      mFullscreen = false;
   }
   
	public boolean isFullscreen() {
		return mFullscreen;
	}
	
	public int getWidth() {
		return mWidth;
	}
	
	public int getHeight() {
		return mHeight;
	}
	
	public void setSize(int width, int height) {
		if(mFullscreen) {
         setFullscreenSize(width, height);
		} else {
         setWindowedSize(width, height);
		}
	}
   
   private void setFullscreenSize(int width, int height) {
      mWidth = width;
      mHeight = height;
      mWindow.setSize(width, height);
   }
   
   private void setWindowedSize(int width, int height) {
      Insets insets = mWindow.getInsets();

      mWidth = width + insets.left + insets.right;
      mHeight = height + insets.top + insets.bottom;
      mWindow.setSize(width, height);
   }
   
   public void switchTo(Component component) {
      if(mDisplayedItem != null)
         mWindow.remove(mDisplayedItem);
      mWindow.add(component);
      mDisplayedItem = component;
      update();
   }
   
   public void update() {
      mWindow.paintAll(mWindow.getGraphics());
   }
}
