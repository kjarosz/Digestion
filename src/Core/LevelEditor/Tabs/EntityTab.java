package Core.LevelEditor.Tabs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import Core.LevelEditor.Models.EntityModel;
import Core.LevelEditor.Models.EntityModelList;

public class EntityTab extends JPanel {
   private EntityModelList mEntityModel;
   
   public EntityTab(EntityModelList entityModel) {
      mEntityModel = entityModel;
      createWidgets();
   }
   
   private void createWidgets() {
      setLayout(new BorderLayout());
      add(createList(), BorderLayout.CENTER);
      add(createButtons(), BorderLayout.SOUTH);
   }
   
   private JScrollPane createList() {
      JList<EntityModel> list = new JList<>(mEntityModel);
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      JScrollPane scroller = new JScrollPane(list);
      return scroller;
   }
   
   private JPanel createButtons() {
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
      buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
      buttonPanel.add(createSaveButton());
      buttonPanel.add(createLoadButton());
      return buttonPanel;
   }
   
   private JButton createSaveButton() {
      JButton button = new JButton("Save");
      button.addActionListener(createSaveCommand());
      return button;
   }
   
   private JButton createLoadButton() {
      JButton button = new JButton("Load");
      button.addActionListener(createLoadCommand());
      return button;
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
