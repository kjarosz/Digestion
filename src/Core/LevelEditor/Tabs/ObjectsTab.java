package Core.LevelEditor.Tabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Core.LevelEditor.Settings.EditorSettings;
import Entity.EntityComponents;
import Entity.EntityFactory;
import Entity.Systems.DrawingSystem;
import Graphics.ScrollablePicture;
import Level.EntityContainer;
import Util.Vector2D;

public class ObjectsTab extends JPanel {
   private String mEntityNames[];
   private EntityComponents mEntityComponents[];
   
   private ImageIcon mCanvas;
   private ScrollablePicture mPicture;
   private JComboBox<String> mObjectSelector;

	public ObjectsTab(EditorSettings editorSettings) {
      loadEntities();      
      createWidgets(editorSettings);
	}
   
   private void loadEntities() {
      EntityFactory entityFactory = EntityFactory.getInstance();
      mEntityNames = entityFactory.getEntityNames();
      mEntityComponents = new EntityComponents[mEntityNames.length];
      
      for(int i = 0; i < mEntityNames.length; i++)
         createEntity(i, mEntityNames[i], entityFactory);
   }
   
   private void createEntity(int index, String name, EntityFactory entityFactory) {
      mEntityComponents[index] = new EntityComponents();
      
      int mask = entityFactory.createEntity(name, 
              new Vector2D(0.0f, 0.0f),
              new Vector2D(0.5f, 2.0f),
              mEntityComponents[index]);
      
      // We want to see the entity in the level editor so a null image 
      // will represent object's lack of a sprite.
      if((mask & EntityContainer.ENTITY_DRAWABLE) == 0)
         mEntityComponents[index].drawable.image = DrawingSystem.getNullImage();
   }
   
   /* ********************************************************************** */
   /*                              VIEW                                      */
   /* ********************************************************************** */
   private void createWidgets(EditorSettings editorSettings) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      
      createObjectPreviewPanel();
		add(Box.createVerticalStrut(5));
      
      createObjectSelectionPanel(editorSettings);
		add(Box.createVerticalStrut(5));
      
      createInstructionPanel();
		add(Box.createVerticalGlue());
   }
   
   private void createObjectPreviewPanel() {
      mCanvas = new ImageIcon();
      mPicture = new ScrollablePicture(mCanvas, 5);
      createPreviewScrollPane(mPicture);
   }
   
   private void createPreviewScrollPane(ScrollablePicture picture) {
      JScrollPane previewPanel = new JScrollPane(picture);
      previewPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      previewPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      previewPanel.setSize(250, 450);
		add(previewPanel);
   }
   
   private void createObjectSelectionPanel(EditorSettings editorSettings) {
      createObjectSelector(editorSettings);
      displaySelectedEntity();
      
      JPanel objectSelectPanel = new JPanel();
      objectSelectPanel.add(new JLabel("Object:"));
      objectSelectPanel.add(mObjectSelector);
		add(objectSelectPanel);
		
		editorSettings.setSelectedEntity(
		      mEntityNames[0]
		);
   }
   
   private void createObjectSelector(EditorSettings editorSettings) {
      mObjectSelector = new JComboBox<>(mEntityNames);
      mObjectSelector.addActionListener(createObjectSelectAction(editorSettings));
   }
   
   private void displaySelectedEntity() {
      int id = mObjectSelector.getSelectedIndex();
      mCanvas.setImage(mEntityComponents[id].drawable.image);
      mPicture.setSize(mEntityComponents[id].drawable.image.getWidth(),
                       mEntityComponents[id].drawable.image.getHeight());
      repaint();
   }
   
   private void createInstructionPanel() {
      JPanel instructionPanel = new JPanel();
      instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
      instructionPanel.add(new JLabel("To place: Left Mouse Click"));
      instructionPanel.add(new JLabel("To delete: Right Mouse Click"));
      instructionPanel.add(new JLabel("To modify: Alt + Right Mouse Click"));
		add(instructionPanel);
   }
   
   private ActionListener createObjectSelectAction(EditorSettings editorSettings) {
      return new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            int id = mObjectSelector.getSelectedIndex();
            String entityName = mEntityNames[id];
            editorSettings.setSelectedEntity(entityName);
            displaySelectedEntity();
         }
      };
   }
}
