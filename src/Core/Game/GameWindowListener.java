package Core.Game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindowListener extends WindowAdapter {
   
   
	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
}
