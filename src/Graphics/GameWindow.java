package Graphics;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;

import javax.swing.JFrame;

import Core.Game.GameWindowListener;
import Input.KeyManager;
import Util.ErrorLog;

public class GameWindow {
	private JFrame mWindow;
	private GameCanvas mCanvas;
   
	private GraphicsDevice mFullscreenDevice;
	
	private boolean mFullscreen;
	private int mWidth;
	private int mHeight;
   
	public GameWindow(String title) {
      setupWindow(title);
      mCanvas = new GameCanvas(mWindow);
      loadSettings();
	}
   
   private void setupWindow(String title) {
		mWindow = new JFrame(title);
      mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mWindow.addWindowListener(new GameWindowListener());
		mWindow.setVisible(true);
		new KeyManager(mWindow.getRootPane());
   }
   
   private void loadSettings() {
		mFullscreenDevice = null;
		mFullscreen = false;
      setSize(1024, 768);
   }
   
   public void setTitle(String title) {
      mWindow.setTitle(title);
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
	
	public Dimension getSize() {
	   return new Dimension(mWidth, mHeight);
	}
	
	public void setSize(int width, int height) {
		if(mFullscreen) {
         setFullscreenSize(width, height);
		} else {
         setWindowedSize(width, height);
		}
		mCanvas.setSize(width, height);
	}
	
	public void setSize(Dimension size) {
	   setSize(size.width, size.height);
	}
   
   private void setFullscreenSize(int width, int height) {
      mWidth = width;
      mHeight = height;
      mWindow.setSize(width, height);
   }
   
   private void setWindowedSize(int width, int height) {
      Insets insets = mWindow.getInsets();

      mWidth = width;
      mHeight = height;
      mWindow.setSize(width + insets.left + insets.right, 
            height + insets.top + insets.bottom);
   }
   
   public CanvasInterface getCanvas() {
      return mCanvas;
   }
   
   public void draw() {
      mCanvas.draw();
   }
}
