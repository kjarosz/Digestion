package Input;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.BitSet;

public class KeyManager implements KeyEventDispatcher {
	private static BitSet mKeyStates = new BitSet(KeyEvent.KEY_LAST);
	
	public synchronized static void clearKeys() {
		mKeyStates.clear();
	}
	
	public synchronized static boolean isKeyPressed(int key) {
		return mKeyStates.get(key);
	}
	
	public synchronized static void pressKey(int key) {
		mKeyStates.set(key, true);
	}
	
	public synchronized static void releaseKey(int key) {
		mKeyStates.set(key, false);
	}

   @Override
   public boolean dispatchKeyEvent(KeyEvent e) {
      int id = e.getID();
      if(id == KeyEvent.KEY_PRESSED) {
         pressKey(id);
      } else if(id == KeyEvent.KEY_RELEASED) {
         releaseKey(id);
      }
      return true;
   }
}
