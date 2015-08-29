package Core.Menu;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import Core.Events.Callback;
import Core.Game.Game;
import Core.Game.GameState;
import Graphics.CanvasInterface;
import Menu.CenteredLayout;
import Menu.Widgets.Button;
import Menu.Widgets.UIElement;

public class MainMenu implements GameState {
	private final String MAIN_MENU_BACKGROUND = "resources/Images/Title.png";
	private final String SINGLE_PLAYER        = "resources/Images/single_player_button.png";
	private final String LEVEL_EDITOR         = "resources/Images/level_editor_button.png";
	private final String EXIT                 = "resources/Images/exit_button.png";
	
	private final Dimension BUTTON_SIZE = new Dimension(600, 50);

	private Game mGame;
	private BufferedImage mBackground;
	
	private CenteredLayout mLayout;
	private LinkedList<UIElement> mElements; 
	
	private Dimension mScreenSize;

	public MainMenu(Game game) {
	   mGame = game;
		loadBackground();
		createWidgets();
	}

	private void loadBackground() {
		try {
			File bckgrFile = new File(MAIN_MENU_BACKGROUND);
			mBackground = ImageIO.read(bckgrFile);
		} catch(IOException ex) {
		   // There's nothing we can really do about this.
		   // And it's already going to be visible as it is.
		}
	}
	
	private void createWidgets() {
	   mLayout = new CenteredLayout(5);
	   mElements = new LinkedList<>();
	   createButton(SINGLE_PLAYER, () -> System.out.println("Single Player"));
	   createButton(LEVEL_EDITOR, () -> System.out.println("Level Editor"));
	   createButton(EXIT, () -> System.out.println("Exit"));
	}
	
	private void createButton(String buttonImage, Callback callback) {
	   Button button = new Button(buttonImage);
	   button.setPreferredSize(BUTTON_SIZE);
	   button.setActionCallback(callback);
	   mLayout.addComponent(button);
	   mElements.add(button);
	}

	public String stateName() {
	   return "TITLE SCREEN";
	}
	
   @Override
   public void beforeSwitch(Dimension screenSize) {
      mScreenSize = screenSize;
      mLayout.resizeParent(mScreenSize);
   }

   @Override
   public void handleEvents() { }

   @Override
   public void update() {}

   @Override
   public void draw(CanvasInterface canvas) {
      canvas.drawImage(mBackground, 0.0f, 0.0f, 0.0f, 
            (float)mScreenSize.width, 
            (float)mScreenSize.height);
      
      for(UIElement e: mElements) {
         e.draw(canvas);
      }
   }

   @Override
   public void onSwitch() { }
}
