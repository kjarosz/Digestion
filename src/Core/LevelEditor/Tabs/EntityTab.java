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

import Core.LevelEditor.Models.EntityModel;
import Core.LevelEditor.Models.EntityModelList;

public class EntityTab extends JPanel {
   private JList<EntityModel> mListing;
   private EntityModelList mEntityModelList;
   
   public EntityTab(EntityModelList modelList) {
      mEntityModelList = modelList;
      createWidgets();
   }
   
   private void createWidgets() {
      setLayout(new BorderLayout());
      add(createList(), BorderLayout.CENTER);
      add(createButtons(), BorderLayout.SOUTH);
   }
   
   private JScrollPane createList() {
      mListing = new JList<>(mEntityModelList);
      mListing.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      JScrollPane scroller = new JScrollPane(mListing);
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
      panel.add(createButton("Add",       createAddCommand()));
      panel.add(createButton("Duplicate", createDuplicateCommand()));
      panel.add(createButton("Remove",    createRemoveCommand()));
      panel.add(createButton("Save",      createSaveCommand()));
      panel.add(createButton("Load",      createLoadCommand()));
   }
   
   private JButton createButton(String name, ActionListener command) {
      JButton button = new JButton(name);
      button.addActionListener(command);
      return button;
   }
   
   /* ********************************************************************** */
   /*                                COMMANDS                                */
   /* ********************************************************************** */
   private ActionListener createAddCommand() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            addEntityModel();
         }
      };
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
   
   private ActionListener createDuplicateCommand() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            
         }
      };
   }
   
   private ActionListener createRemoveCommand() {
      JPanel me = this;
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int index = mListing.getSelectedIndex();
            removeSelectedEntity(index);
            me.repaint();
         }
      };
   }
   
   private void removeSelectedEntity(int index) {
       if(index >= 0) {
          mEntityModelList.removeEntityModel(index);
       }
   }
   
   private ActionListener createSaveCommand() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            
         }
      };
   }
   
   private ActionListener createLoadCommand() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            
         }
      };
   }
}
