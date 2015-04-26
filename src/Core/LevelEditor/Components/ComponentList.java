package Core.LevelEditor.Components;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Core.LevelEditor.Settings.EditorSettings;
import Core.LevelEditor.Settings.EntityComposerSettings;

public class ComponentList extends JPanel {
   private EditorSettings mEditorSettings;
   private EntityComposerSettings mEntityComposerSettings;
   
   private JPanel mComponentList;
   
   
   public ComponentList(EditorSettings editorSettings, 
         EntityComposerSettings entityComposerSettings) {
      mEditorSettings = editorSettings;
      mEntityComposerSettings = entityComposerSettings;
      
      createWidgets();
      
      mEditorSettings.addPropertyChangeListener(
            createEditorListener());
   }
   
   private void createWidgets() {
      setLayout(new BorderLayout());
      createList();
      createButtons();
   }
   
   private void createList() {
      mComponentList = new JPanel();
      mComponentList.setLayout(new BoxLayout(mComponentList, BoxLayout.Y_AXIS));
      JScrollPane scroller = new JScrollPane(mComponentList);
      add(scroller, BorderLayout.CENTER);
   }
   
   private void createButtons() {
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
      createAddButton(buttonPanel);
      createRemoveButton(buttonPanel);
      add(buttonPanel, BorderLayout.SOUTH);
   }
   
   private void createAddButton(JPanel parent) {
      JButton addButton = new JButton("Add");
      parent.add(addButton);
   }
   
   private void createRemoveButton(JPanel parent) {
      JButton removeButton = new JButton("Remove");
      parent.add(removeButton);
   }
   
   private PropertyChangeListener createEditorListener() {
      return new PropertyChangeListener() {
         @Override
         public void propertyChange(PropertyChangeEvent e) {
            String property = e.getPropertyName();
            if("selected_entity_model".equals(property)) {
               displayEntityModel();
            }
         }
      };
   }
   
   private void displayEntityModel() {
      
   }
}
