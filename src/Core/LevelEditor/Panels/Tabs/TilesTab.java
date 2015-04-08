package Core.LevelEditor.Panels.Tabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import Entity.EntityComponents;
import Entity.EntityFactory;
import Entity.Systems.DrawingSystem;
import Graphics.ScrollablePicture;
import Level.EntityContainer;

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
      
      // TODO Eliminate the need to instantiate a Box2D world for entities
      // in the preview tab.
      World world = new World(new Vec2(0.0f, 0.0f));
      for(int i = 0; i < mEntityNames.length; i++)
         createEntity(world, i, mEntityNames[i], entityFactory);
   }
   
   private void createEntity(World world,int index, String name, EntityFactory entityFactory) {
      mEntityComponents[index] = new EntityComponents();
      
      int mask = entityFactory.createEntity(world, name, 
              new Vec2(0.0f, 0.0f), 
              mEntityComponents[index]);
      
      // We want to see the entity in the level editor so a null image 
      // will represent object's lack of a sprite.
      if((mask & EntityContainer.ENTITY_DRAWABLE) == 0)
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
