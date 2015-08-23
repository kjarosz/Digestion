package Core.LevelEditor.Control.Commands;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import Core.LevelEditor.Models.Entity;
import Core.LevelEditor.Models.LevelModel;
import Core.LevelEditor.Settings.DrawerSettings;
import Core.LevelEditor.Settings.EditorSettings;
import Entity.EntityComponents;
import Entity.EntityFactory;
import Level.EntityContainer;
import Util.Size;
import Util.UnitConverter;

public class AddEntity extends LevelModelCommand {
   private ChangeEntityCommand mChangeCommand;
   
   private LinkedList<Entity> mEntityCache;
   
   public AddEntity(ChangeEntityCommand changeCommand, LevelModel level, 
         EditorSettings editor, DrawerSettings drawer) {
      super(level, editor, drawer);
      
      mChangeCommand = changeCommand;
      
      makeEntities();
   }
   
   private void makeEntities() {
      mEntityCache = new LinkedList<>();
      EntityFactory factory = EntityFactory.getInstance();
      
      Size grid = mDrawer.getGridSize();
      World world = new World(new Vec2(0.0f, 0.0f));
      Vec2 pos = new Vec2(0, 0);
      EntityComponents comps = new EntityComponents();
      for(String name: factory.getEntityNames()) {
         int mask = factory.createEntity(world, name, pos, comps);
         
         if((mask & EntityContainer.ENTITY_DRAWABLE) != 0) {
            Rectangle size = new Rectangle(0, 0, grid.width, grid.height);
            if(!comps.resizeable) {
               size.width = (int)UnitConverter.metersToPixels(comps.m_width);
               size.height = (int)UnitConverter.metersToPixels(comps.m_height);
            } 
            Entity entity = new Entity(name, comps.drawable.image, size);
            entity.setResizeable(comps.resizeable);
            mEntityCache.add(entity);
         }
      }
   }
   
   public void perform(MouseEvent e) {
      if(levelHasEntity(e.getPoint())) {
         return;
      }
      
      Entity entity = getSelectedEntity().clone();
      
      Point pos = snapToGrid(e.getPoint());
      Size gridSize = mDrawer.getGridSize();
      Rectangle newEntityRect = new Rectangle();
      newEntityRect.x = pos.x;
      newEntityRect.y = pos.y;
      newEntityRect.width = gridSize.width;
      newEntityRect.height = gridSize.height;
      
      entity.setRect(newEntityRect);
      
      mLevel.addEntity(entity);
      
      mChangeCommand.setActiveEntity(entity);
   }
   
   private boolean levelHasEntity(Point coords) {
      for(Entity entity: mLevel.getEntities()) {
         if(entity.getRect().contains(coords)) {
            return true;
         }
      }
      return false;
   }
   
   private Entity getSelectedEntity() {
      try {
         return tryGettingEntity();
      } catch(RuntimeException e) {
         JOptionPane.showMessageDialog(null, 
               e.getMessage(),
               "Error Selecting Entity",
               JOptionPane.ERROR_MESSAGE);
         throw e;
      }
   }
   
   private Entity tryGettingEntity() {
      String name = mEditor.getSelectedEntity();
      for(Entity entity: mEntityCache) {
         if(entity.getName().equals(name)) {
            return entity;
         }
      }
      throw new RuntimeException("No entity found.");
   }
}
