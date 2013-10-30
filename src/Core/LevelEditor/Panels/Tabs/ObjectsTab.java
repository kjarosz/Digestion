package Core.LevelEditor.Panels.Tabs;

import Entity.EntityComponents;
import Entity.EntityFactory;
import Entity.Systems.DrawingSystem;
import Graphics.ScrollablePicture;
import Level.World;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.*;

public class ObjectsTab extends JPanel implements ActionListener {
   private final String ACTION_OBJECT_SELECT = "Object Select";
   
   private String mEntityNames[];
   private EntityComponents mEntityComponents[];
   
   private ImageIcon mCanvas;
   private ScrollablePicture mPicture;
   private JComboBox<String> mObjectSelector;

	public ObjectsTab(EntityFactory entityFactory) {
      loadEntities(entityFactory);      
      createWidgets();
	}
   
   private void loadEntities(EntityFactory entityFactory) {
      mEntityNames = entityFactory.getEntityNames();
      mEntityComponents = new EntityComponents[mEntityNames.length];
      
      for(int i = 0; i < mEntityNames.length; i++)
         createEntity(i, mEntityNames[i], entityFactory);
   }
   
   private void createEntity(int index, String name, EntityFactory entityFactory) {
      mEntityComponents[index] = new EntityComponents();
      
      int mask = entityFactory.createEntity(name, 
              new Point2D.Double(0.0, 0.0), 
              mEntityComponents[index]);
      
      // We want to see the entity in the level editor so we assign a null image
      if((mask & World.ENTITY_DRAWABLE) == 0)
         mEntityComponents[index].drawable.image = DrawingSystem.getNullImage();
   }
   
   private void createWidgets() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      
      createObjectPreviewPanel();
		add(Box.createVerticalStrut(5));
      
      createObjectSelectionPanel();
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
   
   private void createObjectSelectionPanel() {
      createObjectSelector();
      displaySelectedEntity();
      
      JPanel objectSelectPanel = new JPanel();
      objectSelectPanel.add(new JLabel("Object:"));
      objectSelectPanel.add(mObjectSelector);
		add(objectSelectPanel);
   }
   
   private void createObjectSelector() {
      mObjectSelector = new JComboBox<>(mEntityNames);
      mObjectSelector.setActionCommand(ACTION_OBJECT_SELECT);
      mObjectSelector.addActionListener(this);
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
   
   public String getSelectedEntity() {
      return mEntityNames[mObjectSelector.getSelectedIndex()];
   }
   
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
      
      if(command.compareTo(ACTION_OBJECT_SELECT) == 0) {
         displaySelectedEntity();
      }
	}
}
