package Core.LevelEditor;

import Core.LevelEditor.Panels.ContentPanel;
import Core.LevelEditor.Panels.SettingsPanel;
import Entity.EntityComponents;
import Entity.EntityFactory;
import Level.Level;
import Level.LevelLoaderFactory;
import Level.LevelLoadingScript;
import Level.EntityContainer;
import Menu.MenuScreen;
import Menu.MenuStack;
import Util.ErrorLog;
import Util.FileExistsException;
import Util.Size;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

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
   private EntityContainer mWorld;
   
   public LevelEditor(MenuStack stack) {
      mStack = stack;
      
      mEntityFactory = new EntityFactory();
      mLevelLoaderFactory = new LevelLoaderFactory();
      
      mLevel = new Level();
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
      switch(button)
      {
         case MouseEvent.BUTTON1:
            placeEntity(new Point(e.getX(), e.getY()));
         break;
         case MouseEvent.BUTTON3:
            removeEntity(new Point(e.getX(), e.getY()));
         break;
      }
   }
   
   private void placeEntity(Point position) {
      int id = mWorld.createNewEntity();
      String selectedEntity = mSettingsPanel.getSelectedEntity();
      int mask = mEntityFactory.createEntity(selectedEntity, 
              new Point2D.Double(position.x, position.y), 
              mWorld.accessComponents(id));
      if(entityCollidesWithOthers(id))
         return;
      mWorld.setEntityMask(id, mask);
      mContentPanel.update();
   }
   
   private boolean entityCollidesWithOthers(int id) {
      EntityComponents entityComp = mWorld.accessComponents(id);
      Rectangle entity = new Rectangle((int)entityComp.position.x, (int)entityComp.position.y,
                 entityComp.drawable.image.getWidth(), entityComp.drawable.image.getHeight());
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         if(i == id)
            continue;
         
         int mask = mWorld.getEntityMask(i);
         if(mask == EntityContainer.ENTITY_NONE)
            continue;
         
         EntityComponents otherComp = mWorld.accessComponents(i);
         Rectangle other = new Rectangle((int)otherComp.position.x, (int)otherComp.position.y,
                 otherComp.drawable.image.getWidth(), otherComp.drawable.image.getHeight());
         
         if(other.intersects(entity))
            return true;
      }
      
      return false;
   }
   
   private void removeEntity(Point position) {
      int mask;
      Rectangle2D.Double bounds = new Rectangle2D.Double();
      for(int i = 0; i < EntityContainer.MAXIMUM_ENTITIES; i++) {
         mask = mWorld.getEntityMask(i);
         if(mask == EntityContainer.ENTITY_NONE)
            continue;
         
         EntityComponents components = mWorld.accessComponents(i);
         bounds.x = components.position.x;
         bounds.y = components.position.y;
         if((mask & EntityContainer.ENTITY_COLLIDABLE) != 0) {
            if(components.collidable.bindToImageDimensions)
            {
               bounds.width = components.drawable.image.getWidth();
               bounds.height = components.drawable.image.getHeight();
            }
            else
            {
               bounds.width = components.collidable.width;
               bounds.height = components.collidable.height;
            }
         } else {
            bounds.width = components.drawable.image.getWidth();
            bounds.height = components.drawable.image.getHeight();
         }
         
         if(bounds.contains(position))
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
      mLevel.size = new Size(800, 600);
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
      mContentPanel.setCanvasSize(mLevel.size.width, mLevel.size.height);
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
