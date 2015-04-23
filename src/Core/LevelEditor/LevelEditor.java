package Core.LevelEditor;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;

import Core.LevelEditor.Components.EditorToolbar;
import Core.LevelEditor.Models.EditorSettings;
import Core.LevelEditor.Models.EntityModelList;
import Core.LevelEditor.Models.LevelModel;
import Menu.MenuScreen;
import Menu.MenuStack;

public class LevelEditor extends MenuScreen {   
   private MenuStack mStack;
   
   private ContentPanel mContentPanel;
   
   private EditorSettings mSettings;
   private EntityModelList mEntityModelList;
   private LevelModel mLevelModel;
   
   public LevelEditor(MenuStack stack) {
      mStack = stack;
      
      mSettings = new EditorSettings();
      mEntityModelList = new EntityModelList();
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
      SettingsPanel settingsPanel = new SettingsPanel(mEntityModelList, mSettings);
      parent.add(settingsPanel);
   }
   
   private void createContentPanel(JSplitPane parent) {
      mContentPanel = new ContentPanel(mSettings, mEntityModelList, mLevelModel);
      parent.add(mContentPanel);
   }
}
