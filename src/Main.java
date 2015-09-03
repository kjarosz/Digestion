

import javax.swing.SwingUtilities;

import Core.Game.Game;

public class Main {
   public static void main(String arguments[]) {
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new Game().start();
         }
      });
   }
}
