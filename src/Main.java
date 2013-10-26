

import Core.Game.Game;
import Util.ErrorLog;

public class Main {
	public static void main(String arguments[]) {
      try {
         new Game();
      } catch(Exception e) {
         ErrorLog errorLog = ErrorLog.getInstance();
         errorLog.displayMessageDialog(e.getMessage());
      }
	}
}
