package Core.Game;

import Input.KeyManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindowListener extends WindowAdapter implements KeyListener {
	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
	
   @Override
	public void keyPressed(KeyEvent e) {
		KeyManager.pressKey(e.getKeyCode());
	}
	
   @Override
	public void keyReleased(KeyEvent e) {
		KeyManager.releaseKey(e.getKeyCode());
	}
	
   @Override
	public void keyTyped(KeyEvent e) {}
}
