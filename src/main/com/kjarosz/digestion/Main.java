package com.kjarosz.digestion;


import javax.swing.SwingUtilities;

import com.kjarosz.digestion.core.game.Game;

public class Main {
   public static void main(String arguments[]) {
      SwingUtilities.invokeLater(() -> {
          new Game().start();
      });
   }
   

}
