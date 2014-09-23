package Core.LevelEditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import Core.LevelEditor.Panels.ContentPanel;
import Core.LevelEditor.Panels.SettingsPanel;
import Entity.EntityComponents;
import Entity.EntityFactory;
import Level.EntityContainer;
import Level.Level;
import Level.LevelLoaderFactory;
import Level.LevelLoadingScript;
import Menu.MenuScreen;
import Menu.MenuStack;
import Util.ErrorLog;
import Util.FileExistsException;
import Util.UnitConverter;

public class LevelEditor extends MenuScreen implements ActionListener {
   private final String ACTION_NEW_LEVEL = "New Level";
   private final String ACTION_OPEN_LEVEL = "Load Level";
   private final String ACTION_SAVE_LEVEL = "Save Level";
   private final String ACTION_QUIT_EDITOR = "Quit Editor";
   
   private MenuStack mStack;
   
   private SettingsPanel mSettingsPanel;
   private ContentPanel mContentPanel;
   
   private EntityFactory mEntityFactory;
   
   private LevelLoaderFactory mLevelLoaderFactory;
   private Level mLevel;
   private World mMuckBox2DWorld;
   private EntityContainer mWorld;
   
   public LevelEditor(MenuStack stack) {
      mStack = stack;
      
      mEntityFactory = new EntityFactory();
      mLevelLoaderFactory = new LevelLoaderFactory();
      
      mLevel = new Level();
      mMuckBox2DWorld = new World(new Vec2(0.0f, 0.0f));
      mWorld = new EntityContainer();
      
      createWidgets();
   }
      
   private void createWidgets() {
      setLayout(new BorderLayout());
      createToolBar();
      createSettingsPanel();
      createContentPanel();
   }
   
   private void createToolBar() {
      JToolBar toolbar = new JToolBar();
      addToolBarButton(toolbar, "New", ACTION_NEW_LEVEL);
      addToolBarButton(toolbar, "Open", ACTION_OPEN_LEVEL);
      addToolBarButton(toolbar, "Save", ACTION_SAVE_LEVEL);
      addToolBarButton(toolbar, "Quit", ACTION_QUIT_EDITOR);
      add(toolbar, BorderLayout.NORTH);
   }
   
   private void addToolBarButton(JToolBar bar, String label, String command) {
      JButton button = new JButton(label);
      button.setActionCommand(command);
      button.addActionListener(this);
      bar.add(button);
   }
   
   private void createSettingsPanel() {
      mSettingsPanel = new SettingsPanel(mEntityFactory);
      add(mSettingsPanel, BorderLayout.WEST);
   }
   
   private void createContentPanel() {
      mContentPanel = new ContentPanel(this, mLevel, mWorld);
      add(mContentPanel, BorderLayout.CENTER);
   }
   
   public void processClick(MouseEvent e, int button) {
      e = mContentPanel.snapToGrid(e);
      
      SettingsPanel.InputMode mode = mSettingsPanel.getInputMode();
      switch(mode)
      {
         case Entity:
            respondToMouseClickInEntityMode(e, button);
         break;
         case Pathfinding:
            
         break;
      }
   }
   
   private void respondToMouseClickInEntityMode(MouseEvent e, int button) {
      Vec2 position = UnitConverter.pixelsToMeters(new Vec2(e.getX(), e.getY()));
      switch(button)
      {
         case MouseEvent.BUTTON1:
            placeEntity(position);
         break;
         case MouseEvent.BUTTON3:
            removeEntity(position);
         break;
      }
   }
   
   private void placeEntity(Vec2 position) {
      int id = mWorld.createNewEntity();
      String selectedEntity = mSettingsPanel.getSelectedEntity();
      int mask = mEntityFactory.createEntity(mMuckBox2DWorld, selectedEntity, 
              position, 
              mWorld.accessComponents(id));
      if(entityCollidesWithOthers(id))
         return;
      mWorld.setEntityMask(id, mask);
      mContentPanel.update();
   }
   
   private boolean entityCollidesWithOthers(int id) {
      EntityComponents entityComp = mWorld.accessComponents(id);
      Rectangle2D.Float entity = createEntityShape(entityComp.body.getPosition(), entityComp.width, entityComp.height);
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         if(i == id)
            continue;
         
         int mask = mWorld.getEntityMask(i);
         if(mask == EntityContainer.ENTITY_NONE)
            continue;
         
         EntityComponents otherComp = mWorld.accessComponents(i);
         Rectangle2D.Float other = createEntityShape(otherComp.body.getPosition(),
               otherComp.width, otherComp.height);
         
         if(other.intersects(entity))
            return true;
      }
      
      return false;
   }
   
   private Rectangle2D.Float createEntityShape(Vec2 position, float width, float height) {
      Rectangle2D.Float shape = new Rectangle2D.Float();
      shape.x = position.x - width/2.0f;
      shape.y = position.y - height/2.0f;
      shape.width = width/2.0f;
      shape.height = height/2.0f;
      return shape;
   }
   
   private void removeEntity(Vec2 coordinates) {
      int mask;
      Rectangle2D.Double bounds = new Rectangle2D.Double();
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         mask = mWorld.getEntityMask(i);
         if(mask == EntityContainer.ENTITY_NONE)
            continue;
         
         EntityComponents components = mWorld.accessComponents(i);
         Vec2 position = components.body.getPosition();
         bounds.x = position.x;
         bounds.y = position.y;
         bounds.width = components.width;
         bounds.height = components.height;
         
         if(bounds.contains(coordinates.x, coordinates.y))
            mWorld.destroyEntity(i);
      }
      mContentPanel.update();
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
      
      if(command.compareTo(ACTION_NEW_LEVEL) == 0) {
         try {
            createANewLevel();
         } catch(IOException ex) {
            handleWritingError(ex);
         } catch(FileExistsException ex) {
            notifyUserOfExistingFilename(ex);
         }
      } else if(command.compareTo(ACTION_OPEN_LEVEL) == 0) {
         openLevel();
      } else if(command.compareTo(ACTION_SAVE_LEVEL) == 0) {
         saveLevel();
      } else if(command.compareTo(ACTION_QUIT_EDITOR) == 0) {
         mStack.popScreen();
      }
   }
   
   private void createANewLevel() throws IOException {
      String name = JOptionPane.showInputDialog("Enter name of the level.");
      
      
      mLevel.name = name;
      mLevel.m_size = new Vec2(25.0f, 18.75f);
      mWorld.clearEntities();
      
      mContentPanel.update();
   }
   
   private void handleWritingError(IOException e) {
      ErrorLog log = ErrorLog.getInstance();
      log.displayMessageDialog(e.getMessage());
      log.writeError(e.getMessage());
   }
   
   private void notifyUserOfExistingFilename(FileExistsException e) {
      JOptionPane.showMessageDialog(this, "This file is already taken.");
   }
   
   private void openLevel() {
      LevelLoadingScript script = selectLevel();
      if(script == null)
         return;
      script.loadLevel(mLevel);
      mWorld.clearEntities();
      script.createEntities(mEntityFactory, mWorld);
      
      Vec2 px_levelSize = UnitConverter.metersToPixels(mLevel.m_size);
      mContentPanel.setCanvasSize((int)px_levelSize.x, (int)px_levelSize.y);
      mContentPanel.update();
   }
   
   private LevelLoadingScript selectLevel() {
      String names[] = mLevelLoaderFactory.getLevelScripts();
      if(names.length != 0) {
         String selected = (String)JOptionPane.showInputDialog(
                  this, 
                  "Select a level to open.", 
                  "Level Selector",
                  JOptionPane.PLAIN_MESSAGE,
                  null,
                  names,
                  names[0]);
         return mLevelLoaderFactory.loadLevelScript(selected);
      } else {
         JOptionPane.showMessageDialog(this, "No levels could be found");
         return null;
      }
   }
   
   private void saveLevel() {
      mLevelLoaderFactory.saveLevel(mLevel, mWorld);
   }
}
