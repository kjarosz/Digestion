

import javax.swing.SwingUtilities;

import Core.Game.Game;
import Util.ErrorLog;

public class Main {
	public static void main(String arguments[]) {
	   SwingUtilities.invokeLater(new Runnable() {
	      @Override
	      public void run() {
	         try {
	            new Game();
	         } catch(Exception e) {
	            ErrorLog errorLog = ErrorLog.getInstance();
	            errorLog.displayMessageDialog(e.getMessage());
	         }
	      }
	   });
	}
}
