package Core.LevelEditor;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Core.LevelEditor.Components.DrawerToolbar;
import Core.LevelEditor.Components.LevelCanvas;
import Core.LevelEditor.Models.DrawerSettings;
import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.LevelModel;

public final class ContentPanel extends JPanel {
   private EditorSettings  mEditorSettings;
   private LevelModel      mLevelModel;
   private DrawerSettings  mDrawerSettings;
   
	// CONTENT PANEL
   private LevelCanvas mCanvas;
   
	public ContentPanel(EditorSettings editorSettings, LevelModel levelModel) {
	   mEditorSettings = editorSettings;
      mLevelModel = levelModel;
      mDrawerSettings = new DrawerSettings();
      
      createWidgets();
	}
   
   private void createWidgets() {
		setLayout(new BorderLayout());
      
      createCanvas();
      createContentToolbar();
   }
   
   private void createCanvas() {
      mCanvas = new LevelCanvas(mEditorSettings, mLevelModel, mDrawerSettings);
      JScrollPane scroller = new JScrollPane(mCanvas);
      scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      add(scroller, BorderLayout.CENTER);
   }
   
   private void createContentToolbar() {
      DrawerToolbar toolbar = new DrawerToolbar(mLevelModel, mDrawerSettings);
      add(toolbar, BorderLayout.SOUTH);
   }
}
