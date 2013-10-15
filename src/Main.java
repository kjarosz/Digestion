

import Core.Game.Game;
import Util.ErrorLog;
//import Sound.SoundSystem;

public class Main {
	public static void main(String arguments[]) {
      try {
         Game game = new Game();
      } catch(Exception e) {
         ErrorLog errorLog = ErrorLog.getInstance();
         errorLog.displayMessageDialog(e.getMessage());
      }
	}
}
