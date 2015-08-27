package Core.Menu;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import Core.Game.Game;
import Menu.MenuScreen;
import Menu.MenuStack;

public class SinglePlayerMenu extends MenuScreen {
	final private Dimension BUTTON_SIZE = new Dimension(150, 25);

	private Game mGame;
	private MenuStack mStack;

	private LevelMenu mLevelMenu;
	private CharacterMenu mCharacterMenu;

	public SinglePlayerMenu(Game game, MenuStack stack) {
		super();

		mGame = game;
		mStack = stack;

		createWidgets();

		mLevelMenu = new LevelMenu(mStack);
		mCharacterMenu = new CharacterMenu(mStack);
	}

	private void createWidgets() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createVerticalGlue());
		createButton("Start Game", e -> startGame());
		add(Box.createVerticalStrut(5));
		createButton("Choose Level", e -> chooseLevel());
		add(Box.createVerticalStrut(5));
		createButton("Choose Character", e -> chooseCharacter());
		add(Box.createVerticalStrut(5));
		createButton("Back", e -> goBack());
		add(Box.createVerticalGlue());
	}

	private void createButton(String name, ActionListener listener) {
		JButton button = new JButton(name);
		button.setMinimumSize(BUTTON_SIZE);
		button.setMaximumSize(BUTTON_SIZE);
		button.setAlignmentX(JButton.CENTER_ALIGNMENT);
		button.addActionListener(listener);
		add(button);
	}

	private void startGame() {
		mStack.popScreen();
		mStack.pushScreen(new LoadingScreen());

		Thread thread = 
				new Thread(
						new Runnable() {
							@Override
							public void run() {
								mGame.startLevel(mLevelMenu.getSelectedLevel());
							}
						});
		thread.start();
	}

	private void chooseLevel() {
		mStack.pushScreen(mLevelMenu);
	}
	
	private void chooseCharacter() {
        mStack.pushScreen(mCharacterMenu);
	}
	
	private void goBack() {
        mStack.popScreen();
	}
}
