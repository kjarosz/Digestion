package Core.LevelEditor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Core.LevelEditor.Components.DrawerToolbar;
import Core.LevelEditor.Components.EntityComposer;
import Core.LevelEditor.Components.LevelCanvas;
import Core.LevelEditor.Models.EntityModelList;
import Core.LevelEditor.Models.LevelModel;
import Core.LevelEditor.Settings.DrawerSettings;
import Core.LevelEditor.Settings.EditorSettings;
import Core.LevelEditor.Settings.EditorSettings.EditorMode;

public final class ContentPanel extends JPanel {
   private final static String ENTITY_EDITOR_CARD = "ENTITY EDITOR";
   private final static String LEVEL_EDITOR_CARD = "LEVEL EDITOR";
   
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
      
      mEditorSettings.addPropertyChangeListener(createEditorSettingsListener());
	}
   
   private void createWidgets() {
		setLayout(new CardLayout());
		createEntityEditor();
      createLevelEditor();
   }
   
   private void createEntityEditor() {
      EntityComposer entityComposer = new EntityComposer(mEditorSettings);
      add(entityComposer, ENTITY_EDITOR_CARD);
   }
   
   private void createLevelEditor() {
      JPanel levelEditor = new JPanel();
      levelEditor.setLayout(new BorderLayout());
      createCanvas(levelEditor);
      createContentToolbar(levelEditor);
      add(levelEditor, LEVEL_EDITOR_CARD);
   }
   
   private void createCanvas(JPanel parent) {
      mCanvas = new LevelCanvas(mEditorSettings, mLevelModel, mDrawerSettings);
      JScrollPane scroller = new JScrollPane(mCanvas);
      scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      parent.add(scroller, BorderLayout.CENTER);
   }
   
   private void createContentToolbar(JPanel parent) {
      DrawerToolbar toolbar = new DrawerToolbar(mLevelModel, mDrawerSettings);
      parent.add(toolbar, BorderLayout.SOUTH);
   }
   
   private PropertyChangeListener createEditorSettingsListener() {
      return new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent e) {
            String property = e.getPropertyName();
            if("editor_mode".equals(property)) {
               switchContext((EditorMode)e.getNewValue());
            }
         }
      };
   }
   
   private void switchContext(EditorMode mode) {
      if(mode.equals(EditorMode.ENTITY_EDITOR)) {
         showCard(ENTITY_EDITOR_CARD);
      } else if(mode.equals(EditorMode.OBJECTS)) {
         showCard(LEVEL_EDITOR_CARD);
      }
   }
   
   private void showCard(String cardName) {
      CardLayout layout = (CardLayout)getLayout();
      layout.show(this, cardName);
   }
}
