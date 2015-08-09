package Core.LevelEditor;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;

import Core.LevelEditor.Components.EditorToolbar;
import Core.LevelEditor.Models.LevelModel;
import Core.LevelEditor.Settings.EditorSettings;
import Menu.MenuScreen;
import Menu.MenuStack;

public class LevelEditor extends MenuScreen {   
   private MenuStack mStack;
   
   private ContentPanel mContentPanel;
   
   private EditorSettings mSettings;
   private LevelModel mLevelModel;
   
   public LevelEditor(MenuStack stack) {
      mStack = stack;
      
      mSettings = new EditorSettings();
      mLevelModel = new LevelModel();
      
      createWidgets();
   }
   
   /* ********************************************************************** */
   /*                               VIEW                                     */
   /* ********************************************************************** */
   private void createWidgets() {
      setLayout(new BorderLayout());
      createToolBar();
      createEditorPanel();
   }
   
   private void createToolBar() {
      EditorToolbar toolbar = new EditorToolbar(mStack, mLevelModel);
      add(toolbar, BorderLayout.NORTH);
   }
   
   private void createEditorPanel() {
      JSplitPane editorPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
      createSettingsPanel(editorPanel);
      createContentPanel(editorPanel);
      add(editorPanel, BorderLayout.CENTER);
   }
   
   private void createSettingsPanel(JSplitPane parent) {
      SettingsPanel settingsPanel = new SettingsPanel(mSettings);
      parent.add(settingsPanel);
   }
   
   private void createContentPanel(JSplitPane parent) {
      mContentPanel = new ContentPanel(mSettings, mLevelModel);
      parent.add(mContentPanel);
   }
}
