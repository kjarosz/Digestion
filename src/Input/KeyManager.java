package Input;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class KeyManager implements KeyEventDispatcher {
   private static boolean mKeyStates[] = new boolean[KeyEvent.KEY_LAST];

   public synchronized static void clearKeys() {
      for(int i = 0; i < mKeyStates.length; i++)
         mKeyStates[i] = false;
   }

   public synchronized static boolean isKeyPressed(int key) {
      return mKeyStates[key];
   }

   public synchronized static void pressKey(int key) {
      mKeyStates[key] = true;
   }

   public synchronized static void releaseKey(int key) {
      mKeyStates[key] = false;
   }

   @Override
   public boolean dispatchKeyEvent(KeyEvent e) {
      int id = e.getID();
      if(id == KeyEvent.KEY_PRESSED) {
         pressKey(e.getKeyCode());
      } else if(id == KeyEvent.KEY_RELEASED) {
         releaseKey(e.getKeyCode());
      }
      return true;
   }
}
