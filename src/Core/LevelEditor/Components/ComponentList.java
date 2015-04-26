package Core.LevelEditor.Components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Core.LevelEditor.Models.ComponentModel;
import Core.LevelEditor.Models.EntityModel;
import Core.LevelEditor.Settings.EditorSettings;
import Core.LevelEditor.Settings.EntityComposerSettings;
import Core.LevelEditor.Utils.ComponentFactory;

public class ComponentList extends JPanel {
   private EditorSettings mEditorSettings;
   private EntityComposerSettings mEntityComposerSettings;
   
   private JPanel mComponentList;
   
   private ComponentFactory mComponentFactory;
   
   public ComponentList(EditorSettings editorSettings, 
         EntityComposerSettings entityComposerSettings) {
      mEditorSettings = editorSettings;
      mEntityComposerSettings = entityComposerSettings;
      
      mComponentFactory = new ComponentFactory();
      
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
      addButton.addActionListener(createAddAction());
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
   
   private ActionListener createAddAction() {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            addComponent();
         }
      };
   }
   
   private void addComponent() {
      String componentName = promptForComponent();
      if("uninitializedValue".equals(componentName)) {
         return;
      }
      createNewComponent(componentName);
      displayEntityModel();
   }
   
   private String promptForComponent() {
      JOptionPane dialog = makePrompt();
      JDialog jDialog = dialog.createDialog("Create New Component");
      jDialog.setVisible(true);
      return (String)dialog.getInputValue();
   }
   
   private JOptionPane makePrompt() {
      JOptionPane dialog = new JOptionPane(
          "Choose new component",
          JOptionPane.QUESTION_MESSAGE,
          JOptionPane.OK_CANCEL_OPTION
      );
      Object components[] = filterAvailableComponents();
      dialog.setSelectionValues(components);
      return dialog;
   }
   
   private Object[] filterAvailableComponents() {
      EntityModel entityModel = mEditorSettings.getSelectedEntityModel();
      List<ComponentModel> components = entityModel.getComponents();
      List<String> availableComponents = new LinkedList<>();
      NextComponent:
      for(String componentName: mComponentFactory.getComponentList()) {
         for(ComponentModel componentModel: components) {
            if(componentName.equals(componentModel.getName())) {
               continue NextComponent;
            }
         }
         availableComponents.add(componentName);
      }
      return availableComponents.toArray();
   }
   
   private void createNewComponent(String componentName) {
      
   }
}
