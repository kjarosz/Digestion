package Core.LevelEditor.Tabs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import Core.LevelEditor.Models.EntityModel;
import Core.LevelEditor.Models.EntityModelList;
import Core.LevelEditor.Settings.EditorSettings;

public class EntityTab extends JPanel {
   private EditorSettings mEditorSettings;
   private EntityModelList mEntityModelList;
   
   private JList<EntityModel> mModelList;
   
   public EntityTab(EditorSettings editorSettings) {
      mEditorSettings = editorSettings;
      mEntityModelList = new EntityModelList();
      createWidgets();
   }
   
   private void createWidgets() {
      setLayout(new BorderLayout());
      add(createList(), BorderLayout.CENTER);
      add(createButtons(), BorderLayout.SOUTH);
   }
   
   private JScrollPane createList() {
      mModelList = new JList<>(mEntityModelList);
      mModelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      mModelList.addListSelectionListener((ListSelectionEvent e) -> selectEntityModel());
      JScrollPane scroller = new JScrollPane(mModelList);
      return scroller;
   }
   
   private JPanel createButtons() {
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
      buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
      createAndAddButtons(buttonPanel);
      return buttonPanel;
   }
   
   private void createAndAddButtons(JPanel panel) {
      panel.add(createButton("Add",       (ActionEvent e) -> addEntityModel()));
      panel.add(createButton("Duplicate", (ActionEvent e) -> duplicateEntityModel()));
      panel.add(createButton("Remove",    (ActionEvent e) -> removeEntityModel()));
      panel.add(createButton("Save",      (ActionEvent e) -> saveEntityModels()));
      panel.add(createButton("Load",      (ActionEvent e) -> loadEntityModels()));
   }
   
   private JButton createButton(String name, ActionListener command) {
      JButton button = new JButton(name);
      button.addActionListener(command);
      return button;
   }
   
   /* ********************************************************************** */
   /*                                COMMANDS                                */
   /* ********************************************************************** */   
   private void selectEntityModel() {
      EntityModel selectedModel = mModelList.getSelectedValue();
      mEditorSettings.setSelectedEntityModel(selectedModel);
   }
   
   private void addEntityModel() {
      boolean added = false;
      while(!added) {
         String name = askForName();
         if(name == null) {
            return;
         }
         added = addEntity(name);
      }
   }
   
   private String askForName() {
      return JOptionPane.showInputDialog(this, 
            "Enter name for entity", 
            "New Entity", 
            JOptionPane.OK_CANCEL_OPTION);
   }
   
   private boolean addEntity(String name) {
      try {
         tryAddingEntity(name);
         return true;
      } catch(RuntimeException e) {
         JOptionPane.showMessageDialog(this, 
               e.getMessage(), 
               "Error Adding Entity", 
               JOptionPane.ERROR_MESSAGE);
         return false;
      }
   }
   
   private void tryAddingEntity(String name) {
      EntityModel model = new EntityModel(name);
      mEntityModelList.addEntityModel(model);
   }
   
   private void duplicateEntityModel() {
   }
   
   private void removeEntityModel() {
      int index = mModelList.getSelectedIndex();
      if(index >= 0) {
         mEntityModelList.removeEntityModel(index);
         mModelList.clearSelection();
      }
      repaint();
   }
   
   
   private void saveEntityModels() {
      
   }
   
   private void loadEntityModels() {
      
   }
}
