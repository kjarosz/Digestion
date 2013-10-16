package Input;

import java.awt.event.KeyEvent;
import java.util.BitSet;

public class KeyManager {
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
}
