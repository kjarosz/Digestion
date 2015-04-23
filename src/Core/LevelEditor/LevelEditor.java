package Core.LevelEditor;

import java.awt.BorderLayout;

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
   private EntityModelList mEntityModel;
   private LevelModel mLevelModel;
   
   public LevelEditor(MenuStack stack) {
      mStack = stack;
      
      mSettings = new EditorSettings();
      mEntityModel = new EntityModelList();
      mLevelModel = new LevelModel();
      
      createWidgets();
   }
   
   /* ********************************************************************** */
   /*                               VIEW                                     */
   /* ********************************************************************** */
   private void createWidgets() {
      setLayout(new BorderLayout());
      createToolBar();
      createSettingsPanel();
      createContentPanel();
   }
   
   private void createToolBar() {
      EditorToolbar toolbar = new EditorToolbar(mStack, mLevelModel);
      add(toolbar, BorderLayout.NORTH);
   }
   
   private void createSettingsPanel() {
      SettingsPanel settingsPanel = new SettingsPanel(mEntityModel, mSettings);
      add(settingsPanel, BorderLayout.WEST);
   }
   
   private void createContentPanel() {
      mContentPanel = new ContentPanel(mSettings, mLevelModel);
      add(mContentPanel, BorderLayout.CENTER);
   }
}
