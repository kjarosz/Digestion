package com.kjarosz.digestion.input;

import java.awt.event.KeyEvent;
import java.util.BitSet;

public class KeyManager {
   private BitSet mKeyStates;

   public KeyManager() {
      mKeyStates = new BitSet(KeyEvent.KEY_LAST);
   }

   public void clearKeys() {
      mKeyStates.clear();
   }

   public boolean isKeyPressed(int key) {
      return mKeyStates.get(key);
   }

   public void pressKey(int key) {
      mKeyStates.set(key, true);
   }

   public void releaseKey(int key) {
      mKeyStates.set(key, false);
   }
}
